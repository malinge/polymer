package com.polymer.message.controller;

import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.common.utils.JsonUtils;
import com.polymer.framework.common.utils.SseEmitterUtil;
import com.polymer.message.vo.MessageSseSendVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


/**
 * BelongsProject: polymer
 * BelongsPackage: com.polymer.message.controller
 * CreateTime: 2024-08-02  14:50
 * Description: SSE消息推送
 *
 * @author polymer
 * @version 2.0
 */
@RestController
@RequestMapping("message/sse")
public class MessageSseController {
    /**
     * 用于创建连接
     */
    @GetMapping("/connect/{userId}")
    public SseEmitter connect(@PathVariable("userId") Long userId) {
        return SseEmitterUtil.connect(userId);
    }

    /**
     * 关闭连接
     */
    @GetMapping("/close/{userId}")
    public Result<String> close(@PathVariable("userId") Long userId) {
        SseEmitterUtil.removeUser(userId);
        return Result.ok("关闭连接成功");
    }

    @GetMapping("/sse")
    public Result<String> sse() {
        // 构建推送消息体
        MessageSseSendVO sseMessage = new MessageSseSendVO();
        sseMessage.setId(1L);
        sseMessage.setMsg("SSE测试发送消息");
        sseMessage.setAgentName("测试智能体推送消息");
        SseEmitterUtil.sendMessage(1L, JsonUtils.toJsonString(sseMessage));
        return Result.ok("发送消息成功");
    }
}
