package com.polymer.system.service;

import com.polymer.system.query.SysCityQuery;
import com.polymer.system.vo.SysCityVO;

import java.util.List;

/**
 * 城市管理Service接口
 *
 * @author polymer zhangxf@126.com
 * @since 1.0.0 2025-07-18
 */
public interface SysCityService {

     /**
     * 查询城市管理列表
     *
     * @param query 查询条件
     * @return 城市管理分页集合
     */
     List<SysCityVO> selectSysCityList(SysCityQuery query);

    /**
     * 查询城市管理
     *
     * @param id 城市管理主键
     * @return 城市管理
     */
    SysCityVO selectSysCityById(Long id);

    /**
     * 新增城市管理
     *
     * @param vo 城市管理
     * @return 结果
     */
    SysCityVO insertSysCity(SysCityVO vo);

    /**
     * 批量新增城市管理
     *
     * @param list 城市管理集合
     * @return 结果
     */
    int batchInsertSysCity(List<SysCityVO> list);

    /**
     * 修改城市管理
     *
     * @param vo 城市管理
     * @return 结果
     */
    SysCityVO updateSysCity(SysCityVO vo);

    /**
     * 批量修改城市管理
     *
     * @param list 城市管理集合
     * @return 结果
     */
    int batchUpdateSysCity(List<SysCityVO> list);

    /**
     * 获得城市树
     *
     * @return 城市管理分页集合
     */
    List<SysCityVO> selectSysCityTree();
}