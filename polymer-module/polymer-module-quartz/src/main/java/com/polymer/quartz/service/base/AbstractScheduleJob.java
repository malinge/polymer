package com.polymer.quartz.service.base;

import com.polymer.framework.common.utils.ExceptionUtils;
import com.polymer.framework.common.utils.SpringUtils;
import com.polymer.quartz.entity.ScheduleJobEntity;
import com.polymer.quartz.enums.ScheduleStatusEnum;
import com.polymer.quartz.service.ScheduleJobLogService;
import com.polymer.quartz.utils.ScheduleUtils;
import com.polymer.quartz.vo.ScheduleJobLogVO;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public abstract class AbstractScheduleJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(AbstractScheduleJob.class);
    private static final ThreadLocal<LocalDateTime> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) {
        ScheduleJobEntity scheduleJob = new ScheduleJobEntity();
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY), scheduleJob);

        try {
            threadLocal.set(LocalDateTime.now());
            doExecute(scheduleJob);
            saveLog(scheduleJob, null);
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：{}", scheduleJob.getId(), e);
            saveLog(scheduleJob, e);
        }
    }

    /**
     * 执行spring bean方法
     */
    protected void doExecute(ScheduleJobEntity scheduleJob) throws Exception {
        log.info("准备执行任务，任务ID：{}", scheduleJob.getId());

        Object bean = SpringUtils.getBean(scheduleJob.getBeanName());
        Method method = bean.getClass().getDeclaredMethod(scheduleJob.getMethod(), String.class);
        method.invoke(bean, scheduleJob.getParams());

        log.info("任务执行完毕，任务ID：{}", scheduleJob.getId());
    }

    /**
     * 保存 log
     */
    protected void saveLog(ScheduleJobEntity scheduleJob, Exception e) {
        LocalDateTime startTime = threadLocal.get();
        threadLocal.remove();

        // 执行总时长
        long times = System.currentTimeMillis() - startTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();

        // 保存执行记录
        ScheduleJobLogVO log = new ScheduleJobLogVO();
        log.setJobId(scheduleJob.getId());
        log.setJobName(scheduleJob.getJobName());
        log.setJobGroup(scheduleJob.getJobGroup());
        log.setBeanName(scheduleJob.getBeanName());
        log.setMethod(scheduleJob.getMethod());
        log.setParams(scheduleJob.getParams());
        log.setTimes(times);
        log.setCreateTime(LocalDateTime.now());

        if (e != null) {
            log.setStatus(ScheduleStatusEnum.PAUSE.getValue());
            String error = StringUtils.substring(ExceptionUtils.getExceptionMessage(e), 0, 2000);
            log.setError(error);
        } else {
            log.setStatus(ScheduleStatusEnum.NORMAL.getValue());
        }

        // 保存日志
        SpringUtils.getBean(ScheduleJobLogService.class).save(log);
    }

}
