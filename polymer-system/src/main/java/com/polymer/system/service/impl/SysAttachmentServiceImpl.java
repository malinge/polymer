package com.polymer.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.system.entity.SysAttachmentEntity;
import com.polymer.system.entity.SysPostEntity;
import com.polymer.system.mapper.SysAttachmentMapper;
import com.polymer.system.query.SysAttachmentQuery;
import com.polymer.system.service.SysAttachmentService;
import com.polymer.system.vo.SysAttachmentVO;
import com.polymer.system.vo.SysPostVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 附件管理
 *
 * @author polymer
 */
@Service
public class SysAttachmentServiceImpl implements SysAttachmentService {
    @Resource
    private SysAttachmentMapper sysAttachmentMapper;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 根据附件管理查询获取分页附件数据
     *
     * @param query 附件管理查询
     * @return 结果
     */
    @Override
    public PageResult<SysAttachmentVO> selectSysAttachmentList(SysAttachmentQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<SysAttachmentEntity> entityList = sysAttachmentMapper.selectSysAttachmentList(query);
        PageInfo<SysAttachmentEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, SysAttachmentVO::new), pageInfo.getTotal());
    }

    /**
     * 保存附件
     *
     * @param vo 附件
     * @return 保存数量
     */
    @Override
    public SysAttachmentVO insertSysAttachment(SysAttachmentVO vo) {
        // 1. VO 转 Entity
        SysAttachmentEntity entity = ConvertUtils.convertTo(vo, SysAttachmentEntity::new);
        // 2. 插入数据（MyBatis 会自动回填 entity 的 id）
        sysAttachmentMapper.insertSysAttachment(entity);
        // 3. Entity 转 VO 并返回（此时 VO 中已包含生成的 id）
        return ConvertUtils.convertTo(entity, SysAttachmentVO::new);
    }

    /**
     * 更新附件
     *
     * @param sysAttachment 附件
     * @return 更新数量
     */
    @Override
    public int updateSysAttachment(SysAttachmentEntity sysAttachment) {
        return sysAttachmentMapper.updateSysAttachment(sysAttachment);
    }

    /**
     * 根据附件主键集合删除附件信息
     *
     * @param idList 附件主键集合
     * @return 删除数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteSysAttachmentByIds(List<Long> idList) {
        return batchUtils.executeBatch(SysAttachmentMapper.class, idList, SysAttachmentMapper::deleteSysAttachmentById);
    }

    /**
     * 批量保存附件集合
     *
     * @param list 附件集合
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchSysAttachment(List<SysAttachmentEntity> list) {
        return batchUtils.executeBatch(SysAttachmentMapper.class, list, SysAttachmentMapper::insertSysAttachment);
    }

    /**
     * 根据业务标识和业务类型查询附件集合
     *
     * @param bizMark 业务标识
     * @param bizType 业务类型
     * @return 结果
     */
    @Override
    public List<SysAttachmentEntity> findListByBizMark(String bizMark, String bizType) {
        return sysAttachmentMapper.selectSysAttachmentByBizMark(bizMark, bizType);
    }

    /**
     * 根据业务标识和业务类型删除附件
     *
     * @param bizMark 业务标识
     * @param bizType 业务类型
     * @return 结果
     */
    @Override
    public int delByBizMark(String bizMark, String bizType) {
        return sysAttachmentMapper.delSysAttachmentByBizMark(bizMark, bizType);
    }

}