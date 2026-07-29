package com.polymer.message.controller;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.security.core.user.SecurityUser;
import com.polymer.framework.security.core.user.UserDetail;
import com.polymer.message.mail.service.MailSendService;
import com.polymer.message.query.MessageMailTemplateQuery;
import com.polymer.message.service.MessageMailTemplateService;
import com.polymer.message.vo.MessageMailTemplateSendReqVO;
import com.polymer.message.vo.MessageMailTemplateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
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
 * 邮件模版表
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
@RestController
@RequestMapping("message/mailTemplate")
@Tag(name = "邮件模版表")
public class MessageMailTemplateController {
    @Resource
    private MessageMailTemplateService messageMailTemplateService;
    @Resource
    private MailSendService mailSendService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('message:mailTemplate:page')")
    public Result<PageResult<MessageMailTemplateVO>> page(@ParameterObject @Valid MessageMailTemplateQuery query) {
        PageResult<MessageMailTemplateVO> page = messageMailTemplateService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('message:mailTemplate:info')")
    public Result<MessageMailTemplateVO> get(@PathVariable("id") Long id) {
        MessageMailTemplateVO vo = messageMailTemplateService.getById(id);

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('message:mailTemplate:save')")
    public Result<MessageMailTemplateVO> save(@RequestBody MessageMailTemplateVO vo) {
        MessageMailTemplateVO resVo = messageMailTemplateService.save(vo);

        return Result.ok(resVo);
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('message:mailTemplate:update')")
    public Result<MessageMailTemplateVO> update(@RequestBody @Valid MessageMailTemplateVO vo) {
        MessageMailTemplateVO resVo = messageMailTemplateService.update(vo);

        return Result.ok(resVo);
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('message:mailTemplate:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        messageMailTemplateService.delete(idList);

        return Result.ok();
    }

    @PostMapping("/send-mail")
    @Operation(summary = "发送短信")
    @PreAuthorize("hasPermission('message:mailTemplate:send-mail')")
    public Result<Long> sendMail(@Valid @RequestBody MessageMailTemplateSendReqVO sendReqVO) {
        UserDetail user = SecurityUser.getUser();
        Assert.notNull(user, "用户信息为空");
        return Result.ok(mailSendService.sendSingleMailToUser(sendReqVO.getMail(), user.getId(),
                sendReqVO.getTemplateCode(), sendReqVO.getTemplateParams()));
    }

}