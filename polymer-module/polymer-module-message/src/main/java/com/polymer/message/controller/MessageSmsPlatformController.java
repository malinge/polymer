package com.polymer.message.controller;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.common.utils.ExceptionUtils;
import com.polymer.framework.logger.annotations.OperateLog;
import com.polymer.framework.logger.enums.OperateTypeEnum;
import com.polymer.message.query.MessageSmsPlatformQuery;
import com.polymer.message.service.MessageSmsPlatformService;
import com.polymer.message.sms.SmsContext;
import com.polymer.message.sms.config.SmsConfig;
import com.polymer.message.sms.service.SmsService;
import com.polymer.message.vo.MessageSmsPlatformVO;
import com.polymer.message.vo.MessageSmsSendVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信平台
 *
 * @author polymer
 */
@RestController
@RequestMapping("message/smsPlatform")
@Tag(name = "短信平台")
public class MessageSmsPlatformController {
    @Resource
    private MessageSmsPlatformService messageSmsPlatformService;
    @Resource
    private SmsService smsService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('message:smsPlatform:page')")
    public Result<PageResult<MessageSmsPlatformVO>> page(@ParameterObject @Valid MessageSmsPlatformQuery query) {
        PageResult<MessageSmsPlatformVO> page = messageSmsPlatformService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('message:smsPlatform:info')")
    public Result<MessageSmsPlatformVO> get(@PathVariable("id") Long id) {
        MessageSmsPlatformVO vo = messageSmsPlatformService.getById(id);

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('message:smsPlatform:save')")
    public Result<MessageSmsPlatformVO> save(@RequestBody MessageSmsPlatformVO vo) {
        MessageSmsPlatformVO resVo = messageSmsPlatformService.save(vo);

        return Result.ok(resVo);
    }

    @PostMapping("send")
    @Operation(summary = "发送短信")
    @OperateLog(type = OperateTypeEnum.OTHER)
    @PreAuthorize("hasAuthority('message:smsPlatform:update')")
    public Result<String> send(@RequestBody MessageSmsSendVO vo) {
        MessageSmsPlatformVO smsPlatformVO = messageSmsPlatformService.getById(vo.getId());
        SmsConfig config = ConvertUtils.convertTo(smsPlatformVO, SmsConfig::new);

        // 短信参数
        Map<String, String> params = new LinkedHashMap<>();
        if (StringUtils.isNotBlank(vo.getParamValue())) {
            params.put(vo.getParamKey(), vo.getParamValue());
        }

        try {
            // 发送短信
            new SmsContext(config).send(vo.getMobile(), params);

            // 保存日志
            smsService.saveLog(config, vo.getMobile(), params, null);

            return Result.ok();
        } catch (Exception e) {
            // 保存日志
            smsService.saveLog(config, vo.getMobile(), params, e);

            return Result.error(ExceptionUtils.getExceptionMessage(e));
        }
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('message:smsPlatform:update')")
    public Result<MessageSmsPlatformVO> update(@RequestBody @Valid MessageSmsPlatformVO vo) {
        MessageSmsPlatformVO resVo = messageSmsPlatformService.update(vo);

        return Result.ok(resVo);
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('message:smsPlatform:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        messageSmsPlatformService.delete(idList);

        return Result.ok();
    }
}