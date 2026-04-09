package com.polymer.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.constant.Constant;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.message.entity.MessageSmsPlatformEntity;
import com.polymer.message.mapper.MessageSmsPlatformMapper;
import com.polymer.message.query.MessageSmsPlatformQuery;
import com.polymer.message.service.MessageSmsPlatformService;
import com.polymer.message.sms.config.SmsConfig;
import com.polymer.message.vo.MessageSmsPlatformVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 短信平台
 *
 * @author polymer
 */
@Service
public class MessageSmsPlatformServiceImpl implements MessageSmsPlatformService {
    @Resource
    private MessageSmsPlatformMapper messageSmsPlatformMapper;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 根据短信平台查询获取分页短信平台
     *
     * @param query 短信平台查询
     * @return 结果
     */
    @Override
    public PageResult<MessageSmsPlatformVO> page(MessageSmsPlatformQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<MessageSmsPlatformEntity> entityList = messageSmsPlatformMapper.selectMessageSmsPlatformList(query);
        PageInfo<MessageSmsPlatformEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, MessageSmsPlatformVO::new), pageInfo.getTotal());
    }

    /**
     * 启用的短信平台列表
     *
     * @return 结果
     */
    @Override
    public List<SmsConfig> listByEnable() {
        List<MessageSmsPlatformEntity> list = messageSmsPlatformMapper.selectListByStatus(Constant.ENABLE);

        return ConvertUtils.convertListTo(list, SmsConfig::new);
    }

    /**
     * 保存短信平台信息
     *
     * @param vo 短信平台信息
     * @return 保存个数
     */
    @Override
    public MessageSmsPlatformVO save(MessageSmsPlatformVO vo) {
        // 1. VO 转 Entity
        MessageSmsPlatformEntity entity = ConvertUtils.convertTo(vo, MessageSmsPlatformEntity::new);
        // 2. 插入数据（MyBatis 会自动回填 entity 的 id）
        messageSmsPlatformMapper.insertMessageSmsPlatform(entity);
        // 3. Entity 转 VO 并返回（此时 VO 中已包含生成的 id）
        return ConvertUtils.convertTo(entity, MessageSmsPlatformVO::new);
    }

    /**
     * 更新短信平台信息
     *
     * @param vo 短信平台信息
     * @return 更新个数
     */
    @Override
    public MessageSmsPlatformVO update(MessageSmsPlatformVO vo) {
        // 1. 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }
        // 2. VO 转 Entity
        MessageSmsPlatformEntity entity = ConvertUtils.convertTo(vo, MessageSmsPlatformEntity::new);
        // 3. 执行更新
        int rows = messageSmsPlatformMapper.updateMessageSmsPlatform(entity);
        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        MessageSmsPlatformEntity updatedEntity = messageSmsPlatformMapper.selectMessageSmsPlatformById(vo.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return ConvertUtils.convertTo(updatedEntity, MessageSmsPlatformVO::new);
    }

    /**
     * 根据短信平台主键删除短信平台信息
     *
     * @param idList 短信平台主键
     * @return 更新个数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<Long> idList) {
        return batchUtils.executeBatch(MessageSmsPlatformMapper.class, idList, MessageSmsPlatformMapper::deleteMessageSmsPlatformById);
    }

    /**
     * 根据短信平台主键查询短信平台信息
     *
     * @param id 短信平台主键
     * @return 短信平台信息
     */
    @Override
    public MessageSmsPlatformVO getById(Long id) {
        MessageSmsPlatformEntity entity = messageSmsPlatformMapper.selectMessageSmsPlatformById(id);
        return ConvertUtils.convertTo(entity, MessageSmsPlatformVO::new);
    }

}