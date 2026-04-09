package com.polymer.message.mapper;

import com.polymer.message.entity.MessageNotifyMessageEntity;
import com.polymer.message.query.MessageNotifyMessageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 站内信消息
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-09
 */
@Mapper
public interface MessageNotifyMessageMapper {

    /**
     * 根据站内信消息查询获取站内信消息集合
     *
     * @param query 站内信消息查询
     * @return 结果
     */
    List<MessageNotifyMessageEntity> selectMessageNotifyMessageList(MessageNotifyMessageQuery query);

    /**
     * 根据用户id和查询条数获取站内信消息集合
     *
     * @param userId 用户id
     * @param size   查询条数
     * @return 结果
     */
    List<MessageNotifyMessageEntity> getUnreadNotifyMessageList(@Param("userId") Long userId, @Param("size") Integer size);

    /**
     * 根据用户id和是否已读获取站内信消息个数
     *
     * @param userId     用户id
     * @param readStatus 是否已读
     * @return 结果
     */
    Long getUnreadNotifyMessageCount(@Param("userId") Long userId, @Param("readStatus") Boolean readStatus);

    /**
     * 根据id集合和用户id更新站内信消息
     *
     * @param ids    id集合
     * @param userId 用户id
     * @return 结果
     */
    int updateNotifyMessageRead(@Param("ids") List<Long> ids, @Param("userId") Long userId);

    /**
     * 根据用户id更新站内信消息
     *
     * @param userId 用户id
     * @return 结果
     */
    int updateAllNotifyMessageRead(Long userId);

    /**
     * 查询站内信消息表
     *
     * @param id 站内信消息表主键
     * @return 站内信消息表
     */
    MessageNotifyMessageEntity selectMessageNotifyMessageById(Long id);

    /**
     * 新增站内信消息表
     *
     * @param messageNotifyMessage 站内信消息表
     * @return 结果
     */
    int insertMessageNotifyMessage(MessageNotifyMessageEntity messageNotifyMessage);

    /**
     * 修改站内信消息表
     *
     * @param messageNotifyMessage 站内信消息表
     * @return 结果
     */
    int updateMessageNotifyMessage(MessageNotifyMessageEntity messageNotifyMessage);

    /**
     * 删除站内信消息表
     *
     * @param id 站内信消息表主键
     * @return 结果
     */
    int deleteMessageNotifyMessageById(Long id);
}