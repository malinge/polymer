package com.polymer.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.message.entity.MessageNotifyTemplateEntity;
import com.polymer.message.mapper.MessageNotifyTemplateMapper;
import com.polymer.message.query.MessageNotifyTemplateQuery;
import com.polymer.message.service.MessageNotifyTemplateService;
import com.polymer.message.vo.MessageNotifyTemplateVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 站内信模板
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-09
 */
@Service
public class MessageNotifyTemplateServiceImpl implements MessageNotifyTemplateService {
    @Resource
    private MessageNotifyTemplateMapper messageNotifyTemplateMapper;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 根据站内信模板查询获取分页站内信模板
     *
     * @param query 站内信模板查询
     * @return 结果
     */
    @Override
    public PageResult<MessageNotifyTemplateVO> page(MessageNotifyTemplateQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<MessageNotifyTemplateEntity> entityList = messageNotifyTemplateMapper.selectMessageNotifyTemplateList(query);
        PageInfo<MessageNotifyTemplateEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, MessageNotifyTemplateVO::new), pageInfo.getTotal());
    }

    /**
     * 保存站内信模板
     *
     * @param vo 站内信模板
     * @return 保存数量
     */
    @Override
    public MessageNotifyTemplateVO save(MessageNotifyTemplateVO vo) {
        //entity.setParams(parseTemplateContentParams(entity.getContent()));
        // 1. VO 转 Entity
        MessageNotifyTemplateEntity entity = ConvertUtils.convertTo(vo, MessageNotifyTemplateEntity::new);
        // 2. 插入数据（MyBatis 会自动回填 entity 的 id）
        messageNotifyTemplateMapper.insertMessageNotifyTemplate(entity);
        // 3. Entity 转 VO 并返回（此时 VO 中已包含生成的 id）
        return ConvertUtils.convertTo(entity, MessageNotifyTemplateVO::new);
    }

    public List<String> parseTemplateContentParams(String content) {
        List<String> results = new ArrayList<>();
        Pattern PATTERN_PARAMS = Pattern.compile("\\{(.*?)}");
        Matcher matcher = PATTERN_PARAMS.matcher(content);

        while (matcher.find()) { // 遍历所有匹配项
            // 确保存在分组1（索引从1开始）
            if (matcher.groupCount() >= 1) {
                results.add(matcher.group(1));
            }
        }
        return results;
    }

    /**
     * 更新站内信模板
     *
     * @param vo 站内信模板
     * @return 更新数量
     */
    @Override
    public MessageNotifyTemplateVO update(MessageNotifyTemplateVO vo) {
        // 1. 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }
        // 2. VO 转 Entity
        MessageNotifyTemplateEntity entity = ConvertUtils.convertTo(vo, MessageNotifyTemplateEntity::new);
        // 3. 执行更新
        int rows = messageNotifyTemplateMapper.updateMessageNotifyTemplate(entity);
        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        MessageNotifyTemplateEntity updatedEntity = messageNotifyTemplateMapper.selectMessageNotifyTemplateById(vo.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return ConvertUtils.convertTo(updatedEntity, MessageNotifyTemplateVO::new);
    }

    /**
     * 删除站内信模板
     *
     * @param idList 站内信模板id集合
     * @return 删除数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<Long> idList) {
        return batchUtils.executeBatch(MessageNotifyTemplateMapper.class, idList, MessageNotifyTemplateMapper::deleteMessageNotifyTemplateById);
    }

    /**
     * 根据模版编码获取站内信模板信息
     *
     * @param templateCode 模版编码
     * @return 结果
     */
    @Override
    public MessageNotifyTemplateVO validateNotifyTemplate(String templateCode) {
        MessageNotifyTemplateEntity entity = messageNotifyTemplateMapper.validateNotifyTemplate(templateCode);
        return ConvertUtils.convertTo(entity, MessageNotifyTemplateVO::new);
    }

    /**
     * 查询站内信模板表
     *
     * @param id 站内信模板表主键
     * @return 站内信模板表
     */
    @Override
    public MessageNotifyTemplateVO getById(Long id) {
        MessageNotifyTemplateEntity entity = messageNotifyTemplateMapper.selectMessageNotifyTemplateById(id);
        return ConvertUtils.convertTo(entity, MessageNotifyTemplateVO::new);
    }

}