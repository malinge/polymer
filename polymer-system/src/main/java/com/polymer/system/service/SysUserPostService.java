package com.polymer.system.service;

import java.util.List;

/**
 * 用户岗位关系
 *
 * @author polymer
 */
public interface SysUserPostService {

    /**
     * 保存或修改
     *
     * @param userId     用户ID
     * @param postIdList 岗位ID列表
     */
    void saveOrUpdate(Long userId, List<Long> postIdList);

    /**
     * 根据岗位id列表，删除用户岗位关系
     *
     * @param postIdList 岗位id列表
     */
    void deleteByPostIdList(List<Long> postIdList);

    /**
     * 根据用户id列表，删除用户岗位关系
     *
     * @param userIdList 用户id列表
     */
    void deleteByUserIdList(List<Long> userIdList);

    /**
     * 岗位ID列表
     *
     * @param userId 用户ID
     * @return List<Long>
     */
    List<Long> getPostIdList(Long userId);
}