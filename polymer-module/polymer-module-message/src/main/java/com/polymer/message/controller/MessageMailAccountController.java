package com.polymer.message.controller;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.message.query.MessageMailAccountQuery;
import com.polymer.message.service.MessageMailAccountService;
import com.polymer.message.vo.MessageMailAccountVO;
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
 * 邮箱账号表
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
@RestController
@RequestMapping("message/mailAccount")
@Tag(name = "邮箱账号表")
public class MessageMailAccountController {
    @Resource
    private MessageMailAccountService messageMailAccountService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('message:mailAccount:page')")
    public Result<PageResult<MessageMailAccountVO>> page(@ParameterObject @Valid MessageMailAccountQuery query) {
        PageResult<MessageMailAccountVO> page = messageMailAccountService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('message:mailAccount:info')")
    public Result<MessageMailAccountVO> get(@PathVariable("id") Long id) {
        MessageMailAccountVO vo = messageMailAccountService.getById(id);

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('message:mailAccount:save')")
    public Result<MessageMailAccountVO> save(@RequestBody MessageMailAccountVO vo) {
        MessageMailAccountVO resVo = messageMailAccountService.save(vo);

        return Result.ok(resVo);
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('message:mailAccount:update')")
    public Result<MessageMailAccountVO> update(@RequestBody @Valid MessageMailAccountVO vo) {
        MessageMailAccountVO resVo = messageMailAccountService.update(vo);

        return Result.ok(resVo);
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('message:mailAccount:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        messageMailAccountService.delete(idList);

        return Result.ok();
    }

    @GetMapping("list")
    @Operation(summary = "账号list")
    @PreAuthorize("hasAuthority('message:mailAccount:list')")
    public Result<List<MessageMailAccountVO>> list() {
        List<MessageMailAccountVO> list = messageMailAccountService.list();

        return Result.ok(list);
    }
}