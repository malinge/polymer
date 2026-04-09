package com.polymer.generator.controller;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.generator.common.query.Query;
import com.polymer.generator.entity.BaseClassEntity;
import com.polymer.generator.service.BaseClassService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 基类管理
 * polymer@126.com
 *
 */
@RestController
@RequestMapping("generator/gen/baseclass")
public class BaseClassController {
    @Resource
    private BaseClassService baseClassService;

    @GetMapping("page")
    public Result<PageResult<BaseClassEntity>> page(Query query) {
        PageResult<BaseClassEntity> page = baseClassService.page(query);

        return Result.ok(page);
    }

    @GetMapping("list")
    public Result<List<BaseClassEntity>> list() {
        List<BaseClassEntity> list = baseClassService.getList();

        return Result.ok(list);
    }

    @GetMapping("{id}")
    public Result<BaseClassEntity> get(@PathVariable("id") Long id) {
        BaseClassEntity data = baseClassService.selectBaseClassById(id);

        return Result.ok(data);
    }

    @PostMapping
    public Result<BaseClassEntity> save(@RequestBody BaseClassEntity entity) {
        BaseClassEntity resEntity = baseClassService.save(entity);

        return Result.ok(resEntity);
    }

    @PutMapping
    public Result<BaseClassEntity> update(@RequestBody BaseClassEntity entity) {
        BaseClassEntity resEntity = baseClassService.updateBaseClass(entity);

        return Result.ok(resEntity);
    }

    @DeleteMapping
    public Result<String> delete(@RequestBody Long[] ids) {
        baseClassService.removeBatchByIds(Arrays.asList(ids));

        return Result.ok();
    }
}