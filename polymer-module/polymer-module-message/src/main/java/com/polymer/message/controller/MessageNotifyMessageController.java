package com.polymer.message.controller;

import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.security.core.user.SecurityUser;
import com.polymer.framework.security.core.user.UserDetail;
import com.polymer.message.query.MessageNotifyMessageQuery;
import com.polymer.message.service.MessageNotifyMessageService;
import com.polymer.message.vo.MessageNotifyMessageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 站内信消息
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-09
 */
@RestController
@RequestMapping("message/notifyMessage")
@Tag(name = "站内信消息")
public class MessageNotifyMessageController {
    @Resource
    private MessageNotifyMessageService messageNotifyMessageService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('message:notifyMessage:page')")
    public Result<PageResult<MessageNotifyMessageVO>> page(@ParameterObject @Valid MessageNotifyMessageQuery query) {
        PageResult<MessageNotifyMessageVO> page = messageNotifyMessageService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('message:notifyMessage:info')")
    public Result<MessageNotifyMessageVO> get(@PathVariable("id") Long id) {
        MessageNotifyMessageVO vo = messageNotifyMessageService.getById(id);

        return Result.ok(vo);
    }

    @PutMapping("/update-read")
    @Operation(summary = "标记站内信为已读")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    public Result<Boolean> updateNotifyMessageRead(@RequestParam("ids") List<Long> ids) {
        UserDetail user = SecurityUser.getUser();
        if (user == null) {
            throw new ServiceException("用户信息为空！");
        }
        messageNotifyMessageService.updateNotifyMessageRead(ids, user.getId());
        return Result.ok(Boolean.TRUE);
    }

    @PutMapping("/update-all-read")
    @Operation(summary = "标记所有站内信为已读")
    public Result<Boolean> updateAllNotifyMessageRead() {
        UserDetail user = SecurityUser.getUser();
        if (user == null) {
            throw new ServiceException("用户信息为空！");
        }
        messageNotifyMessageService.updateAllNotifyMessageRead(user.getId());
        return Result.ok(Boolean.TRUE);
    }

    @GetMapping("/get-unread-list")
    @Operation(summary = "获取当前用户的最新站内信列表，默认 10 条")
    @Parameter(name = "size", description = "10")
    public Result<List<MessageNotifyMessageVO>> getUnreadNotifyMessageList(
            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        UserDetail user = SecurityUser.getUser();
        if (user == null) {
            throw new ServiceException("用户信息为空！");
        }
        List<MessageNotifyMessageVO> voList = messageNotifyMessageService.getUnreadNotifyMessageList(
                user.getId(), size);
        return Result.ok(voList);
    }

    @GetMapping("/get-unread-count")
    @Operation(summary = "获得当前用户的未读站内信数量")
    public Result<Long> getUnreadNotifyMessageCount() {
        UserDetail user = SecurityUser.getUser();
        if (user == null) {
            throw new ServiceException("用户信息为空！");
        }
        Long num = messageNotifyMessageService.getUnreadNotifyMessageCount(user.getId());
        return Result.ok(num);
    }
}