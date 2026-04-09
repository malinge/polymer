package com.polymer.quartz.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.quartz.query.ScheduleJobLogQuery;
import com.polymer.quartz.vo.ScheduleJobLogVO;

/**
 * 定时任务日志
 *
 * @author polymer
 */
public interface ScheduleJobLogService {

    /**
     * 根据定时任务日志查询获取分页定时任务日志
     *
     * @param query 定时任务日志查询
     * @return 结果
     */
    PageResult<ScheduleJobLogVO> page(ScheduleJobLogQuery query);

    /**
     * 根据定时任务日志主键获取定时任务日志
     *
     * @param id 定时任务日志主键
     * @return 结果
     */
    ScheduleJobLogVO getById(Long id);

    /**
     * 保存定时任务日志
     *
     * @param logVO 定时任务日志信息
     */
    void save(ScheduleJobLogVO logVO);
}