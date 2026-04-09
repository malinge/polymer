package com.polymer.system.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.system.query.SysRoleQuery;
import com.polymer.system.vo.SysRoleDataScopeVO;
import com.polymer.system.vo.SysRoleVO;

import java.util.List;

/**
 * 角色
 *
 * @author polymer
 */
public interface SysRoleService {

    /**
     * 根据角色查询获取分页角色列表
     *
     * @param query 角色查询
     * @return PageResult<SysRoleVO>
     */
    PageResult<SysRoleVO> page(SysRoleQuery query);

    /**
     * 根据角色查询获取角色集合
     *
     * @param query 角色查询
     * @return List<SysRoleVO>
     */
    List<SysRoleVO> getList(SysRoleQuery query);

    /**
     * 保存角色信息
     *
     * @param vo 角色信息
     */
    SysRoleVO save(SysRoleVO vo);

    /**
     * 更新角色信息
     *
     * @param vo 角色信息
     */
    SysRoleVO update(SysRoleVO vo);

    /**
     * 更新角色数据权限信息
     *
     * @param vo 角色数据权限
     */
    void dataScope(SysRoleDataScopeVO vo);

    /**
     * 删除角色信息
     *
     * @param idList 角色主键集合
     */
    void delete(List<Long> idList);

    /**
     * 根据角色主键查询角色信息
     *
     * @param id 角色主键
     * @return SysRoleVO
     */
    SysRoleVO getById(Long id);

    /**
     * 根据角色主键集合查询角色名称集合
     *
     * @param idList 角色主键集合
     * @return List<String>
     */
    List<String> getNameList(List<Long> idList);
}
