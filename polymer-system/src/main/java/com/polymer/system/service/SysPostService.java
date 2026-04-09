package com.polymer.system.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.system.query.SysPostQuery;
import com.polymer.system.vo.SysPostVO;

import java.util.List;

/**
 * 岗位管理
 *
 * @author polymer
 */
public interface SysPostService {

    /**
     * 根据岗位查询获取分页岗位列表
     *
     * @param query 岗位查询
     * @return PageResult<SysPostVO>
     */
    PageResult<SysPostVO> page(SysPostQuery query);

    /**
     * 查询全部岗位信息集合
     *
     * @return List<SysPostVO>
     */
    List<SysPostVO> getList();

    /**
     * 根据岗位主键集合查询岗位名称集合
     *
     * @param idList 岗位主键集合
     * @return List<String>
     */
    List<String> getNameList(List<Long> idList);

    /**
     * 保存岗位信息
     *
     * @param vo 岗位信息
     * @return 保存后的岗位信息（包含自动生成的id）
     */
    SysPostVO save(SysPostVO vo);

    /**
     * 更新岗位信息
     *
     * @param vo 岗位信息
     * @return 更新后的岗位信息（最新数据）
     */
    SysPostVO update(SysPostVO vo);

    /**
     * 根据岗位主键集合删除岗位信息
     *
     * @param idList 岗位主键集合
     */
    void delete(List<Long> idList);

    /**
     * 根据岗位主键查询岗位信息
     *
     * @param id 岗位主键
     * @return SysPostVO
     */
    SysPostVO getById(Long id);
}