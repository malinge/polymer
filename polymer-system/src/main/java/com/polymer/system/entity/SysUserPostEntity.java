package com.polymer.system.entity;

import com.polymer.framework.mybatis.core.entity.BaseEntity;

/**
 * 用户岗位关系
 *
 * @author polymer
 */
public class SysUserPostEntity extends BaseEntity {

    //用户ID
    private Long userId;

    //岗位ID
    private Long postId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}