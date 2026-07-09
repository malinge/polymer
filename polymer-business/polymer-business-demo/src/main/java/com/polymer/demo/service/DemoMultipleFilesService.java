package com.polymer.demo.service;

import com.polymer.api.system.vo.ImportResultVO;
import com.polymer.demo.query.DemoMultipleFilesQuery;
import com.polymer.demo.vo.DemoMultipleFilesVO;
import com.polymer.framework.common.pojo.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 多文件上传样例Service接口
 *
 * @author polymer polymer@126.com
 * @since 1.0.0 2026-06-23
 */
public interface DemoMultipleFilesService {

     /**
     * 查询多文件上传样例分页列表
     *
     * @param query 查询条件
     * @return 多文件上传样例分页集合
     */
    PageResult<DemoMultipleFilesVO> page(DemoMultipleFilesQuery query);

    /**
     * 查询多文件上传样例
     *
     * @param id 多文件上传样例主键
     * @return 多文件上传样例
     */
    DemoMultipleFilesVO selectDemoMultipleFilesById(Long id);

    /**
     * 新增多文件上传样例
     *
     * @param vo 多文件上传样例
     * @return 结果
     */
    DemoMultipleFilesVO insertDemoMultipleFiles(DemoMultipleFilesVO vo);

    /**
     * 批量新增多文件上传样例
     *
     * @param list 多文件上传样例集合
     * @return 结果
     */
    int batchInsertDemoMultipleFiles(List<DemoMultipleFilesVO> list);

    /**
     * 修改多文件上传样例
     *
     * @param vo 多文件上传样例
     * @return 结果
     */
     DemoMultipleFilesVO updateDemoMultipleFiles(DemoMultipleFilesVO vo);

    /**
     * 批量修改多文件上传样例
     *
     * @param list 多文件上传样例集合
     * @return 结果
     */
    int batchUpdateDemoMultipleFiles(List<DemoMultipleFilesVO> list);

    /**
     * 删除多文件上传样例
     *
     * @param id 多文件上传样例主键
     * @return 结果
     */
    int deleteDemoMultipleFilesById(Long id);

    /**
     * 批量删除多文件上传样例
     *
     * @param idList 需要删除的数据主键集合
     * @return 结果
     */
    int deleteDemoMultipleFilesByIdList(List<Long> idList);

    ImportResultVO importByExcel(MultipartFile file, String strategy) throws Exception;

    byte[] export(DemoMultipleFilesQuery query);
}
