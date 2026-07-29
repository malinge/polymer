package com.polymer.system.service.impl;

import com.polymer.framework.common.exception.ErrorCode;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.security.core.cache.TokenStoreCache;
import com.polymer.framework.security.core.properties.SecurityProperties;
import com.polymer.framework.security.core.user.UserDetail;
import com.polymer.framework.security.core.utils.TokenUtils;
import com.polymer.system.entity.SysUserEntity;
import com.polymer.system.entity.SysUserTokenEntity;
import com.polymer.system.mapper.SysUserMapper;
import com.polymer.system.mapper.SysUserTokenMapper;
import com.polymer.system.service.SysUserDetailsService;
import com.polymer.system.service.SysUserTokenService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * BelongsProject: polymer
 * BelongsPackage: com.polymer.system.service.impl
 * CreateTime: 2023-08-18  15:53
 * Description: 用户token
 *
 * @author polymer
 * @version 2.0
 */
@Service
public class SysUserTokenServiceImpl implements SysUserTokenService {
    @Resource
    private TokenStoreCache tokenStoreCache;
    @Resource
    private SysUserDetailsService sysUserDetailsService;
    @Resource
    private SecurityProperties securityProperties;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserTokenMapper sysUserTokenMapper;

    /**
     * 根据用户ID，生成用户Token
     *
     * @param userId 用户ID
     * @return 用户Token
     */
    @Override
    public SysUserTokenEntity createToken(Long userId) {
        // 生成token
        String accessToken = TokenUtils.generator();
        String refreshToken = TokenUtils.generator();

        SysUserTokenEntity entity = new SysUserTokenEntity();
        entity.setUserId(userId);
        entity.setAccessToken(accessToken);
        entity.setRefreshToken(refreshToken);

        // 过期时间
        LocalDateTime now = LocalDateTime.now();
        entity.setAccessTokenExpire(now.plusSeconds(securityProperties.getAccessTokenExpire()));
        entity.setRefreshTokenExpire(now.plusSeconds(securityProperties.getRefreshTokenExpire()));

        // 是否存在Token
        SysUserTokenEntity tokenEntity = sysUserTokenMapper.selectUserTokenOne(userId);
        if (tokenEntity == null) {
            sysUserTokenMapper.insertUserToken(entity);
        } else {
            entity.setId(tokenEntity.getId());
            sysUserTokenMapper.updateSysUserToken(entity);
        }

        return entity;
    }

    /**
     * 根据refreshToken，生成新Token
     *
     * @param refreshToken refreshToken
     * @return 用户Token
     */
    @Override
    public SysUserTokenEntity refreshToken(String refreshToken) {
        // 不存在，则表示refreshToken错误，或者已过期
        SysUserTokenEntity entity = sysUserTokenMapper.selectOneByRefreshToken(refreshToken);
        if (entity == null) {
            throw new ServiceException(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        // 删除缓存信息
        tokenStoreCache.deleteUser(entity.getAccessToken());

        // 生成新 accessToken
        String accessToken = TokenUtils.generator();
        entity.setAccessToken(accessToken);
        entity.setAccessTokenExpire(LocalDateTime.now().plusSeconds(securityProperties.getAccessTokenExpire()));

        // 更新
        sysUserTokenMapper.updateSysUserToken(entity);

        // 设置用户权限信息
        SysUserEntity user = sysUserMapper.selectSysUserById(entity.getUserId());
        UserDetail userDetail = ConvertUtils.convertTo(user, UserDetail::new);
        sysUserDetailsService.getUserDetails(userDetail);

        // 保存用户信息到缓存
        tokenStoreCache.saveUser(accessToken, userDetail);

        return entity;
    }

    /**
     * Token过期
     *
     * @param userId 用户ID
     */
    @Override
    public void expireToken(Long userId) {
        SysUserTokenEntity entity = new SysUserTokenEntity();
        entity.setAccessTokenExpire(LocalDateTime.now());
        entity.setRefreshTokenExpire(LocalDateTime.now());
        entity.setUserId(userId);
        sysUserTokenMapper.updateByUserId(entity);
    }

    /**
     * 根据角色ID，更新用户缓存权限
     *
     * @param roleId 角色ID
     */

    @Override
    @Async("tokenExecutor")
    public void updateCacheAuthByRoleId(Long roleId) {
        // 根据角色ID，查询用户 access_token 列表
        List<String> accessTokenList = sysUserTokenMapper.getOnlineAccessTokenListByRoleId(roleId, LocalDateTime.now());

        accessTokenList.forEach(this::updateCacheAuth);
    }

    /**
     * 根据用户ID，更新用户缓存权限
     *
     * @param userId 用户ID
     */
    @Override
    @Async("tokenExecutor")
    public void updateCacheAuthByUserId(Long userId) {
        // 根据用户ID，查询用户 access_token 列表
        List<String> accessTokenList = sysUserTokenMapper.getOnlineAccessTokenListByUserId(userId, LocalDateTime.now());

        accessTokenList.forEach(this::updateCacheAuth);
    }

    /**
     * 根据用户主键查询一条用户token信息
     *
     * @param userId 用户主键
     * @return SysUserTokenEntity
     */
    @Override
    public SysUserTokenEntity selectUserTokenOne(Long userId) {
        return sysUserTokenMapper.selectUserTokenOne(userId);
    }

    /**
     * 根据accessToken，更新Cache里面的用户权限
     *
     * @param accessToken access_token
     */
    private void updateCacheAuth(String accessToken) {
        UserDetail user = tokenStoreCache.getUser(accessToken);
        // 用户不存在
        if (user == null) {
            return;
        }

        // 查询过期时间
        Long expire = tokenStoreCache.getExpire(accessToken);
        if (expire == null) {
            return;
        }

        // 设置用户权限信息
        sysUserDetailsService.getUserDetails(user);
        // 更新缓存
        tokenStoreCache.saveUser(accessToken, user, expire);

    }
}
