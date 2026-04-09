package com.polymer.system.controller;

import com.polymer.framework.common.pojo.Result;
import com.polymer.system.query.SysCityQuery;
import com.polymer.system.service.SysCityService;
import com.polymer.system.vo.SysCityVO;
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
import java.util.List;

/**
* 城市管理Controller
*
* @author polymer zhangxf@126.com
* @since 1.0.0 2025-07-18
*/
@RestController
@RequestMapping("system/city")
@Tag(name="城市管理")
public class SysCityController {
    @Resource
    private SysCityService sysCityService;

    @GetMapping("/list")
    @Operation(summary = "查询城市管理")
    @PreAuthorize("hasAuthority('system:city:list')")
    public Result<List<SysCityVO>> page(@ParameterObject SysCityQuery query){
        List<SysCityVO> list = sysCityService.selectSysCityList(query);

        return Result.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取城市管理详细信息")
    @PreAuthorize("hasAuthority('system:city:info')")
    public Result<SysCityVO> getInfo(@PathVariable("id") Long id){
        SysCityVO vo = sysCityService.selectSysCityById(id);

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "新增城市管理")
    @PreAuthorize("hasAuthority('system:city:save')")
    public Result<SysCityVO> save(@RequestBody SysCityVO vo){
        SysCityVO resVo = sysCityService.insertSysCity(vo);

        return Result.ok(resVo);
    }

    @PutMapping
    @Operation(summary = "修改城市管理")
    @PreAuthorize("hasAuthority('system:city:update')")
    public Result<SysCityVO> update(@RequestBody @Valid SysCityVO vo){
        SysCityVO resVo = sysCityService.updateSysCity(vo);

        return Result.ok(resVo);
    }

    @GetMapping("/tree")
    @Operation(summary = "获得城市树")
    public Result<List<SysCityVO>> tree(){
        List<SysCityVO> list = sysCityService.selectSysCityTree();
        return Result.ok(list);
    }
}