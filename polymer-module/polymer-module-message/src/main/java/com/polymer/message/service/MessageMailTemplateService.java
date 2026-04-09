package com.polymer.message.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.message.query.MessageMailTemplateQuery;
import com.polymer.message.vo.MessageMailTemplateVO;

import java.util.List;

/**
 * 邮件模版表
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
public interface MessageMailTemplateService {

    /**
     * 根据邮件模版查询获取分页邮件模版列表
     *
     * @param query 邮件模版查询
     * @return PageResult<MessageMailTemplateVO>
     */
    PageResult<MessageMailTemplateVO> page(MessageMailTemplateQuery query);

    /**
     * 保存邮件模版信息
     *
     * @param vo 邮件模版信息
     * @return 保存数量
     */
    MessageMailTemplateVO save(MessageMailTemplateVO vo);

    /**
     * 更新邮件模版信息
     *
     * @param vo 邮件模版信息
     * @return 更新数量
     */
    MessageMailTemplateVO update(MessageMailTemplateVO vo);

    /**
     * 根据邮件模版主键集合删除邮件模版
     *
     * @param idList 邮件模版主键集合
     * @return 删除数量
     */
    int delete(List<Long> idList);

    /**
     * 根据账号id查询模板数量
     *
     * @param accountId 账号id
     * @return int
     */
    int findMailTemplateCountByAccountId(Long accountId);

    /**
     * 根据模板编码查询模板信息
     *
     * @param templateCode 模板编码
     * @return MessageMailTemplateVO
     */
    MessageMailTemplateVO fildMailTemplateByCode(String templateCode);

    /**
     * 根据邮件模版主键查询邮件模版信息
     *
     * @param id 邮件模版主键
     * @return MessageMailTemplateVO
     */
    MessageMailTemplateVO getById(Long id);
}