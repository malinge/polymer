package com.polymer.system.controller;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.system.query.SysLogOperateQuery;
import com.polymer.system.service.SysLogOperateService;
import com.polymer.system.vo.SysLogOperateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 操作日志
 *
 * @author polymer
 */
@RestController
@RequestMapping("sys/log/operate")
@Tag(name = "操作日志")
public class SysLogOperateController {
    @Resource
    private SysLogOperateService sysLogOperateService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:operate:all')")
    public Result<PageResult<SysLogOperateVO>> page(@ParameterObject @Valid SysLogOperateQuery query) {
        PageResult<SysLogOperateVO> page = sysLogOperateService.page(query);

        return Result.ok(page);
    }
}