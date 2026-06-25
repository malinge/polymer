package com.polymer.gen.autoconfigure;

import com.polymer.gen.config.template.GeneratorConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * spring boot starter AutoConfiguration
 * polymer@126.com
 */
@Configuration
public class GeneratorAutoConfiguration {
    public static final String template = "/template/polymer";

    @Bean
    GeneratorConfig generatorConfig() {
        return new GeneratorConfig(template);
    }

}
