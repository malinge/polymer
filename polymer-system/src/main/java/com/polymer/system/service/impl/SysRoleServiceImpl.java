package com.polymer.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.mybatis.core.enums.DataScopeEnum;
import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.system.entity.SysRoleEntity;
import com.polymer.system.mapper.SysRoleMapper;
import com.polymer.system.query.SysRoleQuery;
import com.polymer.system.service.SysRoleDataScopeService;
import com.polymer.system.service.SysRoleMenuService;
import com.polymer.system.service.SysRoleService;
import com.polymer.system.service.SysUserRoleService;
import com.polymer.system.service.SysUserTokenService;
import com.polymer.system.vo.SysRoleDataScopeVO;
import com.polymer.system.vo.SysRoleVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 角色
 *
 * @author polymer
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private SysRoleDataScopeService sysRoleDataScopeService;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysUserTokenService sysUserTokenService;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 根据角色查询获取分页角色列表
     *
     * @param query 角色查询
     * @return PageResult<SysRoleVO>
     */
    @Override
    public PageResult<SysRoleVO> page(SysRoleQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<SysRoleEntity> entityList = sysRoleMapper.selectSysRoleList(query);
        PageInfo<SysRoleEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(ConvertUtils.convertListTo(entityList, SysRoleVO::new), pageInfo.getTotal());
    }

    /**
     * 根据角色查询获取角色集合
     *
     * @param query 角色查询
     * @return List<SysRoleVO>
     */
    @Override
    public List<SysRoleVO> getList(SysRoleQuery query) {
        List<SysRoleEntity> entityList = sysRoleMapper.selectSysRoleList(query);
        return ConvertUtils.convertListTo(entityList, SysRoleVO::new);
    }

    /**
     * 保存角色信息
     *
     * @param vo 角色信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysRoleVO save(SysRoleVO vo) {
        SysRoleEntity entity = ConvertUtils.convertTo(vo, SysRoleEntity::new);
        // 保存角色
        entity.setDataScope(DataScopeEnum.SELF.getValue());
        sysRoleMapper.insertSysRole(entity);

        // 保存角色菜单关系
        sysRoleMenuService.saveOrUpdate(entity.getId(), entity.getMenuIdList());

        return ConvertUtils.convertTo(entity, SysRoleVO::new);
    }

    /**
     * 更新角色信息
     *
     * @param vo 角色信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysRoleVO update(SysRoleVO vo) {
        // 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }

        SysRoleEntity entity = ConvertUtils.convertTo(vo, SysRoleEntity::new);
        // 更新角色
        int rows = sysRoleMapper.updateSysRole(entity);

        // 更新角色菜单关系
        sysRoleMenuService.saveOrUpdate(entity.getId(), entity.getMenuIdList());

        // 更新角色对应用户的缓存权限
        sysUserTokenService.updateCacheAuthByRoleId(entity.getId());

        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }
        // 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        SysRoleEntity updatedEntity = sysRoleMapper.selectSysRoleById(vo.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return ConvertUtils.convertTo(updatedEntity, SysRoleVO::new);
    }

    /**
     * 更新角色数据权限信息
     *
     * @param vo 角色数据权限
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dataScope(SysRoleDataScopeVO vo) {
        SysRoleEntity entity = sysRoleMapper.selectSysRoleById(vo.getId());
        entity.setDataScope(vo.getDataScope());
        // 更新角色
        sysRoleMapper.updateSysRole(entity);

        // 更新角色数据权限关系
        if (vo.getDataScope().equals(DataScopeEnum.CUSTOM.getValue())) {
            sysRoleDataScopeService.saveOrUpdate(entity.getId(), vo.getDeptIdList());
        } else {
            sysRoleDataScopeService.deleteByRoleIdList(Collections.singletonList(vo.getId()));
        }

        // 更新角色对应用户的缓存权限
        sysUserTokenService.updateCacheAuthByRoleId(entity.getId());
    }

    /**
     * 删除角色信息
     *
     * @param idList 角色主键集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        // 删除角色
        batchUtils.executeBatch(SysRoleMapper.class, idList, SysRoleMapper::deleteSysRoleById);

        // 删除用户角色关系
        sysUserRoleService.deleteByRoleIdList(idList);

        // 删除角色菜单关系
        sysRoleMenuService.deleteByRoleIdList(idList);

        // 删除角色数据权限关系
        sysRoleDataScopeService.deleteByRoleIdList(idList);

        // 更新角色对应用户的缓存权限
        idList.forEach(sysUserTokenService::updateCacheAuthByRoleId);
    }

    /**
     * 根据角色主键查询角色信息
     *
     * @param id 角色主键
     * @return SysRoleVO
     */
    @Override
    public SysRoleVO getById(Long id) {
        SysRoleEntity entity = sysRoleMapper.selectSysRoleById(id);
        SysRoleVO role = ConvertUtils.convertTo(entity, SysRoleVO::new);
        // 查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.getMenuIdList(id);
        role.setMenuIdList(menuIdList);

        // 查询角色对应的数据权限
        List<Long> deptIdList = sysRoleDataScopeService.getDeptIdList(id);
        role.setDeptIdList(deptIdList);
        return role;
    }

    /**
     * 根据角色主键集合查询角色名称集合
     *
     * @param idList 角色主键集合
     * @return List<String>
     */
    @Override
    public List<String> getNameList(List<Long> idList) {
        if (idList.isEmpty()) {
            return null;
        }

        return sysRoleMapper.selectSysRoleNameByidList(idList);
    }

}