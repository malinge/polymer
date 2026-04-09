package com.polymer.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.system.entity.SysPostEntity;
import com.polymer.system.mapper.SysPostMapper;
import com.polymer.system.query.SysPostQuery;
import com.polymer.system.service.SysPostService;
import com.polymer.system.service.SysUserPostService;
import com.polymer.system.vo.SysPostVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 岗位管理
 *
 * @author polymer
 */
@Service
public class SysPostServiceImpl implements SysPostService {
    @Resource
    private SysPostMapper sysPostMapper;
    @Resource
    private SysUserPostService sysUserPostService;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 根据岗位查询获取分页岗位列表
     *
     * @param query 岗位查询
     * @return PageResult<SysPostVO>
     */
    @Override
    public PageResult<SysPostVO> page(SysPostQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<SysPostEntity> entityList = sysPostMapper.selectSysPostList(query);
        PageInfo<SysPostEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, SysPostVO::new), pageInfo.getTotal());
    }

    /**
     * 查询全部岗位信息集合
     *
     * @return List<SysPostVO>
     */
    @Override
    public List<SysPostVO> getList() {
        SysPostQuery query = new SysPostQuery();
        query.setStatus(1);
        List<SysPostEntity> entityList = sysPostMapper.selectSysPostList(query);
        return ConvertUtils.convertListTo(entityList, SysPostVO::new);
    }

    /**
     * 根据岗位主键集合查询岗位名称集合
     *
     * @param idList 岗位主键集合
     * @return List<String>
     */
    @Override
    public List<String> getNameList(List<Long> idList) {
        if (idList.isEmpty()) {
            return null;
        }
        SysPostQuery query = new SysPostQuery();
        query.setIdList(idList);
        return sysPostMapper.selectSysPostList(query).stream().map(SysPostEntity::getPostName).collect(Collectors.toList());
    }

    /**
     * 保存岗位信息
     *
     * @param vo 岗位信息
     */
    @Override
    public SysPostVO save(SysPostVO vo) {
        // 1. VO 转 Entity
        SysPostEntity entity = ConvertUtils.convertTo(vo, SysPostEntity::new);
        // 2. 插入数据（MyBatis 会自动回填 entity 的 id）
        sysPostMapper.insertSysPost(entity);
        // 3. Entity 转 VO 并返回（此时 VO 中已包含生成的 id）
        return ConvertUtils.convertTo(entity, SysPostVO::new);
    }

    /**
     * 更新岗位信息
     *
     * @param vo 岗位信息
     */
    @Override
    public SysPostVO update(SysPostVO vo) {
        // 1. 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }
        // 2. VO 转 Entity
        SysPostEntity entity = ConvertUtils.convertTo(vo, SysPostEntity::new);
        // 3. 执行更新
        int rows = sysPostMapper.updateSysPost(entity);
        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        SysPostEntity updatedEntity = sysPostMapper.selectSysPostById(vo.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return ConvertUtils.convertTo(updatedEntity, SysPostVO::new);
    }

    /**
     * 根据岗位主键集合删除岗位信息
     *
     * @param idList 岗位主键集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        // 删除岗位
        batchUtils.executeBatch(SysPostMapper.class, idList, SysPostMapper::deleteSysPostById);

        // 删除岗位用户关系
        sysUserPostService.deleteByPostIdList(idList);
    }

    /**
     * 根据岗位主键查询岗位信息
     *
     * @param id 岗位主键
     * @return SysPostVO
     */
    @Override
    public SysPostVO getById(Long id) {
        SysPostEntity entity = sysPostMapper.selectSysPostById(id);
        return ConvertUtils.convertTo(entity, SysPostVO::new);
    }

}