package com.polymer.gen.controller;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.common.utils.FileUtils;
import com.polymer.gen.common.query.Query;
import com.polymer.gen.entity.ProjectModifyEntity;
import com.polymer.gen.service.ProjectModifyService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 项目名变更
 * polymer@126.com
 *
 */
@RestController
@RequestMapping("gen/project")
public class ProjectModifyController {
    @Resource
    private ProjectModifyService projectModifyService;

    @GetMapping("page")
    public Result<PageResult<ProjectModifyEntity>> page(@Valid Query query) {
        PageResult<ProjectModifyEntity> page = projectModifyService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    public Result<ProjectModifyEntity> get(@PathVariable("id") Long id) {
        ProjectModifyEntity entity = projectModifyService.getById(id);

        return Result.ok(entity);
    }

    @PostMapping
    public Result<ProjectModifyEntity> save(@RequestBody ProjectModifyEntity entity) {
        ProjectModifyEntity resEntity = projectModifyService.save(entity);

        return Result.ok(resEntity);
    }

    @PutMapping
    public Result<ProjectModifyEntity> update(@RequestBody @Valid ProjectModifyEntity entity) {
        ProjectModifyEntity resEntity = projectModifyService.updateById(entity);

        return Result.ok(resEntity);
    }

    @DeleteMapping
    public Result<String> delete(@RequestBody List<Long> idList) {
        projectModifyService.removeByIds(idList);

        return Result.ok();
    }

    /**
     * 源码下载
     */
    @GetMapping("download/{id}")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
        // 项目信息
        ProjectModifyEntity project = projectModifyService.getById(id);

        byte[] b = projectModifyService.download(project);

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        FileUtils.setAttachmentResponseHeader(response, project.getModifyProjectName()+".zip");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(b);
    }
}