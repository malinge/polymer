package com.polymer.system.controller;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.logger.annotations.OperateLog;
import com.polymer.framework.logger.enums.OperateTypeEnum;
import com.polymer.system.query.SysAttachmentQuery;
import com.polymer.system.service.SysAttachmentService;
import com.polymer.system.vo.SysAttachmentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 附件管理
 *
 * @author polymer
 */
@RestController
@RequestMapping("sys/attachment")
@Tag(name = "附件管理")
public class SysAttachmentController {
    @Resource
    private SysAttachmentService sysAttachmentService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:attachment:page')")
    public Result<PageResult<SysAttachmentVO>> page(@ParameterObject @Valid SysAttachmentQuery query) {
        PageResult<SysAttachmentVO> page = sysAttachmentService.selectSysAttachmentList(query);

        return Result.ok(page);
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:attachment:save')")
    public Result<SysAttachmentVO> save(@RequestBody SysAttachmentVO vo) {
        SysAttachmentVO resVo = sysAttachmentService.insertSysAttachment(vo);

        return Result.ok(resVo);
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:attachment:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        sysAttachmentService.deleteSysAttachmentByIds(idList);

        return Result.ok();
    }
}