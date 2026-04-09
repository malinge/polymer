package com.polymer.system.mapper;

import com.polymer.system.entity.SysUserPostEntity;
import com.polymer.system.query.SysUserPostQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 用户岗位关系
 *
 * @author polymer
 */
@Mapper
public interface SysUserPostMapper {

    /**
     * 岗位ID列表
     *
     * @param userId 用户ID
     */
    List<Long> getPostIdList(Long userId);

    /**
     * 根据用户id集合和岗位id集合删除用户岗位关系信息
     *
     * @param userIdList 用户id集合
     * @param postIdList 岗位id集合
     * @return 结果
     */
    int deleteByUserIdAndPostId(@Param("userIdList") Collection<Long> userIdList, @Param("postIdList") Collection<Long> postIdList);

    /**
     * 查询用户岗位关系列表
     *
     * @param query 用户岗位关系查询
     * @return 用户岗位关系集合
     */
    List<SysUserPostEntity> selectSysUserPostList(SysUserPostQuery query);

    /**
     * 查询用户岗位关系
     *
     * @param id 用户岗位关系主键
     * @return 用户岗位关系
     */
    SysUserPostEntity selectSysUserPostById(Long id);

    /**
     * 新增用户岗位关系
     *
     * @param sysUserPost 用户岗位关系
     * @return 结果
     */
    int insertSysUserPost(SysUserPostEntity sysUserPost);

    /**
     * 修改用户岗位关系
     *
     * @param sysUserPost 用户岗位关系
     * @return 结果
     */
    int updateSysUserPost(SysUserPostEntity sysUserPost);

    /**
     * 删除用户岗位关系
     *
     * @param id 用户岗位关系主键
     * @return 结果
     */
    int deleteSysUserPostById(Long id);
}