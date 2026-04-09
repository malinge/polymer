package com.polymer.quartz.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.quartz.query.ScheduleJobQuery;
import com.polymer.quartz.vo.ScheduleJobVO;

import java.util.List;

/**
 * 定时任务
 *
 * @author polymer
 */
public interface ScheduleJobService {

    /**
     * 根据定时任务查询获取分页定时任务
     *
     * @param query 定时任务查询
     * @return 结果
     */
    PageResult<ScheduleJobVO> page(ScheduleJobQuery query);

    /**
     * 保存定时任务
     *
     * @param vo 定时任务
     */
    ScheduleJobVO save(ScheduleJobVO vo);

    /**
     * 更新定时任务
     *
     * @param vo 定时任务
     */
    ScheduleJobVO update(ScheduleJobVO vo);

    /**
     * 删除定时任务
     *
     * @param idList 定时任务id集合
     */
    void delete(List<Long> idList);

    /**
     * 执行定时任务
     *
     * @param vo 定时任务
     */
    void run(ScheduleJobVO vo);

    /**
     * 更新定时任务状态
     *
     * @param vo 定时任务
     */
    void changeStatus(ScheduleJobVO vo);

    /**
     * 根据定时任务主键查询定时任务
     *
     * @param id 定时任务主键
     */
    ScheduleJobVO getById(Long id);
}