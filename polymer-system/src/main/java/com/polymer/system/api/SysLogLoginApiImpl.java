package com.polymer.system.api;

import com.polymer.api.system.SysLogLoginApi;
import com.polymer.system.service.SysLogLoginService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SysLogLoginApiImpl implements SysLogLoginApi {
    @Resource
    private SysLogLoginService sysLogLoginService;

    @Override
    public int saveLogLogin(String username, Integer status, Integer operation) {
        return sysLogLoginService.saveLogLogin(username, status, operation);
    }
}
