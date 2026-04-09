package com.polymer.system.task;

import com.polymer.system.service.SysAppDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 重新加载appid和appSecret 定时任务
 *
 * @author polymer
 */

@Service
public class AppIdCleanTask {
    private static final Logger log = LoggerFactory.getLogger(AppIdCleanTask.class);
    @Resource
    private SysAppDetailsService sysAppDetailsService;

    public void run(String params) {
        sysAppDetailsService.reloadSysAppDetails();
        log.info("定时执行重新加载");
    }
}
