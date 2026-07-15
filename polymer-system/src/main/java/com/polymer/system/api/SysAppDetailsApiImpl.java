package com.polymer.system.api;

import com.polymer.api.system.SysAppDetailsApi;
import com.polymer.system.service.SysAppDetailsService;
import com.polymer.system.vo.SysAppDetailsVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SysAppDetailsApiImpl implements SysAppDetailsApi {
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
