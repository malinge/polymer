package com.polymer.system.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.system.query.SysAppDetailsQuery;
import com.polymer.system.vo.SysAppDetailsVO;

import java.util.List;

/**
 * app信息表Service接口
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2025-05-09
 */
public interface SysAppDetailsService {

    /**
     * 查询app信息表分页列表
     *
     * @param query 查询条件
     * @return app信息表分页集合
     */
    PageResult<SysAppDetailsVO> page(SysAppDetailsQuery query);

    /**
     * 查询app信息表
     *
     * @param id app信息表主键
     * @return app信息表
     */
    SysAppDetailsVO selectSysAppDetailsById(Integer id);

    /**
     * 新增app信息表
     *
     * @param vo app信息表
     * @return 结果
     */
    SysAppDetailsVO insertSysAppDetails(SysAppDetailsVO vo);

    /**
     * 批量新增app信息表
     *
     * @param list app信息表集合
     * @return 结果
     */
    int batchInsertSysAppDetails(List<SysAppDetailsVO> list);

    /**
     * 修改app信息表
     *
     * @param vo app信息表
     * @return 结果
     */
    SysAppDetailsVO updateSysAppDetails(SysAppDetailsVO vo);

    /**
     * 批量修改app信息表
     *
     * @param list app信息表集合
     * @return 结果
     */
    int batchUpdateSysAppDetails(List<SysAppDetailsVO> list);

    /**
     * 重新加载app信息到缓存
     */
    void reloadSysAppDetails();

}