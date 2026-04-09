package com.polymer.system.service.impl;

import com.polymer.framework.common.exception.ServiceException;
import com.polymer.framework.mybatis.core.utils.MyBatisBatchUtils;
import com.polymer.system.entity.SysUserPostEntity;
import com.polymer.system.mapper.SysUserPostMapper;
import com.polymer.system.service.SysUserPostService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户岗位关系
 *
 * @author polymer
 */
@Service
public class SysUserPostServiceImpl implements SysUserPostService {
    @Resource
    private SysUserPostMapper sysUserPostMapper;
    @Resource
    private MyBatisBatchUtils batchUtils;

    /**
     * 保存或修改
     *
     * @param userId     用户ID
     * @param postIdList 岗位ID列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long userId, List<Long> postIdList) {
        // 数据库岗位ID列表
        List<Long> dbPostIdList = getPostIdList(userId);

        // 需要新增的岗位ID
        Collection<Long> insertPostIdList = CollectionUtils.subtract(postIdList, dbPostIdList);
        if (CollectionUtils.isNotEmpty(insertPostIdList)) {
            List<SysUserPostEntity> postList = insertPostIdList.stream().map(postId -> {
                SysUserPostEntity entity = new SysUserPostEntity();
                entity.setUserId(userId);
                entity.setPostId(postId);
                return entity;
            }).collect(Collectors.toList());

            // 批量新增
            batchUtils.executeBatch(SysUserPostMapper.class, postList, SysUserPostMapper::insertSysUserPost);
        }

        // 需要删除的岗位ID
        Collection<Long> deletePostIdList = CollectionUtils.subtract(dbPostIdList, postIdList);
        if (CollectionUtils.isNotEmpty(deletePostIdList)) {
            List<Long> userIdList = new ArrayList<>();
            userIdList.add(userId);
            sysUserPostMapper.deleteByUserIdAndPostId(userIdList, deletePostIdList);
        }
    }

    /**
     * 根据岗位id列表，删除用户岗位关系
     *
     * @param postIdList 岗位id列表
     */
    @Override
    public void deleteByPostIdList(List<Long> postIdList) {
        boolean isPostIdValid = postIdList != null && !postIdList.isEmpty();
        if (!isPostIdValid) {
            throw new ServiceException("岗位ID集合不能为空！");
        }
        sysUserPostMapper.deleteByUserIdAndPostId(null, postIdList);
    }

    /**
     * 根据用户id列表，删除用户岗位关系
     *
     * @param userIdList 用户id列表
     */
    @Override
    public void deleteByUserIdList(List<Long> userIdList) {
        boolean isUserIdValid = userIdList != null && !userIdList.isEmpty();
        if (!isUserIdValid) {
            throw new IllegalArgumentException("用户ID集合不能为空！");
        }
        sysUserPostMapper.deleteByUserIdAndPostId(userIdList, null);
    }

    /**
     * 岗位ID列表
     *
     * @param userId 用户ID
     * @return List<Long>
     */
    @Override
    public List<Long> getPostIdList(Long userId) {
        return sysUserPostMapper.getPostIdList(userId);
    }
}