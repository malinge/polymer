package com.polymer.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.enums.UserTypeEnum;
import com.polymer.framework.common.enums.WebSocketMessageTypeEnum;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.common.utils.JsonUtils;
import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.framework.security.core.user.SecurityUser;
import com.polymer.framework.security.core.user.UserDetail;
import com.polymer.framework.web.websocket.core.MyWebSocketHandler;
import com.polymer.framework.web.websocket.core.NotifyMessageDTO;
import com.polymer.message.entity.MessageNotifyMessageEntity;
import com.polymer.message.mapper.MessageNotifyMessageMapper;
import com.polymer.message.query.MessageNotifyMessageQuery;
import com.polymer.message.service.MessageNotifyMessageService;
import com.polymer.message.vo.MessageNotifyMessageVO;
import com.polymer.message.vo.MessageNotifyTemplateVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 站内信消息
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-09
 */
@Service
public class MessageNotifyMessageServiceImpl implements MessageNotifyMessageService {
    private static final Logger log = LoggerFactory.getLogger(MessageNotifyMessageServiceImpl.class);
    @Resource
    private MessageNotifyMessageMapper messageNotifyMessageMapper;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 根据站内信消息查询获取分页站内信消息
     *
     * @param query 站内信消息查询
     * @return 结果
     */
    @Override
    public PageResult<MessageNotifyMessageVO> page(MessageNotifyMessageQuery query) {
        UserDetail user = SecurityUser.getUser();
        if (user == null) {
            throw new ServiceException("用户信息为空！");
        }
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        query.setUserId(user.getId());
        List<MessageNotifyMessageEntity> entityList = messageNotifyMessageMapper.selectMessageNotifyMessageList(query);
        PageInfo<MessageNotifyMessageEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, MessageNotifyMessageVO::new), pageInfo.getTotal());
    }

    /**
     * 保存站内信消息
     *
     * @param vo 站内信消息
     * @return 保存数量
     */
    @Override
    public int save(MessageNotifyMessageVO vo) {

        return messageNotifyMessageMapper.insertMessageNotifyMessage(ConvertUtils.convertTo(vo, MessageNotifyMessageEntity::new));
    }

    /**
     * 更新站内信消息
     *
     * @param vo 站内信消息
     * @return 更新数量
     */
    @Override
    public int update(MessageNotifyMessageVO vo) {

        return messageNotifyMessageMapper.updateMessageNotifyMessage(ConvertUtils.convertTo(vo, MessageNotifyMessageEntity::new));
    }

    /**
     * 根据站内信信息id集合删除站内信消息
     *
     * @param idList 站内信消息id集合
     * @return 删除数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<Long> idList) {
        return batchUtils.executeBatch(MessageNotifyMessageMapper.class, idList, MessageNotifyMessageMapper::deleteMessageNotifyMessageById);
    }

    /**
     * 根据用户id和查询条数查询站内信消息集合
     *
     * @param userId 用户id
     * @param size   查询条数
     * @return 站内信消息集合
     */
    @Override
    public List<MessageNotifyMessageVO> getUnreadNotifyMessageList(Long userId, Integer size) {
        List<MessageNotifyMessageEntity> entityList = messageNotifyMessageMapper.getUnreadNotifyMessageList(userId, size);
        return ConvertUtils.convertListTo(entityList, MessageNotifyMessageVO::new);
    }

    /**
     * 根据用户id获取站内信消息未读数量
     *
     * @param userId 用户id
     * @return 未读数量
     */
    @Override
    public Long getUnreadNotifyMessageCount(Long userId) {
        return messageNotifyMessageMapper.getUnreadNotifyMessageCount(userId, false);
    }

    /**
     * 根据id集合和用户id更新站内信消息
     *
     * @param ids    id集合
     * @param userId 用户id
     * @return 结果
     */
    @Override
    public int updateNotifyMessageRead(List<Long> ids, Long userId) {
        return messageNotifyMessageMapper.updateNotifyMessageRead(ids, userId);
    }

    /**
     * 根据用户id更新站内信消息
     *
     * @param userId 用户id
     * @return 结果
     */
    @Override
    public int updateAllNotifyMessageRead(Long userId) {
        return messageNotifyMessageMapper.updateAllNotifyMessageRead(userId);
    }

    /**
     * 根据用户id集合和站内信模板创建站内信
     *
     * @param userIds    站内信消息表
     * @param templateVO 站内信消息表
     * @return 保存个数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createNotifyMessage(List<Long> userIds, MessageNotifyTemplateVO templateVO, String templateContent, Map<String, Object> templateParams) {
        List<MessageNotifyMessageEntity> entityList = new ArrayList<>();
        UserDetail user = SecurityUser.getUser();
        String username = "";
        String avatar = "";
        if(user != null) {
            username = user.getUsername();
            avatar = user.getAvatar();
        }
        for (Long userId : userIds) {
            MessageNotifyMessageEntity entity = new MessageNotifyMessageEntity();
            entity.setUserId(userId);
            entity.setUserType(UserTypeEnum.ADMIN.getValue());
            entity.setTemplateCode(templateVO.getCode());
            entity.setTitle(templateVO.getName());
            entity.setType(templateVO.getType());
            entity.setSender(username);
            entity.setContent(templateContent);
            entity.setSenderAvatar(avatar);
            entity.setReadStatus(false);
            entityList.add(entity);
        }
        if (entityList.size() > 0) {
            int i = batchUtils.executeBatch(MessageNotifyMessageMapper.class, entityList, MessageNotifyMessageMapper::insertMessageNotifyMessage);
            if (i > 0) {
                pushWebSocket(userIds);
            }
            return i;
        }
        return 0;
    }

    /**
     * 推送WebSocket
     * @param userIds 用户id集合
     */
    private void pushWebSocket(List<Long> userIds){
        for (Long userId : userIds) {
            try {
                NotifyMessageDTO dto = new NotifyMessageDTO();
                dto.setType(WebSocketMessageTypeEnum.NOTIFY_MESSAGE.name());
                dto.setData("1");
                MyWebSocketHandler.sendMessageToUser(String.valueOf(userId), JsonUtils.toJsonString(dto));
            } catch (Exception e) {
                // 自己处理异常，throw的话整个流程就会回滚
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 根据主键查询站内信消息表
     *
     * @param id 站内信消息表主键
     * @return 站内信消息
     */
    @Override
    public MessageNotifyMessageVO getById(Long id) {
        MessageNotifyMessageEntity entity = messageNotifyMessageMapper.selectMessageNotifyMessageById(id);
        return ConvertUtils.convertTo(entity, MessageNotifyMessageVO::new);
    }

}