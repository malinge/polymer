package com.polymer.generator.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.generator.common.query.Query;
import com.polymer.generator.entity.BaseClassEntity;

import java.util.List;

/**
 * 基类管理
 * polymer@126.com
 *
 */
public interface BaseClassService {

    PageResult<BaseClassEntity> page(Query query);

    List<BaseClassEntity> getList();

    BaseClassEntity save(BaseClassEntity entity);

    /**
     * 修改基类管理
     *
     * @param baseClass 基类管理
     * @return 结果
     */
    BaseClassEntity updateBaseClass(BaseClassEntity baseClass);

    void removeBatchByIds(List<Long> ids);

    /**
     * 查询基类管理
     *
     * @param id 基类管理主键
     * @return 基类管理
     */
    BaseClassEntity selectBaseClassById(Long id);
}