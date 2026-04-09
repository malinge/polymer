package com.polymer.message.controller;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.message.notify.service.NotifySendService;
import com.polymer.message.query.MessageNotifyTemplateQuery;
import com.polymer.message.service.MessageNotifyTemplateService;
import com.polymer.message.vo.MessageNotifyTemplateVO;
import com.polymer.message.vo.NotifyTemplateSendReqVO;
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
 * 站内信模板
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-09
 */
@RestController
@RequestMapping("message/notifyTemplate")
@Tag(name = "站内信模板")
public class MessageNotifyTemplateController {
    @Resource
    private MessageNotifyTemplateService messageNotifyTemplateService;
    @Resource
    private NotifySendService notifySendService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('message:notifyTemplate:page')")
    public Result<PageResult<MessageNotifyTemplateVO>> page(@ParameterObject @Valid MessageNotifyTemplateQuery query) {
        PageResult<MessageNotifyTemplateVO> page = messageNotifyTemplateService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('message:notifyTemplate:info')")
    public Result<MessageNotifyTemplateVO> get(@PathVariable("id") Long id) {
        MessageNotifyTemplateVO vo = messageNotifyTemplateService.getById(id);

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('message:notifyTemplate:save')")
    public Result<MessageNotifyTemplateVO> save(@RequestBody MessageNotifyTemplateVO vo) {
        MessageNotifyTemplateVO resVo = messageNotifyTemplateService.save(vo);

        return Result.ok(resVo);
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('message:notifyTemplate:update')")
    public Result<MessageNotifyTemplateVO> update(@RequestBody @Valid MessageNotifyTemplateVO vo) {
        MessageNotifyTemplateVO resVo = messageNotifyTemplateService.update(vo);

        return Result.ok(resVo);
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('message:notifyTemplate:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        messageNotifyTemplateService.delete(idList);

        return Result.ok();
    }

    @PostMapping("/send-notify")
    @Operation(summary = "发送站内信")
    @PreAuthorize("hasAuthority('message:notifyTemplate:sendNotify')")
    public Result<String> sendNotify(@Valid @RequestBody NotifyTemplateSendReqVO sendReqVO) {
        notifySendService.sendBatchNotify(sendReqVO.getUserIds(), sendReqVO.getTemplateCode(), sendReqVO.getTemplateParams());
        return Result.ok();
    }
}