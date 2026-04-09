package com.polymer.message.mapper;

import com.polymer.framework.mybatis.core.annotation.DataScope;
import com.polymer.message.entity.MessageMailLogEntity;
import com.polymer.message.query.MessageMailLogQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 邮件日志表
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
@Mapper
public interface MessageMailLogMapper {

    /**
     * 根据邮件日志查询获取邮件日志集合
     *
     * @param query 邮件日志表查询
     * @return 结果
     */
    @DataScope
    List<MessageMailLogEntity> selectMessageMailLogList(MessageMailLogQuery query);

    /**
     * 查询邮件日志表
     *
     * @param id 邮件日志表主键
     * @return 邮件日志表
     */
    MessageMailLogEntity selectMessageMailLogById(Long id);

    /**
     * 新增邮件日志表
     *
     * @param messageMailLog 邮件日志表
     * @return 结果
     */
    int insertMessageMailLog(MessageMailLogEntity messageMailLog);

    /**
     * 修改邮件日志表
     *
     * @param messageMailLog 邮件日志表
     * @return 结果
     */
    int updateMessageMailLog(MessageMailLogEntity messageMailLog);

    /**
     * 删除邮件日志表
     *
     * @param id 邮件日志表主键
     * @return 结果
     */
    int deleteMessageMailLogById(Long id);

}