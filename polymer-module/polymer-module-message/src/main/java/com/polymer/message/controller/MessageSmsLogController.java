package com.polymer.message.controller;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.message.query.MessageSmsLogQuery;
import com.polymer.message.service.MessageSmsLogService;
import com.polymer.message.vo.MessageSmsLogVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 短信日志
 *
 * @author polymer
 */
@RestController
@RequestMapping("message/smsLog")
@Tag(name = "短信日志")
public class MessageSmsLogController {
    @Resource
    private MessageSmsLogService messageSmsLogService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('message:smsLog:page')")
    public Result<PageResult<MessageSmsLogVO>> page(@ParameterObject @Valid MessageSmsLogQuery query) {
        PageResult<MessageSmsLogVO> page = messageSmsLogService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('message:smsLog:info')")
    public Result<MessageSmsLogVO> get(@PathVariable("id") Long id) {
        MessageSmsLogVO vo = messageSmsLogService.getById(id);

        return Result.ok(vo);
    }

}