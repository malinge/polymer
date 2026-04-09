package com.polymer.system.service.impl;

import com.polymer.framework.common.cache.RedisCache;
import com.polymer.framework.common.constant.CacheConstants;
import com.polymer.framework.common.utils.StringUtils;
import com.polymer.framework.security.core.utils.TokenUtils;
import com.polymer.system.enums.SysParamsEnum;
import com.polymer.system.service.SysCaptchaService;
import com.polymer.system.service.SysParamsService;
import com.polymer.system.vo.SysCaptchaVO;
import com.polymer.system.vo.SysParamsVO;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 验证码
 *
 * @author polymer
 */
@Service
public class SysCaptchaServiceImpl implements SysCaptchaService {
    @Resource
    private RedisCache redisCache;
    @Resource
    private SysParamsService sysParamsService;

    /**
     * 生成验证码
     *
     * @return 结果
     */
    @Override
    public SysCaptchaVO generate() {

        // 生成验证码key
        String key = TokenUtils.generator();

        // 生成验证码
        SpecCaptcha captcha = new SpecCaptcha(150, 40);
        captcha.setLen(4);
        captcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        String image = captcha.toBase64();

        // 保存到缓存
        String redisKey = CacheConstants.SYS_CAPTCHA_KEY + key;
        redisCache.set(redisKey, captcha.text(), 300);

        // 封装返回数据
        SysCaptchaVO captchaVO = new SysCaptchaVO();
        captchaVO.setKey(key);
        captchaVO.setImage(image);

        return captchaVO;
    }

    /**
     * 验证码效验
     *
     * @param key  key
     * @param code 验证码
     * @return true：成功  false：失败
     */
    @Override
    public boolean validate(String key, String code) {
        // 如果关闭了验证码，则直接效验通过
        if (!isCaptchaEnabled()) {
            return true;
        }

        if (StringUtils.isBlank(key) || StringUtils.isBlank(code)) {
            return false;
        }

        // 获取验证码
        String captcha = getCache(key);

        // 效验成功
        return code.equalsIgnoreCase(captcha);
    }

    /**
     * 是否开启登录验证码
     *
     * @return true：开启  false：关闭
     */
    @Override
    public boolean isCaptchaEnabled() {
        SysParamsVO paramsVO = sysParamsService.getParamsByparamKey(SysParamsEnum.LOGIN_CAPTCHA.name());
        return Boolean.parseBoolean(paramsVO.getParamValue());
    }

    /**
     * 获取缓存验证码
     *
     * @param key key
     * @return 验证码
     */
    private String getCache(String key) {
        key = CacheConstants.SYS_CAPTCHA_KEY + key;
        String captcha = (String) redisCache.get(key);
        // 删除验证码
        if (captcha != null) {
            redisCache.delete(key);
        }
        return captcha;
    }

}
