package com.polymer.system.service.impl;

import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.common.utils.ConvertUtils;
import com.polymer.framework.common.utils.StringUtils;
import com.polymer.framework.common.utils.TreeUtils;
import com.polymer.framework.security.core.user.UserDetail;
import com.polymer.system.entity.SysMenuEntity;
import com.polymer.system.enums.SuperAdminEnum;
import com.polymer.system.mapper.SysMenuMapper;
import com.polymer.system.service.SysMenuService;
import com.polymer.system.service.SysRoleMenuService;
import com.polymer.system.vo.SysMenuVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 菜单管理
 *
 * @author polymer
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Resource
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 保存菜单
     *
     * @param vo 菜单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysMenuVO save(SysMenuVO vo) {
        // 1. VO 转 Entity
        SysMenuEntity entity = ConvertUtils.convertTo(vo, SysMenuEntity::new);
        // 2. 插入数据（MyBatis 会自动回填 entity 的 id）
        sysMenuMapper.insertSysMenu(entity);
        // 3. Entity 转 VO 并返回（此时 VO 中已包含生成的 id）
        return ConvertUtils.convertTo(entity, SysMenuVO::new);
    }

    /**
     * 更新菜单
     *
     * @param vo 菜单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysMenuVO update(SysMenuVO vo) {

        // 校验 id 是否存在（业务层可增加存在性检查，这里以简洁为主）
        if (vo.getId() == null) {
            throw new ServiceException("更新时ID不能为空！");
        }

        // 上级菜单不能为自己
        if (vo.getId().equals(vo.getPid())) {
            throw new ServiceException("上级菜单不能为自己");
        }

        // 上级部门不能为下级
        List<Long> subMenuList = getSubMenuIdList(vo.getId());
        if (subMenuList.contains(vo.getPid())) {
            throw new ServiceException("上级菜单不能为下级");
        }
        // 更新菜单
        SysMenuEntity entity = ConvertUtils.convertTo(vo, SysMenuEntity::new);
        int rows = sysMenuMapper.updateSysMenu(entity);

        if (rows == 0) {
            throw new ServiceException("更新失败，可能数据不存在或未做任何修改");
        }

        // 重新查询最新数据（确保返回字段包含数据库默认值，如 update_time）
        SysMenuEntity updatedEntity = sysMenuMapper.selectSysMenuById(vo.getId());
        if (updatedEntity == null) {
            throw new ServiceException("更新后查询数据失败");
        }
        return ConvertUtils.convertTo(updatedEntity, SysMenuVO::new);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单信息主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 删除菜单
        sysMenuMapper.deleteSysMenuById(id);

        // 删除角色菜单关系
        sysRoleMenuService.deleteByMenuId(id);
    }

    /**
     * 菜单列表
     *
     * @param type 菜单类型
     */
    @Override
    public List<SysMenuVO> getMenuList(Integer type) {
        List<SysMenuEntity> menuList = sysMenuMapper.getMenuList(type);
        List<SysMenuVO> sysMenuVOS = ConvertUtils.convertListTo(menuList, SysMenuVO::new);
        return TreeUtils.build(sysMenuVOS);
    }

    /**
     * 用户菜单列表
     *
     * @param user 用户
     * @param type 菜单类型
     * @return List<SysMenuVO>
     */
    @Override
    public List<SysMenuVO> getUserMenuList(UserDetail user, Integer type) {
        List<SysMenuEntity> menuList;

        // 系统管理员，拥有最高权限
        if (user.getSuperAdmin().equals(SuperAdminEnum.YES.getValue())) {
            menuList = sysMenuMapper.getMenuList(type);
        } else {
            menuList = sysMenuMapper.getUserMenuList(user.getId(), type);
        }
        List<SysMenuVO> sysMenuVOS = ConvertUtils.convertListTo(menuList, SysMenuVO::new);
        return TreeUtils.build(sysMenuVOS);
    }

    /**
     * 获取子菜单的数量
     *
     * @param pid 父菜单ID
     * @return Long
     */
    @Override
    public int getSubMenuCount(Long pid) {
        return sysMenuMapper.getSubMenuCount(pid);
    }

    /**
     * 获取用户权限列表
     *
     * @param user 登录用户信息
     * @return Set<String>
     */
    @Override
    public Set<String> getUserAuthority(UserDetail user) {
        // 系统管理员，拥有最高权限
        List<String> authorityList;
        if (user.getSuperAdmin().equals(SuperAdminEnum.YES.getValue())) {
            authorityList = sysMenuMapper.getAuthorityList();
        } else {
            authorityList = sysMenuMapper.getUserAuthorityList(user.getId());
        }

        // 用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String authority : authorityList) {
            if (StringUtils.isBlank(authority)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(authority.trim().split(",")));
        }

        return permsSet;
    }

    /**
     * 根据菜单ID，获取子菜单ID列表(包含本菜单ID)
     *
     * @param id 菜单ID
     */
    @Override
    public List<Long> getSubMenuIdList(Long id) {
        // 所有菜单的id、pid列表
        List<SysMenuEntity> menuList = sysMenuMapper.getIdAndPidList();

        // 递归查询所有子菜单ID列表
        List<Long> subIdList = new ArrayList<>();
        getTree(id, menuList, subIdList);

        // 本菜单也添加进去
        subIdList.add(id);

        return subIdList;
    }

    /**
     * 根据菜单主键获取菜单信息
     *
     * @param id 菜单ID
     * @return SysMenuVO
     */
    @Override
    public SysMenuVO getById(Long id) {
        SysMenuEntity entity = sysMenuMapper.selectSysMenuById(id);

        return ConvertUtils.convertTo(entity, SysMenuVO::new);
    }

    /**
     * 递归组装树结构数据
     *
     * @param id        菜单主键
     * @param menuList  菜单集合
     * @param subIdList 父级id集合
     */
    private void getTree(Long id, List<SysMenuEntity> menuList, List<Long> subIdList) {
        for (SysMenuEntity menu : menuList) {
            if (Objects.equals(menu.getPid(), id)) {
                getTree(menu.getId(), menuList, subIdList);
                subIdList.add(menu.getId());
            }
        }
    }

}