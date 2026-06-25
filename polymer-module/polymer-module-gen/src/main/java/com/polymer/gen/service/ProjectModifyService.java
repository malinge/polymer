package com.polymer.gen.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.gen.common.query.Query;
import com.polymer.gen.entity.ProjectModifyEntity;

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