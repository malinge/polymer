package com.polymer.system.controller;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.logger.annotations.OperateLog;
import com.polymer.framework.logger.enums.OperateTypeEnum;
import com.polymer.system.query.SysPostQuery;
import com.polymer.system.service.SysPostService;
import com.polymer.system.vo.SysPostVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
 * 岗位管理
 *
 * @author polymer
 */
@RestController
@RequestMapping("sys/post")
@Tag(name = "岗位管理")
public class SysPostController {
    @Resource
    private SysPostService sysPostService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:post:page')")
    public Result<PageResult<SysPostVO>> page(@ParameterObject @Valid SysPostQuery query) {
        PageResult<SysPostVO> page = sysPostService.page(query);

        return Result.ok(page);
    }

    @GetMapping("list")
    @Operation(summary = "列表")
    public Result<List<SysPostVO>> list() {
        List<SysPostVO> list = sysPostService.getList();

        return Result.ok(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:post:info')")
    public Result<SysPostVO> get(@PathVariable("id") Long id) {
        SysPostVO vo = sysPostService.getById(id);

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:post:save')")
    public Result<SysPostVO> save(@RequestBody SysPostVO vo) {
        SysPostVO resVO = sysPostService.save(vo);

        return Result.ok(resVO);
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:post:update')")
    public Result<SysPostVO> update(@RequestBody @Valid SysPostVO vo) {
        SysPostVO resVo = sysPostService.update(vo);
        return Result.ok(resVo);
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:post:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        sysPostService.delete(idList);

        return Result.ok();
    }
}