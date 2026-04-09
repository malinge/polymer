package com.polymer.message.mapper;

import com.polymer.framework.mybatis.core.annotation.DataScope;
import com.polymer.message.entity.MessageMailAccountEntity;
import com.polymer.message.query.MessageMailAccountQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 邮箱账号表
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
@Mapper
public interface MessageMailAccountMapper {
    /**
     * 根据邮箱账号表查询获取邮箱账号集合
     *
     * @param query 邮箱账号表查询
     * @return 结果
     */
    @DataScope
    List<MessageMailAccountEntity> selectMessageMailAccountList(MessageMailAccountQuery query);

    /**
     * 查询邮箱账号表
     *
     * @param id 邮箱账号表主键
     * @return 邮箱账号表
     */
    MessageMailAccountEntity selectMessageMailAccountById(Long id);

    /**
     * 新增邮箱账号表
     *
     * @param messageMailAccount 邮箱账号表
     * @return 结果
     */
    int insertMessageMailAccount(MessageMailAccountEntity messageMailAccount);

    /**
     * 修改邮箱账号表
     *
     * @param messageMailAccount 邮箱账号表
     * @return 结果
     */
    int updateMessageMailAccount(MessageMailAccountEntity messageMailAccount);

    /**
     * 删除邮箱账号表
     *
     * @param id 邮箱账号表主键
     * @return 结果
     */
    int deleteMessageMailAccountById(Long id);
}