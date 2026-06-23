package com.polymer.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.demo.service.DemoMultipleFilesService;
import com.polymer.demo.query.DemoMultipleFilesQuery;
import com.polymer.demo.vo.DemoMultipleFilesVO;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
* 多文件上传样例Controller
*
* @author polymer polymer@126.com
* @since 1.0.0 2026-06-23
*/
@RestController
@RequestMapping("demo/multipleFiles")
@Tag(name="多文件上传样例")
public class DemoMultipleFilesController {
    @Resource
    private DemoMultipleFilesService demoMultipleFilesService;

    @GetMapping("/page")
    @Operation(summary = "查询多文件上传样例分页")
    @PreAuthorize("hasAuthority('demo:multipleFiles:page')")
    public Result<PageResult<DemoMultipleFilesVO>> page(@ParameterObject @Valid DemoMultipleFilesQuery query){
        PageResult<DemoMultipleFilesVO> page = demoMultipleFilesService.page(query);

        return Result.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取多文件上传样例详细信息")
    @PreAuthorize("hasAuthority('demo:multipleFiles:info')")
    public Result<DemoMultipleFilesVO> getInfo(@PathVariable("id") Long id){
        DemoMultipleFilesVO vo = demoMultipleFilesService.selectDemoMultipleFilesById(id);

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "新增多文件上传样例")
    @PreAuthorize("hasAuthority('demo:multipleFiles:save')")
    public Result<DemoMultipleFilesVO> save(@RequestBody DemoMultipleFilesVO vo){
        DemoMultipleFilesVO resVo = demoMultipleFilesService.insertDemoMultipleFiles(vo);

        return Result.ok(resVo);
    }

    @PutMapping
    @Operation(summary = "修改多文件上传样例")
    @PreAuthorize("hasAuthority('demo:multipleFiles:update')")
    public Result<DemoMultipleFilesVO> update(@RequestBody @Valid DemoMultipleFilesVO vo){
        DemoMultipleFilesVO resVo = demoMultipleFilesService.updateDemoMultipleFiles(vo);

        return Result.ok(resVo);
    }

    @DeleteMapping
    @Operation(summary = "删除多文件上传样例")
    @PreAuthorize("hasAuthority('demo:multipleFiles:delete')")
    public Result<Integer> delete(@RequestBody List<Long> idList){
        int num = demoMultipleFilesService.deleteDemoMultipleFilesByIdList(idList);

        return Result.ok(num);
    }
}
