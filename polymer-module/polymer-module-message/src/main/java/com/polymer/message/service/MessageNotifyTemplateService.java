package com.polymer.message.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.message.query.MessageNotifyTemplateQuery;
import com.polymer.message.vo.MessageNotifyTemplateVO;

import java.util.List;

/**
 * 站内信模板
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-09
 */
public interface MessageNotifyTemplateService {

    /**
     * 根据站内信模板查询获取分页站内信模板
     *
     * @param query 站内信模板查询
     * @return 结果
     */
    PageResult<MessageNotifyTemplateVO> page(MessageNotifyTemplateQuery query);

    /**
     * 保存站内信模板
     *
     * @param vo 站内信模板
     * @return 保存数量
     */
    MessageNotifyTemplateVO save(MessageNotifyTemplateVO vo);

    /**
     * 更新站内信模板
     *
     * @param vo 站内信模板
     * @return 更新数量
     */
    MessageNotifyTemplateVO update(MessageNotifyTemplateVO vo);

    /**
     * 删除站内信模板
     *
     * @param idList 站内信模板id集合
     * @return 删除数量
     */
    int delete(List<Long> idList);

    /**
     * 根据模版编码获取站内信模板信息
     *
     * @param templateCode 模版编码
     * @return 结果
     */
    MessageNotifyTemplateVO validateNotifyTemplate(String templateCode);

    /**
     * 查询站内信模板表
     *
     * @param id 站内信模板表主键
     * @return 站内信模板表
     */
    MessageNotifyTemplateVO getById(Long id);
}