package com.polymer.system.mapper;

import com.polymer.framework.mybatis.core.annotation.DataScope;
import com.polymer.system.entity.SysDeptEntity;
import com.polymer.system.query.SysDeptQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门管理
 *
 * @author polymer
 */
@Mapper
public interface SysDeptMapper {

    /**
     * 查询部门管理集合
     *
     * @param param 部门管理查询
     * @return 部门管理
     */
    @DataScope(alias = "t1", dataId = "id")
    List<SysDeptEntity> getSysDeptList(SysDeptQuery param);

    /**
     * 获取所有部门的id、pid列表
     */
    List<SysDeptEntity> getIdAndPidList();

    /**
     * 查询部门管理
     *
     * @param id 部门管理主键
     * @return 部门管理
     */
    SysDeptEntity selectSysDeptById(Long id);

    /**
     * 新增部门管理
     *
     * @param sysOrg 部门管理
     * @return 结果
     */
    int insertSysDept(SysDeptEntity sysOrg);

    /**
     * 修改部门管理
     *
     * @param sysOrg 部门管理
     * @return 结果
     */
    int updateSysDept(SysDeptEntity sysOrg);

    /**
     * 删除部门管理
     *
     * @param id 部门管理主键
     * @return 结果
     */
    int deleteSysDeptById(Long id);

    /**
     * 查询部门子集个数
     *
     * @param id 父级id
     * @return 结果
     */
    int selectCountByPid(Long id);

    /**
     * 根据部门路径查询子部门ID
     *
     * @param path 部门路径
     * @return  List<Long> 结果
     */
    List<Long> selectSubDeptIdsByPath(@Param("path") String path);

    /**
     * 查询用户担任负责人的部门ID
     *
     * @param userId 用户ID
     * @return List<Long>
     */
    List<Long> selectDeptIdsByLeaderId(@Param("userId") Long userId);
}