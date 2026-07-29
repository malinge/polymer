package com.polymer.monitor.controller;

import com.polymer.framework.common.cache.RedisCache;
import com.polymer.framework.common.constant.CacheConstants;
import com.polymer.framework.common.pojo.PageParam;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.common.utils.StringUtils;
import com.polymer.framework.security.core.cache.TokenStoreCache;
import com.polymer.framework.security.core.user.UserDetail;
import com.polymer.monitor.vo.UserOnlineVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("monitor/user")
@Tag(name = "在线用户监控")
public class UserOnlineController {
    @Resource
    private TokenStoreCache tokenStoreCache;
    @Resource
    private RedisCache redisCache;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('monitor:user:all')")
    public Result<PageResult<UserOnlineVO>> page(@ParameterObject @Valid PageParam pageParam) {
        // 获取登录用户的全部key
        List<String> keys = tokenStoreCache.getUserKeyList();

        // 2. 手动逻辑分页
        int pageNum = pageParam.getPageNo(), pageSize = pageParam.getPageSize();
        // 逻辑分页
        List<String> keyList = keys.stream()
                .skip((long) (pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        List<UserOnlineVO> userOnlineList = new ArrayList<>();
        keyList.forEach(key -> {
            UserDetail user = (UserDetail) redisCache.get(key);
            if (user != null) {
                UserOnlineVO userOnlineVO = new UserOnlineVO();
                userOnlineVO.setId(user.getId());
                userOnlineVO.setUsername(user.getUsername());
                userOnlineVO.setRealName(user.getRealName());
                userOnlineVO.setGender(user.getGender());
                userOnlineVO.setEmail(user.getEmail());
                userOnlineVO.setAccessToken(key.replace(CacheConstants.SYS_TOKEN_KEY, ""));

                userOnlineList.add(userOnlineVO);
            }

        });

        return Result.ok(new PageResult<>(userOnlineList, keys.size()));
    }

    @DeleteMapping("{accessToken}")
    @Operation(summary = "强制退出")
    @PreAuthorize("hasAuthority('monitor:user:all')")
    public Result<String> forceLogout(@PathVariable("accessToken") String accessToken) {
        // token不能为空
        if (StringUtils.isBlank(accessToken)) {
            Result.error("token不能为空");
        }

        // 删除用户信息
        tokenStoreCache.deleteUser(accessToken);

        return Result.ok();
    }
}
