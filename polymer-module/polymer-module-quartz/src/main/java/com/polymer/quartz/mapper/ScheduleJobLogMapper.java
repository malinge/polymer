package com.polymer.quartz.mapper;

import com.polymer.quartz.entity.ScheduleJobLogEntity;
import com.polymer.quartz.query.ScheduleJobLogQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 定时任务日志
 *
 * @author polymer
 */
@Mapper
public interface ScheduleJobLogMapper {

    /**
     * 根据定时任务日志查询获取定时任务日志集合
     *
     * @param query 定时任务日志查询
     * @return 结果
     */
    List<ScheduleJobLogEntity> selectScheduleJobLogList(ScheduleJobLogQuery query);

    /**
     * 查询定时任务日志
     *
     * @param id 定时任务日志主键
     * @return 定时任务日志
     */
    ScheduleJobLogEntity selectScheduleJobLogById(Long id);

    /**
     * 新增定时任务日志
     *
     * @param scheduleJobLog 定时任务日志
     * @return 结果
     */
    int insertScheduleJobLog(ScheduleJobLogEntity scheduleJobLog);

    /**
     * 修改定时任务日志
     *
     * @param scheduleJobLog 定时任务日志
     * @return 结果
     */
    int updateScheduleJobLog(ScheduleJobLogEntity scheduleJobLog);

    /**
     * 删除定时任务日志
     *
     * @param id 定时任务日志主键
     * @return 结果
     */
    int deleteScheduleJobLogById(Long id);
}