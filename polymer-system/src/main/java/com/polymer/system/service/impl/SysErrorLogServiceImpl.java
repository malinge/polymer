package com.polymer.system.service.impl;

import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.logger.event.ErrorLogEvent;
import com.polymer.system.entity.SysLogErrorEntity;
import com.polymer.system.mapper.SysLogErrorMapper;
import com.polymer.system.service.SysErrorLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
@Service
public class SysErrorLogServiceImpl implements SysErrorLogService {
    @Resource
    private SysLogErrorMapper sysLogErrorMapper;

    /**
     * 清理 exceedDay 天前的错误日志
     *
     * @param exceedDay   超过多少天就进行清理
     * @param deleteLimit 清理的间隔条数
     * @return 清理数量
     */
    @Override
    public Integer cleanErrorLog(Integer exceedDay, Integer deleteLimit) {
        int count = 0;
        LocalDateTime expireDate = LocalDateTime.now().minusDays(exceedDay);
        // 循环删除，直到没有满足条件的数据
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            int deleteCount = sysLogErrorMapper.deleteByCreateTimeLt(expireDate, deleteLimit);
            count += deleteCount;
            // 达到删除预期条数，说明到底了
            if (deleteCount < deleteLimit) {
                break;
            }
        }
        return count;
    }

    @Override
    @Async("logExecutor")
    public void asyncErrorProcess(ErrorLogEvent event) {
        SysLogErrorEntity entity = ConvertUtils.convertTo(event, SysLogErrorEntity::new);
        sysLogErrorMapper.insertSysLogError(entity);
    }
}
