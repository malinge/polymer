package com.polymer.demo.controller;

import com.polymer.framework.common.annotation.SignatureCheck;
import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.common.utils.JsonUtils;
import com.polymer.framework.web.websocket.core.MyWebSocketHandler;
import com.polymer.framework.web.websocket.core.NotifyMessageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 新模块测试
 *
 * @author polymer
 */
@RestController
@RequestMapping("demo/test")
@Tag(name = "新模块测试")
public class TestController {

    @SignatureCheck
    @GetMapping("/test/{type}")
    @Operation(summary = "测试接口")
    public Result<String> test(@PathVariable String type) {
        try {
            NotifyMessageDTO dto = new NotifyMessageDTO();
            dto.setType(type);
            dto.setData("1");
            MyWebSocketHandler.sendMessageToUser("10000", JsonUtils.toJsonString(dto));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.ok("测试数据");
    }
}

