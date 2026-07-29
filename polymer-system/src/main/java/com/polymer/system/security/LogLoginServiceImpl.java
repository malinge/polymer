package com.polymer.system.security;

import com.polymer.framework.security.core.service.LogLoginService;
import com.polymer.system.service.SysLogLoginService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class LogLoginServiceImpl implements LogLoginService {
    @Resource
    private SysLogLoginService sysLogLoginService;

    @Override
    public int saveLogLogin(String username, Integer status, Integer operation) {
        return sysLogLoginService.saveLogLogin(username, status, operation);
    }
}
