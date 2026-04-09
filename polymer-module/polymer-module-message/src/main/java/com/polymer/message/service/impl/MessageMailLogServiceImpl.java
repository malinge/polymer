package com.polymer.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.message.entity.MessageMailLogEntity;
import com.polymer.message.enums.MailSendStatusEnum;
import com.polymer.message.mapper.MessageMailLogMapper;
import com.polymer.message.query.MessageMailLogQuery;
import com.polymer.message.service.MessageMailLogService;
import com.polymer.message.vo.MessageMailAccountVO;
import com.polymer.message.vo.MessageMailLogVO;
import com.polymer.message.vo.MessageMailTemplateVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 邮件日志表
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
@Service
public class MessageMailLogServiceImpl implements MessageMailLogService {
    @Resource
    private MessageMailLogMapper messageMailLogMapper;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 根据邮件日志查询获取分页邮件日志列表
     *
     * @param query 邮件日志查询
     * @return PageResult<MessageMailLogVO>
     */
    @Override
    public PageResult<MessageMailLogVO> page(MessageMailLogQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<MessageMailLogEntity> entityList = messageMailLogMapper.selectMessageMailLogList(query);
        PageInfo<MessageMailLogEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, MessageMailLogVO::new), pageInfo.getTotal());
    }

    /**
     * 保存邮件日志信息
     *
     * @param vo 邮件日志信息
     * @return 保存数量
     */
    @Override
    public MessageMailLogVO save(MessageMailLogVO vo) {
        // 1. VO 转 Entity
        MessageMailLogEntity entity = ConvertUtils.convertTo(vo, MessageMailLogEntity::new);
        // 2. 插入数据（MyBatis 会自动回填 entity 的 id）
        messageMailLogMapper.insertMessageMailLog(entity);
        // 3. Entity 转 VO 并返回（此时 VO 中已包含生成的 id）
        return ConvertUtils.convertTo(entity, MessageMailLogVO::new);
    }

    /**
     * 更新邮件日志信息
     *
     * @param vo 邮件日志信息
     * @return 更新数量
     */
    @Override
    public MessageMailLogVO update(MessageMailLogVO vo) {
        // 1. 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }
        // 2. VO 转 Entity
        MessageMailLogEntity entity = ConvertUtils.convertTo(vo, MessageMailLogEntity::new);
        // 3. 执行更新
        int rows = messageMailLogMapper.updateMessageMailLog(entity);
        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        MessageMailLogEntity updatedEntity = messageMailLogMapper.selectMessageMailLogById(vo.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return ConvertUtils.convertTo(updatedEntity, MessageMailLogVO::new);
    }

    /**
     * 根据邮件日志主键集合删除邮件日志
     *
     * @param idList 邮件日志主键集合
     * @return 删除数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<Long> idList) {
        return batchUtils.executeBatch(MessageMailLogMapper.class, idList, MessageMailLogMapper::deleteMessageMailLogById);
    }

    /**
     * 保存邮件日志
     *
     * @param userId 用户id
     * @param userType 用户类型
     * @param toMail 邮箱
     * @param account 账户
     * @param template 模板
     * @param templateContent 内容
     * @param templateParams 模板参数
     * @param isSend 是否发送
     * @return 日志id
     */
    @Override
    public Long createMailLog(Long userId, Integer userType, String toMail, MessageMailAccountVO account,
                              MessageMailTemplateVO template, String templateContent,
                              Map<String, Object> templateParams, Boolean isSend) {
        MessageMailLogEntity entity = new MessageMailLogEntity();
        // 根据是否要发送，设置状态
        entity.setSendStatus(Objects.equals(isSend, true) ? MailSendStatusEnum.INIT.getStatus()
                : MailSendStatusEnum.IGNORE.getStatus());
        // 用户信息
        entity.setUserId(userId);
        entity.setUserType(userType);
        entity.setToMail(toMail);
        entity.setAccountId(account.getId());
        entity.setFromMail(account.getMail());
        // 模板相关字段
        entity.setTemplateId(template.getId());
        entity.setTemplateCode(template.getCode());
        entity.setTemplateNickname(template.getNickname());
        entity.setTemplateTitle(template.getTitle());
        entity.setTemplateContent(templateContent);
        entity.setTemplateParams(templateParams);
        messageMailLogMapper.insertMessageMailLog(entity);
        return entity.getId();
    }

    /**
     * 更新发送状态
     *
     * @param logId 日志id
     * @param messageId 消息id
     * @param exception 异常信息
     */
    @Override
    public void updateMailSendResult(Long logId, String messageId, Exception exception) {
        MessageMailLogEntity messageMailLog = messageMailLogMapper.selectMessageMailLogById(logId);
        //成功
        if (exception == null) {
            messageMailLog.setSendMessageId(messageId);
            messageMailLog.setSendStatus(MailSendStatusEnum.SUCCESS.getStatus());

        } else {
            messageMailLog.setSendException(exception.getMessage());
            messageMailLog.setSendStatus(MailSendStatusEnum.FAILURE.getStatus());
        }
        messageMailLogMapper.updateMessageMailLog(messageMailLog);
    }

    /**
     * 根据邮件日志主键查询邮件日志信息
     *
     * @param id 邮件日志主键
     * @return MessageMailLogVO
     */
    @Override
    public MessageMailLogVO getById(Long id) {
        MessageMailLogEntity entity = messageMailLogMapper.selectMessageMailLogById(id);
        return ConvertUtils.convertTo(entity, MessageMailLogVO::new);
    }

}