package com.polymer.system.service.impl;

import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.common.utils.StringUtils;
import com.polymer.framework.common.utils.TreeUtils;
import com.polymer.system.entity.SysCityEntity;
import com.polymer.system.entity.SysDeptEntity;
import com.polymer.system.mapper.SysDeptMapper;
import com.polymer.system.mapper.SysUserMapper;
import com.polymer.system.query.SysDeptQuery;
import com.polymer.system.service.SysDeptService;
import com.polymer.system.vo.SysCityVO;
import com.polymer.system.vo.SysDeptVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门管理
 *
 * @author polymer
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {
    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 获取部门树结构数据
     *
     * @return List<SysOrgVO>
     */
    @Override
    public List<SysDeptVO> getSysDeptList() {
        SysDeptQuery param = new SysDeptQuery();
        // 部门列表
        List<SysDeptEntity> entityList = sysDeptMapper.getSysDeptList(param);

        return TreeUtils.build(ConvertUtils.convertListTo(entityList, SysDeptVO::new));
    }

    /**
     * 保存部门信息
     *
     * @param vo 部门信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDeptVO save(SysDeptVO vo) {
        SysDeptEntity entity = ConvertUtils.convertTo(vo, SysDeptEntity::new);

        sysDeptMapper.insertSysDept(entity);

        // 计算部门路径，方便查询所属子集集合
        if(entity.getPid()!=null){
            SysDeptEntity parent = sysDeptMapper.selectSysDeptById(vo.getPid());
            entity.setDeptPath(parent.getDeptPath() + entity.getId() + "/");
        }else {
            entity.setDeptPath("/" + entity.getId() + "/");
        }
        sysDeptMapper.updateSysDept(entity);

        return ConvertUtils.convertTo(entity, SysDeptVO::new);

    }

    /**
     * 更新部门信息
     *
     * @param vo 部门信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDeptVO update(SysDeptVO vo) {

        // 1. 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }

        // 上级部门不能为自身
        if (vo.getId().equals(vo.getPid())) {
            throw new ServiceException("上级部门不能为自身");
        }

        // 上级部门不能为下级
        List<Long> subDeptList = getDeptTreeIds(vo.getId());
        if (subDeptList.contains(vo.getPid())) {
            throw new ServiceException("上级部门不能为下级");
        }

        // 更新菜单
        SysDeptEntity entity = ConvertUtils.convertTo(vo, SysDeptEntity::new);
        // 计算部门路径，方便查询所属子集集合
        if(entity.getPid()!=null){
            SysDeptEntity parent = sysDeptMapper.selectSysDeptById(vo.getPid());
            entity.setDeptPath(parent.getDeptPath() + entity.getId() + "/");
        }else {
            entity.setDeptPath("/" + entity.getId() + "/");
        }
        int rows = sysDeptMapper.updateSysDept(entity);

        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 4. 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        SysDeptEntity updatedEntity = sysDeptMapper.selectSysDeptById(vo.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return ConvertUtils.convertTo(updatedEntity, SysDeptVO::new);
    }

    /**
     * 删除部门信息
     *
     * @param id 部门信息主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 判断是否有子部门
        long orgCount = sysDeptMapper.selectCountByPid(id);
        if (orgCount > 0) {
            throw new ServiceException("请先删除子部门");
        }

        // 判断部门下面是否有用户
        long userCount = sysUserMapper.selectCountByDeptId(id);
        if (userCount > 0) {
            throw new ServiceException("部门下面有用户，不能删除");
        }

        // 删除
        sysDeptMapper.deleteSysDeptById(id);
    }

    /**
     * 根据部门ID，获取部门及其所有子部门ID
     *
     * @param id 部门ID
     * @return List<Long>
     */
    @Override
    public List<Long> getDeptTreeIds(Long id) {
        SysDeptEntity current = sysDeptMapper.selectSysDeptById(id);
        return sysDeptMapper.selectSubDeptIdsByPath(current.getDeptPath());
    }

    /**
     * 查询用户担任负责人的部门ID
     *
     * @param userId 用户ID
     * @return List<Long>
     */
    @Override
    public List<Long> getDeptIdsByLeaderId(Long userId) {
        return sysDeptMapper.selectDeptIdsByLeaderId(userId);
    }

    /**
     * 根据部门信息主键查询部门信息
     *
     * @param id 部门信息主键
     * @return SysOrgVO
     */
    @Override
    public SysDeptVO getById(Long id) {
        SysDeptEntity entity = sysDeptMapper.selectSysDeptById(id);
        return ConvertUtils.convertTo(entity, SysDeptVO::new);
    }

    /**
     * 校验部门是否有数据权限
     *
     * @param deptId 部门id
     */
    @Override
    public void checkDeptDataScope(Long deptId) {
        if (StringUtils.isNotNull(deptId)) {
            SysDeptEntity entity = sysDeptMapper.selectSysDeptById(deptId);
            if (entity == null) {
                throw new ServiceException("没有权限访问部门数据！");
            }
        }
    }

    @Override
    public List<SysDeptVO> simpleList() {
        SysDeptQuery param = new SysDeptQuery();
        // 部门列表
        List<SysDeptEntity> entityList = sysDeptMapper.getSysDeptList(param);

        return ConvertUtils.convertListTo(entityList, SysDeptVO::new);
    }

}
