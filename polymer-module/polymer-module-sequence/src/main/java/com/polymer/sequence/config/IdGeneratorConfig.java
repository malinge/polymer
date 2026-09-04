package com.polymer.sequence.config;

import com.polymer.sequence.range.SnowflakeIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGeneratorConfig {

    @Bean
    public SnowflakeIdGenerator snowflakeIdGenerator() {
        long workerId = 1L;
        return new SnowflakeIdGenerator(workerId);
    }
}
