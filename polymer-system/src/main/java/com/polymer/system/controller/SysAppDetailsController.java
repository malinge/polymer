package com.polymer.system.controller;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.system.query.SysAppDetailsQuery;
import com.polymer.system.service.SysAppDetailsService;
import com.polymer.system.vo.SysAppDetailsVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * app信息表Controller
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2025-05-09
 */
@RestController
@RequestMapping("system/appDetails")
@Tag(name = "app信息表")
public class SysAppDetailsController {
    @Resource
    private SysAppDetailsService sysAppDetailsService;

    @GetMapping("/page")
    @Operation(summary = "查询app信息表分页")
    @PreAuthorize("hasAuthority('system:appDetails:page')")
    public Result<PageResult<SysAppDetailsVO>> page(@ParameterObject @Valid SysAppDetailsQuery query) {
        PageResult<SysAppDetailsVO> page = sysAppDetailsService.page(query);

        return Result.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取app信息表详细信息")
    @PreAuthorize("hasAuthority('system:appDetails:info')")
    public Result<SysAppDetailsVO> getInfo(@PathVariable("id") Integer id) {
        SysAppDetailsVO vo = sysAppDetailsService.selectSysAppDetailsById(id);

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "新增app信息表")
    @PreAuthorize("hasAuthority('system:appDetails:save')")
    public Result<SysAppDetailsVO> save(@RequestBody SysAppDetailsVO vo) {
        SysAppDetailsVO resVo = sysAppDetailsService.insertSysAppDetails(vo);

        return Result.ok(resVo);
    }

    @PutMapping
    @Operation(summary = "修改app信息表")
    @PreAuthorize("hasAuthority('system:appDetails:update')")
    public Result<SysAppDetailsVO> update(@RequestBody @Valid SysAppDetailsVO vo) {
        SysAppDetailsVO resVo = sysAppDetailsService.updateSysAppDetails(vo);

        return Result.ok(resVo);
    }

}