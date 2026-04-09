package com.polymer.framework.idempotent.config;

import com.polymer.framework.idempotent.core.aspectj.RepeatSubmitAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 幂等功能配置
 *
 * @author polymer
 */
@Configuration
public class IdempotentConfig {

    @Bean
    public RepeatSubmitAspect repeatSubmitAspect() {
        return new RepeatSubmitAspect();
    }

}
