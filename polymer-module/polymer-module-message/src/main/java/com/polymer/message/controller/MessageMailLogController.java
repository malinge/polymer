package com.polymer.message.controller;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.message.query.MessageMailLogQuery;
import com.polymer.message.service.MessageMailLogService;
import com.polymer.message.vo.MessageMailLogVO;
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
 * 邮件日志表
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
@RestController
@RequestMapping("message/mailLog")
@Tag(name = "邮件日志表")
public class MessageMailLogController {
    @Resource
    private MessageMailLogService messageMailLogService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('message:mailLog:page')")
    public Result<PageResult<MessageMailLogVO>> page(@ParameterObject @Valid MessageMailLogQuery query) {
        PageResult<MessageMailLogVO> page = messageMailLogService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('message:mailLog:info')")
    public Result<MessageMailLogVO> get(@PathVariable("id") Long id) {
        MessageMailLogVO vo = messageMailLogService.getById(id);

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('message:mailLog:save')")
    public Result<MessageMailLogVO> save(@RequestBody MessageMailLogVO vo) {
        MessageMailLogVO resVo = messageMailLogService.save(vo);

        return Result.ok(resVo);
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('message:mailLog:update')")
    public Result<MessageMailLogVO> update(@RequestBody @Valid MessageMailLogVO vo) {
        MessageMailLogVO resVo = messageMailLogService.update(vo);

        return Result.ok(resVo);
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('message:mailLog:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        messageMailLogService.delete(idList);

        return Result.ok();
    }
}