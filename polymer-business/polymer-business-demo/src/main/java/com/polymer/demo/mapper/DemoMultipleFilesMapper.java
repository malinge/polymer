package com.polymer.demo.mapper;

import java.util.List;
import com.polymer.demo.entity.DemoMultipleFilesEntity;
import com.polymer.demo.query.DemoMultipleFilesQuery;
import org.apache.ibatis.annotations.Mapper;

/**
* 多文件上传样例Mapper接口
*
* @author polymer polymer@126.com
* @since 1.0.0 2026-06-23
*/
@Mapper
public interface DemoMultipleFilesMapper {
    
    /**
     * 查询多文件上传样例列表
     * 
     * @param query 多文件上传样例查询
     * @return 多文件上传样例集合
     */
    List<DemoMultipleFilesEntity> selectDemoMultipleFilesList(DemoMultipleFilesQuery query);

    /**
     * 查询多文件上传样例
     *
     * @param id 多文件上传样例主键
     * @return 多文件上传样例
     */
    DemoMultipleFilesEntity selectDemoMultipleFilesById(Long id);

    /**
     * 新增多文件上传样例
     * 
     * @param demoMultipleFiles 多文件上传样例
     * @return 结果
     */
    int insertDemoMultipleFiles(DemoMultipleFilesEntity demoMultipleFiles);

    /**
     * 修改多文件上传样例
     * 
     * @param demoMultipleFiles 多文件上传样例
     * @return 结果
     */
    int updateDemoMultipleFiles(DemoMultipleFilesEntity demoMultipleFiles);

    /**
     * 删除多文件上传样例
     * 
     * @param id 多文件上传样例主键
     * @return 结果
     */
    int deleteDemoMultipleFilesById(Long id);

}
