package com.polymer.framework.mybatis.core.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity基类
 *
 * @author polymer
 */
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;

    //创建者
    private Long creator;

    //更新者
    private Long updater;

    //创建时间
    private LocalDateTime createTime;

    //更新时间
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Long getUpdater() {
        return updater;
    }

    public void setUpdater(Long updater) {
        this.updater = updater;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

}
