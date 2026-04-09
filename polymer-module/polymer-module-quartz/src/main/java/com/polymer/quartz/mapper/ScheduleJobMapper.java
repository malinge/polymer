package com.polymer.quartz.mapper;

import com.polymer.quartz.entity.ScheduleJobEntity;
import com.polymer.quartz.query.ScheduleJobQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 定时任务
 *
 * @author polymer
 */
@Mapper
public interface ScheduleJobMapper {

    /**
     * 根据定时任务查询获取定时任务集合
     *
     * @param query 定时任务查询
     * @return 结果
     */
    List<ScheduleJobEntity> selectScheduleJobList(ScheduleJobQuery query);

    /**
     * 查询定时任务
     *
     * @param id 定时任务主键
     * @return 定时任务
     */
    ScheduleJobEntity selectScheduleJobById(Long id);

    /**
     * 新增定时任务
     *
     * @param scheduleJob 定时任务
     * @return 结果
     */
    int insertScheduleJob(ScheduleJobEntity scheduleJob);

    /**
     * 修改定时任务
     *
     * @param scheduleJob 定时任务
     * @return 结果
     */
    int updateScheduleJob(ScheduleJobEntity scheduleJob);

    /**
     * 删除定时任务
     *
     * @param id 定时任务主键
     * @return 结果
     */
    int deleteScheduleJobById(Long id);
}