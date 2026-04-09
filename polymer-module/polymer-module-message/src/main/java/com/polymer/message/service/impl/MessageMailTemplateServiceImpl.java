package com.polymer.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.constant.Constant;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.common.utils.StringUtils;
import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.message.entity.MessageMailAccountEntity;
import com.polymer.message.entity.MessageMailTemplateEntity;
import com.polymer.message.mapper.MessageMailAccountMapper;
import com.polymer.message.mapper.MessageMailTemplateMapper;
import com.polymer.message.query.MessageMailTemplateQuery;
import com.polymer.message.service.MessageMailTemplateService;
import com.polymer.message.vo.MessageMailTemplateVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 邮件模版表
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
@Service
public class MessageMailTemplateServiceImpl implements MessageMailTemplateService {
    @Resource
    private MessageMailTemplateMapper messageMailTemplateMapper;
    @Resource
    private MessageMailAccountMapper messageMailAccountMapper;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 根据邮件模版查询获取分页邮件模版列表
     *
     * @param query 邮件模版查询
     * @return PageResult<MessageMailTemplateVO>
     */
    @Override
    public PageResult<MessageMailTemplateVO> page(MessageMailTemplateQuery query) {

        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<MessageMailTemplateEntity> entityList = messageMailTemplateMapper.selectMessageMailTemplateList(query);
        PageInfo<MessageMailTemplateEntity> pageInfo = new PageInfo<>(entityList);
        List<MessageMailTemplateVO> templateVOS = ConvertUtils.convertListTo(entityList, MessageMailTemplateVO::new);
        for (MessageMailTemplateVO vo : templateVOS) {
            MessageMailAccountEntity account = messageMailAccountMapper.selectMessageMailAccountById(vo.getAccountId());
            vo.setMail(account.getMail());
        }
        return new PageResult<>(templateVOS, pageInfo.getTotal());
    }

    /**
     * 保存邮件模版信息
     *
     * @param vo 邮件模版信息
     * @return 保存数量
     */
    @Override
    public MessageMailTemplateVO save(MessageMailTemplateVO vo) {
        // 校验 code 是否唯一
        validateCodeUnique(null, vo.getCode());
        vo.setParams(parseTemplateContentParams(vo.getContent()));
        // 1. VO 转 Entity
        MessageMailTemplateEntity entity = ConvertUtils.convertTo(vo, MessageMailTemplateEntity::new);
        // 2. 插入数据（MyBatis 会自动回填 entity 的 id）
        messageMailTemplateMapper.insertMessageMailTemplate(entity);
        // 3. Entity 转 VO 并返回（此时 VO 中已包含生成的 id）
        return ConvertUtils.convertTo(entity, MessageMailTemplateVO::new);
    }

    public List<String> parseTemplateContentParams(String content) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
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
     * 更新邮件模版信息
     *
     * @param vo 邮件模版信息
     * @return 更新数量
     */
    @Override
    public MessageMailTemplateVO update(MessageMailTemplateVO vo) {
        // 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }
        // 校验 code 是否唯一
        validateCodeUnique(vo.getId(),vo.getCode());
        vo.setParams(parseTemplateContentParams(vo.getContent()));
        MessageMailTemplateEntity entity = ConvertUtils.convertTo(vo, MessageMailTemplateEntity::new);
        // 执行更新
        int rows = messageMailTemplateMapper.updateMessageMailTemplate(entity);
        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        MessageMailTemplateEntity updatedEntity = messageMailTemplateMapper.selectMessageMailTemplateById(vo.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return ConvertUtils.convertTo(updatedEntity, MessageMailTemplateVO::new);
    }

    void validateCodeUnique(Long id, String code) {
        MessageMailTemplateVO template = fildMailTemplateByCode(code);
        if (template == null) {
            return;
        }
        // 存在 template 记录的情况下
        if (id == null // 新增时，说明重复
                || !Objects.equals(template.getId(), id)) { // 更新时，如果 id 不一致，说明重复
            throw new ServiceException("模板编码重复");
        }
    }

    /**
     * 根据邮件模版主键集合删除邮件模版
     *
     * @param idList 邮件模版主键集合
     * @return 删除数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<Long> idList) {
        return batchUtils.executeBatch(MessageMailTemplateMapper.class, idList, MessageMailTemplateMapper::deleteMessageMailTemplateById);
    }

    /**
     * 根据账号id查询模板数量
     *
     * @param accountId 账号id
     * @return int
     */
    @Override
    public int findMailTemplateCountByAccountId(Long accountId) {
        List<MessageMailTemplateEntity> entityList = messageMailTemplateMapper.findMailTemplateCountByAccountId(accountId);
        if (entityList != null) {
            return entityList.size();
        }
        return 0;
    }

    /**
     * 根据模板编码查询模板信息
     *
     * @param templateCode 模板编码
     * @return MessageMailTemplateVO
     */
    @Override
    public MessageMailTemplateVO fildMailTemplateByCode(String templateCode) {
        MessageMailTemplateEntity entity = messageMailTemplateMapper.fildMailTemplateByCode(templateCode, Constant.ENABLE);
        return ConvertUtils.convertTo(entity, MessageMailTemplateVO::new);
    }

    /**
     * 根据邮件模版主键查询邮件模版信息
     *
     * @param id 邮件模版主键
     * @return MessageMailTemplateVO
     */
    @Override
    public MessageMailTemplateVO getById(Long id) {
        MessageMailTemplateEntity entity = messageMailTemplateMapper.selectMessageMailTemplateById(id);
        return ConvertUtils.convertTo(entity, MessageMailTemplateVO::new);
    }

}