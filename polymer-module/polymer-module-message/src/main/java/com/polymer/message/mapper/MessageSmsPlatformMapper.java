package com.polymer.message.mapper;

import com.polymer.message.entity.MessageSmsPlatformEntity;
import com.polymer.message.query.MessageSmsPlatformQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 短信平台
 *
 * @author polymer
 */
@Mapper
public interface MessageSmsPlatformMapper {

    /**
     * 查询短信平台列表
     *
     * @param query 短信平台查询
     * @return 短信平台集合
     */
    List<MessageSmsPlatformEntity> selectMessageSmsPlatformList(MessageSmsPlatformQuery query);

    /**
     * 查询短信平台
     *
     * @param id 短信平台主键
     * @return 短信平台
     */
    MessageSmsPlatformEntity selectMessageSmsPlatformById(Long id);

    /**
     * 新增短信平台
     *
     * @param messageSmsPlatform 短信平台
     * @return 结果
     */
    int insertMessageSmsPlatform(MessageSmsPlatformEntity messageSmsPlatform);

    /**
     * 修改短信平台
     *
     * @param messageSmsPlatform 短信平台
     * @return 结果
     */
    int updateMessageSmsPlatform(MessageSmsPlatformEntity messageSmsPlatform);

    /**
     * 删除短信平台
     *
     * @param id 短信平台主键
     * @return 结果
     */
    int deleteMessageSmsPlatformById(Long id);

    /**
     * 根据状态查询短信平台集合
     *
     * @param status 状态
     * @return 结果
     */
    List<MessageSmsPlatformEntity> selectListByStatus(Integer status);

}