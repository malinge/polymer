package com.polymer.application;

import com.polymer.api.message.MailSendApi;
import com.polymer.api.sequence.SequenceApi;
import com.polymer.api.sequence.dto.DbSeqDTO;
import com.polymer.framework.web.websocket.core.MyWebSocketHandler;
import com.polymer.system.entity.SysUserTokenEntity;
import com.polymer.system.mapper.SysUserMapper;
import com.polymer.system.service.SysUserService;
import com.polymer.system.service.SysUserTokenService;
import com.polymer.system.vo.SysUserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * BelongsProject: polymer
 * BelongsPackage: com.polymer
 * CreateTime: 2024-08-30  19:46
 * Description: TODO
 *
 * @author polymer
 * @version 2.0
 */
@SpringBootTest
public class TestTest {
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private MailSendApi mailSendApi;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SequenceApi sequenceApi;

    @Autowired
    private SysUserService sysUserService;

    @Test
    public void getUserById() {
        SysUserVO byId = sysUserService.getById(10001L);
        System.out.println("#########:"+byId.getCity());

    }

    @Test
    public void sequence() {
        String s = sequenceApi.nextNo(new DbSeqDTO());
        System.out.println("#########:"+s);

    }

    @Test
    public void uploadAvatar() {
        sysUserMapper.updateUserAvatar(10001L, "http://localhost:8081/polymer/upload/20250711/1- 1-2_1752225815.jpg");

    }

    @Test
    public void uploadTest() {
        SysUserTokenEntity sysUserToken = sysUserTokenService.selectUserTokenOne(10002L);
        System.out.println(sysUserToken.getUserId());

    }

    @Test
    public void sendMailTest() {
        Map<String, Object> templateParams = new HashMap<>();
        templateParams.put("key01", "test");
        templateParams.put("key02", "test11");
        mailSendApi.sendSingleMailToUser("1600965800@qq.com", 10003L, "test_02",templateParams);
    }

    @Test
    public void sendNotifyTest() throws IOException {
        MyWebSocketHandler.sendMessageToUser("10000", "1");
    }
}
