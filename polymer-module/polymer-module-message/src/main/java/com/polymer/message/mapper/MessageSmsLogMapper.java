package com.polymer.message.mapper;

import com.polymer.message.entity.MessageSmsLogEntity;
import com.polymer.message.query.MessageSmsLogQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 短信日志
 *
 * @author polymer
 */
@Mapper
public interface MessageSmsLogMapper {

    /**
     * 查询短信日志列表
     *
     * @param query 短信日志查询
     * @return 短信日志集合
     */
    List<MessageSmsLogEntity> selectMessageSmsLogList(MessageSmsLogQuery query);

    /**
     * 查询短信日志
     *
     * @param id 短信日志主键
     * @return 短信日志
     */
    MessageSmsLogEntity selectMessageSmsLogById(Long id);

    /**
     * 新增短信日志
     *
     * @param messageSmsLog 短信日志
     * @return 结果
     */
    int insertMessageSmsLog(MessageSmsLogEntity messageSmsLog);

    /**
     * 修改短信日志
     *
     * @param messageSmsLog 短信日志
     * @return 结果
     */
    int updateMessageSmsLog(MessageSmsLogEntity messageSmsLog);

    /**
     * 删除短信日志
     *
     * @param id 短信日志主键
     * @return 结果
     */
    int deleteMessageSmsLogById(Long id);
}