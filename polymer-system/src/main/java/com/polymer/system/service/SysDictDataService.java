package com.polymer.system.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.system.query.SysDictDataQuery;
import com.polymer.system.vo.SysDictDataVO;

import java.util.List;

/**
 * 数据字典
 *
 * @author polymer
 */
public interface SysDictDataService {

    /**
     * 根据字典数据查询获取分页字典数据集合
     *
     * @param query 字典数据查询
     * @return PageResult<SysDictDataVO>
     */
    PageResult<SysDictDataVO> page(SysDictDataQuery query);

    /**
     * 保存字典数据
     *
     * @param vo 字典数据
     */
    SysDictDataVO save(SysDictDataVO vo);

    /**
     * 更新字典数据
     *
     * @param vo 字典数据
     */
    SysDictDataVO update(SysDictDataVO vo);

    /**
     * 根据字典数据主键集合删除字典数据
     *
     * @param idList 字典数据主键集合
     */
    void delete(List<Long> idList);

    /**
     * 根据字典数据主键查询字典数据
     *
     * @param id 字典数据主键
     */
    SysDictDataVO getById(Long id);

}