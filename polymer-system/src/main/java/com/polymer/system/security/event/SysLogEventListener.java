package com.polymer.system.security.event;

import com.polymer.framework.logger.event.ErrorLogEvent;
import com.polymer.framework.logger.event.OperateLogEvent;
import com.polymer.system.service.SysErrorLogService;
import com.polymer.system.service.SysLogOperateService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SysLogEventListener {
    @Resource
    private SysErrorLogService sysErrorLogService;
    @Resource
    private SysLogOperateService sysLogOperateService;

    @EventListener
    public void onErrorLogEvent(ErrorLogEvent event) {
        // 同步调用异步方法
        //  Spring 代理机制导致的 @Async 失效：同一个类内部直接调用 @Async 方法时，
        //  实际执行的是原始方法（同步），而不是通过代理执行的异步逻辑
        //被 this 直接调用，Spring 的代理对象并未参与，所以 @Async 注解被忽略，
        // 方法在事件发布者的当前线程（通常是 Web 容器线程）中同步执行
        sysErrorLogService.asyncErrorProcess(event);
    }

    @EventListener
    public void onOperateLogEvent(OperateLogEvent event) {
        // 同步调用异步方法
        sysLogOperateService.asyncOperateProcess(event);
    }
}
