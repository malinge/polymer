package com.polymer.generator.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.generator.common.query.Query;
import com.polymer.generator.entity.ProjectModifyEntity;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 项目名变更
 * polymer@126.com
 *
 */
public interface ProjectModifyService {

    PageResult<ProjectModifyEntity> page(Query query);

    byte[] download(ProjectModifyEntity project) throws IOException;

    ProjectModifyEntity save(ProjectModifyEntity entity);

    ProjectModifyEntity getById(Long id);

    ProjectModifyEntity updateById(@Valid ProjectModifyEntity entity);

    void removeByIds(List<Long> idList);
}