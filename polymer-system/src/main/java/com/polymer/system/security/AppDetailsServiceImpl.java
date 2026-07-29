package com.polymer.system.security;

import com.polymer.framework.security.core.service.AppDetailsService;
import com.polymer.system.service.SysAppDetailsService;
import com.polymer.system.vo.SysAppDetailsVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AppDetailsServiceImpl implements AppDetailsService {
    @Resource
    private SysAppDetailsService sysAppDetailsService;
    @Override
    public String getAppSecretByAppId(String appId) {
        SysAppDetailsVO vo = sysAppDetailsService.getAppSecretByAppId(appId);
        if(vo == null){
            return "";
        }
        return vo.getAppSecret();
    }
}
