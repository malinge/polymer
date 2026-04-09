package com.polymer.system.task;

import com.polymer.system.service.SysErrorLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 物理删除 N 天前的错误日志的 Task
 *
 * @author polymer
 */

@Service
public class ErrorLogCleanTask {
    private static final Logger log = LoggerFactory.getLogger(ErrorLogCleanTask.class);
    @Resource
    private SysErrorLogService sysErrorLogService;

    /**
     * 清理超过（14）天的日志
     */
    private static final Integer JOB_CLEAN_RETAIN_DAY = 14;

    /**
     * 每次删除间隔的条数，如果值太高可能会造成数据库的压力过大
     */
    private static final Integer DELETE_LIMIT = 100;

    public void run(String params) {
        Integer count = sysErrorLogService.cleanErrorLog(JOB_CLEAN_RETAIN_DAY, DELETE_LIMIT);
        log.info("[run][定时执行清理错误日志数量 ({}) 个]", count);
    }
}
