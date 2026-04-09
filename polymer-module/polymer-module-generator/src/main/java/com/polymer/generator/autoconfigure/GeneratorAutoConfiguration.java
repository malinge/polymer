package com.polymer.generator.autoconfigure;

import com.polymer.framework.security.core.properties.GeneratorProperties;
import com.polymer.generator.config.template.GeneratorConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * spring boot starter AutoConfiguration
 * polymer@126.com
 */
@Configuration
@EnableConfigurationProperties(GeneratorProperties.class)
public class GeneratorAutoConfiguration {
    @Resource
    private GeneratorProperties properties;

    @Bean
    GeneratorConfig generatorConfig() {
        return new GeneratorConfig(properties.getTemplate());
    }

}
