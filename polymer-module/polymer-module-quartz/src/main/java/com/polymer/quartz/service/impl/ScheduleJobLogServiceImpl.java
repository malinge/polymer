package com.polymer.quartz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.quartz.entity.ScheduleJobLogEntity;
import com.polymer.quartz.mapper.ScheduleJobLogMapper;
import com.polymer.quartz.query.ScheduleJobLogQuery;
import com.polymer.quartz.service.ScheduleJobLogService;
import com.polymer.quartz.vo.ScheduleJobLogVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务日志
 *
 * @author polymer
 */
@Service
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {
    @Resource
    private ScheduleJobLogMapper scheduleJobLogMapper;

    /**
     * 根据定时任务日志查询获取分页定时任务日志
     *
     * @param query 定时任务日志查询
     * @return 结果
     */
    @Override
    public PageResult<ScheduleJobLogVO> page(ScheduleJobLogQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<ScheduleJobLogEntity> entityList = scheduleJobLogMapper.selectScheduleJobLogList(query);
        PageInfo<ScheduleJobLogEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, ScheduleJobLogVO::new), pageInfo.getTotal());
    }

    /**
     * 根据定时任务日志主键获取定时任务日志
     *
     * @param id 定时任务日志主键
     * @return 结果
     */
    @Override
    public ScheduleJobLogVO getById(Long id) {
        ScheduleJobLogEntity entity = scheduleJobLogMapper.selectScheduleJobLogById(id);
        return ConvertUtils.convertTo(entity, ScheduleJobLogVO::new);
    }

    /**
     * 保存定时任务日志
     *
     * @param vo 定时任务日志信息
     */
    @Override
    public void save(ScheduleJobLogVO vo) {
        scheduleJobLogMapper.insertScheduleJobLog(ConvertUtils.convertTo(vo, ScheduleJobLogEntity::new));
    }


}