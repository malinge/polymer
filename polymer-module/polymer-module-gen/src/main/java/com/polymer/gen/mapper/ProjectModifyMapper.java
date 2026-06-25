package com.polymer.gen.mapper;

import com.polymer.gen.common.query.Query;
import com.polymer.gen.entity.ProjectModifyEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* 项目名变更Mapper接口
*
* @author polymer polymer@126.com
* @since 1.0.0 2025-10-21
*/
@Mapper
public interface ProjectModifyMapper {
    
    /**
     * 查询项目名变更列表
     * 
     * @param query 项目名变更查询
     * @return 项目名变更集合
     */
    List<ProjectModifyEntity> selectProjectModifyList(Query query);

    /**
     * 查询项目名变更
     *
     * @param id 项目名变更主键
     * @return 项目名变更
     */
    ProjectModifyEntity selectProjectModifyById(Long id);

    /**
     * 新增项目名变更
     * 
     * @param projectModify 项目名变更
     * @return 结果
     */
    int insertProjectModify(ProjectModifyEntity projectModify);

    /**
     * 修改项目名变更
     * 
     * @param projectModify 项目名变更
     * @return 结果
     */
    int updateProjectModify(ProjectModifyEntity projectModify);

    /**
     * 删除项目名变更
     * 
     * @param id 项目名变更主键
     * @return 结果
     */
    int deleteProjectModifyById(Long id);

}