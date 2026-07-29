package com.polymer.framework.security.core.service;

@FunctionalInterface
public interface AppDetailsService {

    String getAppSecretByAppId(String appId);
}
