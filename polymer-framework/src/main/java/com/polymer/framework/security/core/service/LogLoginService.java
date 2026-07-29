package com.polymer.framework.security.core.service;

@FunctionalInterface
public interface LogLoginService {
    int saveLogLogin(String username, Integer status, Integer operation);
}
