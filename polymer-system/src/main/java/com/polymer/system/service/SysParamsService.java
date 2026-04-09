package com.polymer.system.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.system.query.SysParamsQuery;
import com.polymer.system.vo.SysParamsVO;

import java.util.List;

/**
 * 参数管理
 *
 * @author polymer
 */
public interface SysParamsService {

    /**
     * 根据参数管理查询获取分页参数列表
     *
     * @param query 参数管理查询
     * @return PageResult<SysParamsVO>
     */
    PageResult<SysParamsVO> page(SysParamsQuery query);

    /**
     * 保存参数信息
     *
     * @param vo 参数信息
     */
    SysParamsVO save(SysParamsVO vo);

    /**
     * 更新参数信息
     *
     * @param vo 参数信息
     */
    SysParamsVO update(SysParamsVO vo);

    /**
     * 根据参数信息主键集合删除参数信息
     *
     * @param idList 参数信息主键集合
     */
    void delete(List<Long> idList);

    /**
     * 根据paramKey，获取字符串值
     *
     * @param paramKey 参数Key
     * @return String
     */
    SysParamsVO getParamsByparamKey(String paramKey);

    /**
     * 根据参数主键查询参数信息
     *
     * @param id 参数主键
     * @return SysParamsVO
     */
    SysParamsVO getById(Long id);
}