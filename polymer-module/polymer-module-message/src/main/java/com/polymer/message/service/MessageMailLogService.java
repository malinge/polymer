package com.polymer.message.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.message.query.MessageMailLogQuery;
import com.polymer.message.vo.MessageMailAccountVO;
import com.polymer.message.vo.MessageMailLogVO;
import com.polymer.message.vo.MessageMailTemplateVO;

import java.util.List;
import java.util.Map;

/**
 * 邮件日志表
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
public interface MessageMailLogService {

    /**
     * 根据邮件日志查询获取分页邮件日志列表
     *
     * @param query 邮件日志查询
     * @return PageResult<MessageMailLogVO>
     */
    PageResult<MessageMailLogVO> page(MessageMailLogQuery query);

    /**
     * 保存邮件日志信息
     *
     * @param vo 邮件日志信息
     * @return 保存数量
     */
    MessageMailLogVO save(MessageMailLogVO vo);

    /**
     * 更新邮件日志信息
     *
     * @param vo 邮件日志信息
     * @return 更新数量
     */
    MessageMailLogVO update(MessageMailLogVO vo);

    /**
     * 根据邮件日志主键集合删除邮件日志
     *
     * @param idList 邮件日志主键集合
     * @return 删除数量
     */
    int delete(List<Long> idList);

    /**
     * 保存邮件日志
     *
     * @param userId 用户id
     * @param userType 用户类型
     * @param mail 邮箱
     * @param account 账户
     * @param template 模板
     * @param content 内容
     * @param templateParams 模板参数
     * @param isSend 是否发送
     * @return 日志id
     */
    Long createMailLog(Long userId, Integer userType, String mail, MessageMailAccountVO account, MessageMailTemplateVO template, String content, Map<String, Object> templateParams, Boolean isSend);

    /**
     * 更新发送状态
     *
     * @param logId 日志id
     * @param messageId 消息id
     * @param exception 异常信息
     */
    void updateMailSendResult(Long logId, String messageId, Exception exception);

    /**
     * 根据邮件日志主键查询邮件日志信息
     *
     * @param id 邮件日志主键
     * @return MessageMailLogVO
     */
    MessageMailLogVO getById(Long id);
}