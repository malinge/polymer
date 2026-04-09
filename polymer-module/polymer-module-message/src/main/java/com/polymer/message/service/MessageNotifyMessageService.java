package com.polymer.message.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.message.query.MessageNotifyMessageQuery;
import com.polymer.message.vo.MessageNotifyMessageVO;
import com.polymer.message.vo.MessageNotifyTemplateVO;

import java.util.List;
import java.util.Map;

/**
 * 站内信消息
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-09
 */
public interface MessageNotifyMessageService {

    /**
     * 根据站内信消息查询获取分页站内信消息
     *
     * @param query 站内信消息查询
     * @return 结果
     */
    PageResult<MessageNotifyMessageVO> page(MessageNotifyMessageQuery query);

    /**
     * 保存站内信消息
     *
     * @param vo 站内信消息
     * @return 保存数量
     */
    int save(MessageNotifyMessageVO vo);

    /**
     * 更新站内信消息
     *
     * @param vo 站内信消息
     * @return 更新数量
     */
    int update(MessageNotifyMessageVO vo);

    /**
     * 根据站内信信息id集合删除站内信消息
     *
     * @param idList 站内信消息id集合
     * @return 删除数量
     */
    int delete(List<Long> idList);

    /**
     * 根据用户id和查询条数查询站内信消息集合
     *
     * @param userId 用户id
     * @param size   查询条数
     * @return 站内信消息集合
     */
    List<MessageNotifyMessageVO> getUnreadNotifyMessageList(Long userId, Integer size);

    /**
     * 根据用户id获取站内信消息未读数量
     *
     * @param userId 用户id
     * @return 未读数量
     */
    Long getUnreadNotifyMessageCount(Long userId);

    /**
     * 根据id集合和用户id更新站内信消息
     *
     * @param ids    id集合
     * @param userId 用户id
     * @return 结果
     */
    int updateNotifyMessageRead(List<Long> ids, Long userId);

    /**
     * 根据用户id更新站内信消息
     *
     * @param userId 用户id
     * @return 结果
     */
    int updateAllNotifyMessageRead(Long userId);

    /**
     * 根据用户id集合和站内信模板创建站内信
     *
     * @param userIds    站内信消息表
     * @param templateVO 站内信消息表
     * @return 保存个数
     */
    int createNotifyMessage(List<Long> userIds, MessageNotifyTemplateVO templateVO, String content, Map<String, Object> templateParams);

    /**
     * 根据主键查询站内信消息表
     *
     * @param id 站内信消息表主键
     * @return 站内信消息
     */
    MessageNotifyMessageVO getById(Long id);
}