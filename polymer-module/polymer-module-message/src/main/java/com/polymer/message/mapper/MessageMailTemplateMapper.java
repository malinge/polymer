package com.polymer.message.mapper;

import com.polymer.framework.mybatis.core.annotation.DataScope;
import com.polymer.message.entity.MessageMailTemplateEntity;
import com.polymer.message.query.MessageMailTemplateQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 邮件模版表
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
@Mapper
public interface MessageMailTemplateMapper {

    /**
     * 根据邮件模版查询获取邮件模版集合
     *
     * @param query 邮件模版查询
     * @return 结果
     */
    @DataScope
    List<MessageMailTemplateEntity> selectMessageMailTemplateList(MessageMailTemplateQuery query);

    /**
     * 根据模板编码和开启状态查询邮件模版信息
     *
     * @param code   模板编码
     * @param status 开启状态
     * @return 结果
     */
    MessageMailTemplateEntity fildMailTemplateByCode(@Param("code") String code, @Param("status") Integer status);

    /**
     * 根据发送的邮箱账号编号查询邮件模版集合
     *
     * @param accountId 发送的邮箱账号编号
     * @return 结果
     */
    List<MessageMailTemplateEntity> findMailTemplateCountByAccountId(Long accountId);

    /**
     * 查询邮件模版表
     *
     * @param id 邮件模版表主键
     * @return 邮件模版表
     */
    MessageMailTemplateEntity selectMessageMailTemplateById(Long id);

    /**
     * 新增邮件模版表
     *
     * @param messageMailTemplate 邮件模版表
     * @return 结果
     */
    int insertMessageMailTemplate(MessageMailTemplateEntity messageMailTemplate);

    /**
     * 修改邮件模版表
     *
     * @param messageMailTemplate 邮件模版表
     * @return 结果
     */
    int updateMessageMailTemplate(MessageMailTemplateEntity messageMailTemplate);

    /**
     * 删除邮件模版表
     *
     * @param id 邮件模版表主键
     * @return 结果
     */
    int deleteMessageMailTemplateById(Long id);
}