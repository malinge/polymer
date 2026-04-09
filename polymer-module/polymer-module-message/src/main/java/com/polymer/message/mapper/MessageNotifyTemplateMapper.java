package com.polymer.message.mapper;

import com.polymer.framework.mybatis.core.annotation.DataScope;
import com.polymer.message.entity.MessageNotifyTemplateEntity;
import com.polymer.message.query.MessageNotifyTemplateQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 站内信模板
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-09
 */
@Mapper
public interface MessageNotifyTemplateMapper {

    /**
     * 根据站内信模板查询获取站内信模板集合
     *
     * @param query 站内信模板查询
     * @return 结果
     */
    @DataScope
    List<MessageNotifyTemplateEntity> selectMessageNotifyTemplateList(MessageNotifyTemplateQuery query);

    /**
     * 根据模版编码获取站内信模板信息
     *
     * @param code 模版编码
     * @return 结果
     */
    MessageNotifyTemplateEntity validateNotifyTemplate(String code);

    /**
     * 查询站内信模板表
     *
     * @param id 站内信模板表主键
     * @return 站内信模板表
     */
    MessageNotifyTemplateEntity selectMessageNotifyTemplateById(Long id);

    /**
     * 新增站内信模板表
     *
     * @param messageNotifyTemplate 站内信模板表
     * @return 结果
     */
    int insertMessageNotifyTemplate(MessageNotifyTemplateEntity messageNotifyTemplate);

    /**
     * 修改站内信模板表
     *
     * @param messageNotifyTemplate 站内信模板表
     * @return 结果
     */
    int updateMessageNotifyTemplate(MessageNotifyTemplateEntity messageNotifyTemplate);

    /**
     * 删除站内信模板表
     *
     * @param id 站内信模板表主键
     * @return 结果
     */
    int deleteMessageNotifyTemplateById(Long id);
}