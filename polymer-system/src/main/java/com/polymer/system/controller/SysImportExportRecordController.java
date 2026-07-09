package com.polymer.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.system.service.SysImportExportRecordService;
import com.polymer.system.query.SysImportExportRecordQuery;
import com.polymer.system.vo.SysImportExportRecordVO;
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
* 导入导出记录表Controller
*
* @author polymer polymer@126.com
* @since 1.0.0 2026-06-29
*/
@RestController
@RequestMapping("system/importExportRecord")
@Tag(name="导入导出记录表")
public class SysImportExportRecordController {
    @Resource
    private SysImportExportRecordService sysImportExportRecordService;

    @GetMapping("/page")
    @Operation(summary = "查询导入导出记录表分页")
    @PreAuthorize("hasAuthority('system:importExportRecord:page')")
    public Result<PageResult<SysImportExportRecordVO>> page(@ParameterObject @Valid SysImportExportRecordQuery query){
        PageResult<SysImportExportRecordVO> page = sysImportExportRecordService.page(query);

        return Result.ok(page);
    }

    @GetMapping("/list/{businessType}")
    @Operation(summary = "查询导入记录表分页")
    public Result<List<SysImportExportRecordVO>> list(@PathVariable("businessType") String businessType){
        List<SysImportExportRecordVO> list = sysImportExportRecordService.list(businessType);
        return Result.ok(list);
    }
}