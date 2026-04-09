package com.polymer.message.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.message.query.MessageMailAccountQuery;
import com.polymer.message.vo.MessageMailAccountVO;

import java.util.List;

/**
 * 邮箱账号表
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
public interface MessageMailAccountService {

    /**
     * 根据邮箱账号查询获取分页邮箱账号列表
     *
     * @param query 邮箱账号查询
     * @return PageResult<MessageMailAccountVO>
     */
    PageResult<MessageMailAccountVO> page(MessageMailAccountQuery query);

    /**
     * 保存邮箱账号信息
     *
     * @param vo 邮箱账号信息
     * @return 保存数量
     */
    MessageMailAccountVO save(MessageMailAccountVO vo);

    /**
     * 更新邮箱账号信息
     *
     * @param vo 邮箱账号信息
     * @return 更新数量
     */
    MessageMailAccountVO update(MessageMailAccountVO vo);

    /**
     * 根据邮箱账号主键集合删除邮箱账号
     *
     * @param idList 邮箱账号主键集合
     * @return 删除数量
     */
    int delete(List<Long> idList);

    /**
     * 根据邮箱账号主键查询邮箱账号信息
     *
     * @param id 邮箱账号主键
     * @return MessageMailAccountVO
     */
    MessageMailAccountVO getById(Long id);

    /**
     * 查询全部邮箱账号信息
     *
     * @return List<MessageMailAccountVO>
     */
    List<MessageMailAccountVO> list();
}