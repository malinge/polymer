package com.polymer.system.security.config;

import com.polymer.framework.security.core.mobile.MobileAuthenticationProvider;
import com.polymer.framework.security.core.mobile.MobileUserDetailsService;
import com.polymer.framework.security.core.mobile.MobileVerifyCodeService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * SpringSecurity 配置文件
 *
 * @author polymer
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private MobileUserDetailsService mobileUserDetailsService;
    @Resource
    private MobileVerifyCodeService mobileVerifyCodeService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    MobileAuthenticationProvider mobileAuthenticationProvider() {
        return new MobileAuthenticationProvider(mobileUserDetailsService, mobileVerifyCodeService);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providerList = new ArrayList<>();
        providerList.add(daoAuthenticationProvider());
        providerList.add(mobileAuthenticationProvider());

        ProviderManager providerManager = new ProviderManager(providerList);
        providerManager.setAuthenticationEventPublisher(new DefaultAuthenticationEventPublisher(applicationEventPublisher));

        return providerManager;
    }
}
