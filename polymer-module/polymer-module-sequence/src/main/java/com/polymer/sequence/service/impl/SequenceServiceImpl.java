package com.polymer.sequence.service.impl;

import com.polymer.framework.common.exception.ServiceException;
import com.polymer.sequence.mapper.SequenceMapper;
import com.polymer.sequence.range.SeqRange;
import com.polymer.sequence.service.SequenceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 发号器表Service业务层处理
 *
 * @author polymer polymer@126.com
 * @since 1.0.0 2026-03-27
 */
@Service
public class SequenceServiceImpl implements SequenceService {
    @Resource
    private SequenceMapper sequenceMapper;

    private final Map<String, AtomicReference<SeqRange>> rangeMap = new ConcurrentHashMap<>();

    @Override
    public long nextValue(String name, int step, long stepStart) {
        AtomicReference<SeqRange> rangeRef = rangeMap.computeIfAbsent(name, k -> new AtomicReference<>());
        while (true) {
            SeqRange current = rangeRef.get();
            if (current == null || current.isOver()) {
                // 获取新区间
                SeqRange newRange = nextRange(name, step, stepStart);
                if (!rangeRef.compareAndSet(current, newRange)) {
                    // 其他线程已更新，重试
                    continue;
                }
                current = newRange;
            }
            long value = current.getAndIncrement();
            if (value != -1) {
                return value;
            }
            // 当前区间已耗尽，标记并继续循环获取新区间
            current.setOver(true);
        }
    }

    private SeqRange nextRange(String name, int step, long stepStart) {
        // 区间失败重试次数
        int retryTimes = 3;
        for (int i = 0; i < retryTimes; i++) {
            Long oldValue = sequenceMapper.selectCurrentValue(name);
            if (oldValue == null) {
                // 初始化该业务名称的记录
                sequenceMapper.insertIfNotExists(name, stepStart);
                continue;
            }
            long newValue = oldValue + step;
            int updated = sequenceMapper.updateCurrentValue(name, newValue, oldValue);
            if (updated > 0) {
                return new SeqRange(oldValue + 1, newValue);
            }
        }
        throw new ServiceException("获取序列号区间失败，重试次数已达上限：" + retryTimes);
    }
}
