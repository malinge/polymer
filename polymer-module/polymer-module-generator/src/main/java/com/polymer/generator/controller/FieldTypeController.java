package com.polymer.generator.controller;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.generator.common.query.Query;
import com.polymer.generator.entity.FieldTypeEntity;
import com.polymer.generator.service.FieldTypeService;
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
import java.util.Set;

/**
 * 字段类型管理
 * polymer@126.com
 *
 */
@RestController
@RequestMapping("generator/gen/fieldtype")
public class FieldTypeController {
    @Resource
    private FieldTypeService fieldTypeService;

    @GetMapping("page")
    public Result<PageResult<FieldTypeEntity>> page(Query query) {
        PageResult<FieldTypeEntity> page = fieldTypeService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    public Result<FieldTypeEntity> get(@PathVariable("id") Long id) {
        FieldTypeEntity data = fieldTypeService.getById(id);

        return Result.ok(data);
    }

    @GetMapping("list")
    public Result<Set<String>> list() {
        Set<String> set = fieldTypeService.getList();

        return Result.ok(set);
    }

    @PostMapping
    public Result<FieldTypeEntity> save(@RequestBody FieldTypeEntity entity) {
        FieldTypeEntity resEntity = fieldTypeService.save(entity);

        return Result.ok(resEntity);
    }

    @PutMapping
    public Result<FieldTypeEntity> update(@RequestBody FieldTypeEntity entity) {
        FieldTypeEntity resEntity = fieldTypeService.updateById(entity);

        return Result.ok(resEntity);
    }

    @DeleteMapping
    public Result<String> delete(@RequestBody Long[] ids) {
        fieldTypeService.removeBatchByIds(Arrays.asList(ids));

        return Result.ok();
    }
}