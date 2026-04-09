package com.polymer.system.service;


import com.polymer.system.vo.SysDeptVO;

import java.util.List;

/**
 * 部门管理
 *
 * @author polymer
 */
public interface SysDeptService {

    /**
     * 获取部门树结构数据
     *
     * @return List<SysOrgVO>
     */
    List<SysDeptVO> getSysDeptList();

    /**
     * 保存部门信息
     *
     * @param vo 部门信息
     */
    SysDeptVO save(SysDeptVO vo);

    /**
     * 更新部门信息
     *
     * @param vo 部门信息
     */
    SysDeptVO update(SysDeptVO vo);

    /**
     * 删除部门信息
     *
     * @param id 部门信息主键
     */
    void delete(Long id);

    /**
     * 根据部门ID，获取部门及其所有子部门ID
     *
     * @param id 部门ID
     * @return List<Long>
     */
    List<Long> getDeptTreeIds(Long id);

    //查询用户担任负责人的部门

    /**
     * 查询用户担任负责人的部门ID
     *
     * @param userId 用户ID
     * @return List<Long>
     */
    List<Long> getDeptIdsByLeaderId(Long userId);

    /**
     * 根据部门信息主键查询部门信息
     *
     * @param id 部门信息主键
     * @return SysOrgVO
     */
    SysDeptVO getById(Long id);

    /**
     * 校验部门是否有数据权限
     *
     * @param deptId 部门id
     */
    void checkDeptDataScope(Long deptId);

    /**
     * 全部部门数据
     * @return SysDeptVO
     */
    List<SysDeptVO> simpleList();
}