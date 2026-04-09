package com.polymer.quartz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.common.utils.SpringUtils;
import com.polymer.quartz.entity.ScheduleJobEntity;
import com.polymer.quartz.enums.ScheduleStatusEnum;
import com.polymer.quartz.mapper.ScheduleJobMapper;
import com.polymer.quartz.query.ScheduleJobQuery;
import com.polymer.quartz.service.ScheduleJobService;
import com.polymer.quartz.utils.CronUtils;
import com.polymer.quartz.utils.ScheduleUtils;
import com.polymer.quartz.vo.ScheduleJobVO;
import org.apache.commons.lang3.ArrayUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务
 *
 * @author polymer
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {
    @Resource
    private ScheduleJobMapper scheduleJobMapper;
    @Resource
    private Scheduler scheduler;

    /**
     * 启动项目时，初始化定时器
     * 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void init() throws SchedulerException {
        scheduler.clear();
        List<ScheduleJobEntity> scheduleJobList = scheduleJobMapper.selectScheduleJobList(null);
        for (ScheduleJobEntity scheduleJob : scheduleJobList) {
            ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        }
    }

    /**
     * 根据定时任务查询获取分页定时任务
     *
     * @param query 定时任务查询
     * @return 结果
     */
    @Override
    public PageResult<ScheduleJobVO> page(ScheduleJobQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<ScheduleJobEntity> entityList = scheduleJobMapper.selectScheduleJobList(query);
        PageInfo<ScheduleJobEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, ScheduleJobVO::new), pageInfo.getTotal());
    }

    /**
     * 保存定时任务
     *
     * @param vo 定时任务
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ScheduleJobVO save(ScheduleJobVO vo) {
        if (!CronUtils.isValid(vo.getCronExpression())) {
            throw new ServiceException("操作失败，Cron表达式不正确");
        }
        // 检查Bean的合法性
        checkBean(vo.getBeanName());
        ScheduleJobEntity entity = ConvertUtils.convertTo(vo, ScheduleJobEntity::new);
        entity.setStatus(ScheduleStatusEnum.PAUSE.getValue());
        if (scheduleJobMapper.insertScheduleJob(entity) > 0) {
            ScheduleUtils.createScheduleJob(scheduler, entity);
        }
        return ConvertUtils.convertTo(entity, ScheduleJobVO::new);
    }

    private void checkBean(String beanName) {
        // 为避免执行jdbcTemplate等类，只允许添加有@Service注解的Bean
        String[] serviceBeans = SpringUtils.getApplicationContext().getBeanNamesForAnnotation(Service.class);
        if (!ArrayUtils.contains(serviceBeans, beanName)) {
            throw new ServiceException("只允许添加有@Service注解的Bean！");
        }
    }

    /**
     * 更新定时任务
     *
     * @param vo 定时任务
     */
    @Override
    public ScheduleJobVO update(ScheduleJobVO vo) {
        // 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }
        if (!CronUtils.isValid(vo.getCronExpression())) {
            throw new ServiceException("操作失败，Cron表达式不正确");
        }

        // 检查Bean的合法性
        checkBean(vo.getBeanName());
        ScheduleJobEntity entity = ConvertUtils.convertTo(vo, ScheduleJobEntity::new);
        // 更新定时任务
        if (scheduleJobMapper.updateScheduleJob(entity) == 1) {
            ScheduleJobEntity scheduleJob = scheduleJobMapper.selectScheduleJobById(entity.getId());
            ScheduleUtils.updateSchedulerJob(scheduler, scheduleJob);
        }else {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }

        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        ScheduleJobEntity updatedEntity = scheduleJobMapper.selectScheduleJobById(vo.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return ConvertUtils.convertTo(updatedEntity, ScheduleJobVO::new);
    }

    /**
     * 删除定时任务
     *
     * @param idList 定时任务id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            ScheduleJobEntity scheduleJob = scheduleJobMapper.selectScheduleJobById(id);

            // 删除定时任务
            if (scheduleJobMapper.deleteScheduleJobById(id) > 1) {
                ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    /**
     * 执行定时任务
     *
     * @param vo 定时任务
     */
    @Override
    public void run(ScheduleJobVO vo) {
        ScheduleJobEntity scheduleJob = scheduleJobMapper.selectScheduleJobById(vo.getId());
        if (scheduleJob == null) {
            return;
        }

        ScheduleUtils.run(scheduler, scheduleJob);
    }

    /**
     * 更新定时任务状态
     *
     * @param vo 定时任务
     */
    @Override
    public void changeStatus(ScheduleJobVO vo) {
        ScheduleJobEntity scheduleJob = scheduleJobMapper.selectScheduleJobById(vo.getId());
        if (scheduleJob == null) {
            return;
        }

        // 更新数据
        scheduleJob.setStatus(vo.getStatus());
        scheduleJobMapper.updateScheduleJob(scheduleJob);

        if (ScheduleStatusEnum.PAUSE.getValue() == vo.getStatus()) {
            ScheduleUtils.pauseJob(scheduler, scheduleJob);
        } else if (ScheduleStatusEnum.NORMAL.getValue() == vo.getStatus()) {
            ScheduleUtils.resumeJob(scheduler, scheduleJob);
        }
    }

    /**
     * 根据定时任务主键查询定时任务
     *
     * @param id 定时任务主键
     */
    @Override
    public ScheduleJobVO getById(Long id) {
        ScheduleJobEntity entity = scheduleJobMapper.selectScheduleJobById(id);
        return ConvertUtils.convertTo(entity, ScheduleJobVO::new);
    }

}