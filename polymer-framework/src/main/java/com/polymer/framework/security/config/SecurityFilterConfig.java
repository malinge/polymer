package com.polymer.framework.security.config;

import com.polymer.framework.security.core.exception.SecurityAuthenticationEntryPoint;
import com.polymer.framework.security.core.properties.GeneratorProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import java.util.List;

/**
 * Spring SecurityFilter 配置文件
 *
 * @author polymer
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityFilterConfig {
    @Resource
    private OncePerRequestFilter authenticationTokenFilter;
    @Resource
    private PermitResource permitResource;

    @Resource
    private GeneratorProperties generatorProperties;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 忽略授权的地址列表
        List<String> permitList = permitResource.getPermitList();
        if (generatorProperties != null && !generatorProperties.isEnabled()) {
            // 移除代码生成器路径
            permitList.remove(generatorProperties.getPath());
        }
        String[] permits = permitList.toArray(new String[0]);

        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    // 先配置的生效
                    // 先配置代码生成器的安全规则
                    if (generatorProperties != null && !generatorProperties.isEnabled()) {
                        // 禁用状态：拒绝所有代码生成器路径的访问
                        // 注意：这里的路径必须与 permitList 中的路径完全一致
                        auth.antMatchers(generatorProperties.getPath()).denyAll();
                    }

                    // 然后配置 permitAll 的路径
                    auth.antMatchers(permits).permitAll()
                            .antMatchers(HttpMethod.OPTIONS).permitAll();

                    // 最后配置其他请求
                    auth.anyRequest().authenticated();
                })
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new SecurityAuthenticationEntryPoint()))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

}
