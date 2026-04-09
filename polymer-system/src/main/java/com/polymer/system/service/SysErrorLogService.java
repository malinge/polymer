package com.polymer.system.service;

import com.polymer.framework.logger.event.ErrorLogEvent;

public interface SysErrorLogService {

    /**
     * 清理 exceedDay 天前的错误日志
     *
     * @param exceedDay   超过多少天就进行清理
     * @param deleteLimit 清理的间隔条数
     * @return 清理数量
     */
    Integer cleanErrorLog(Integer exceedDay, Integer deleteLimit);

    void asyncErrorProcess(ErrorLogEvent event);
}
