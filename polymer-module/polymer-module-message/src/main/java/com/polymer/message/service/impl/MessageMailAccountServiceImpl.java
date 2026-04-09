package com.polymer.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.message.entity.MessageMailAccountEntity;
import com.polymer.message.mapper.MessageMailAccountMapper;
import com.polymer.message.query.MessageMailAccountQuery;
import com.polymer.message.service.MessageMailAccountService;
import com.polymer.message.service.MessageMailTemplateService;
import com.polymer.message.vo.MessageMailAccountVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 邮箱账号表
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2024-04-01
 */
@Service
public class MessageMailAccountServiceImpl implements MessageMailAccountService {
    @Resource
    private MessageMailAccountMapper messageMailAccountMapper;
    @Resource
    private MessageMailTemplateService messageMailTemplateService;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 根据邮箱账号查询获取分页邮箱账号列表
     *
     * @param query 邮箱账号查询
     * @return PageResult<MessageMailAccountVO>
     */
    @Override
    public PageResult<MessageMailAccountVO> page(MessageMailAccountQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<MessageMailAccountEntity> entityList = messageMailAccountMapper.selectMessageMailAccountList(query);
        PageInfo<MessageMailAccountEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, MessageMailAccountVO::new), pageInfo.getTotal());
    }

    /**
     * 保存邮箱账号信息
     *
     * @param vo 邮箱账号信息
     * @return 保存数量
     */
    @Override
    public MessageMailAccountVO save(MessageMailAccountVO vo) {
        // 1. VO 转 Entity
        MessageMailAccountEntity entity = ConvertUtils.convertTo(vo, MessageMailAccountEntity::new);
        // 2. 插入数据（MyBatis 会自动回填 entity 的 id）
        messageMailAccountMapper.insertMessageMailAccount(entity);
        // 3. Entity 转 VO 并返回（此时 VO 中已包含生成的 id）
        return ConvertUtils.convertTo(entity, MessageMailAccountVO::new);
    }

    /**
     * 更新邮箱账号信息
     *
     * @param vo 邮箱账号信息
     * @return 更新数量
     */
    @Override
    public MessageMailAccountVO update(MessageMailAccountVO vo) {
        // 1. 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }
        // 2. VO 转 Entity
        MessageMailAccountEntity entity = ConvertUtils.convertTo(vo, MessageMailAccountEntity::new);
        // 3. 执行更新
        int rows = messageMailAccountMapper.updateMessageMailAccount(entity);
        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        MessageMailAccountEntity updatedEntity = messageMailAccountMapper.selectMessageMailAccountById(vo.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return ConvertUtils.convertTo(updatedEntity, MessageMailAccountVO::new);
    }

    /**
     * 根据邮箱账号主键集合删除邮箱账号
     *
     * @param idList 邮箱账号主键集合
     * @return 删除数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<Long> idList) {
        for (Long id : idList) {
            if (messageMailTemplateService.findMailTemplateCountByAccountId(id) > 0) {
                throw new RuntimeException("无法删除，该邮箱账号还有邮件模板");
            }
        }
        return batchUtils.executeBatch(MessageMailAccountMapper.class, idList, MessageMailAccountMapper::deleteMessageMailAccountById);
    }

    /**
     * 根据邮箱账号主键查询邮箱账号信息
     *
     * @param id 邮箱账号主键
     * @return MessageMailAccountVO
     */
    @Override
    public MessageMailAccountVO getById(Long id) {
        MessageMailAccountEntity entity = messageMailAccountMapper.selectMessageMailAccountById(id);
        return ConvertUtils.convertTo(entity, MessageMailAccountVO::new);
    }

    /**
     * 查询全部邮箱账号信息
     *
     * @return List<MessageMailAccountVO>
     */
    @Override
    public List<MessageMailAccountVO> list() {
        List<MessageMailAccountEntity> entityList = messageMailAccountMapper.selectMessageMailAccountList(null);
        return ConvertUtils.convertListTo(entityList, MessageMailAccountVO::new);
    }
}