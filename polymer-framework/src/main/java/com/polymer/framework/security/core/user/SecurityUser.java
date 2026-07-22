package com.polymer.framework.security.core.user;

import com.polymer.api.system.user.UserDetail;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户
 *
 * @author polymer
 */
public class SecurityUser {

    /**
     * 获取用户信息
     */
    public static UserDetail getUser() {
        UserDetail user;
        try {
            user = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }

        return user;
    }

    /**
     * 获取用户ID
     */
    public static Long getUserId() {
        UserDetail user = getUser();
        if (user == null) {
            return null;
        }

        return user.getId();
    }

    /**
     * 获取用户昵称
     */
    public static String getRealName() {
        UserDetail user = getUser();
        if (user == null) {
            return "";
        }

        return user.getRealName();
    }

    /**
     * 获取用户昵称
     */
    public static String getUsername() {
        UserDetail user = getUser();
        if (user == null) {
            return "";
        }

        return user.getUsername();
    }

}