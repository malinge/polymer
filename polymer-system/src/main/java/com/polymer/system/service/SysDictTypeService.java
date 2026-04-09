package com.polymer.system.service;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.system.query.SysDictTypeQuery;
import com.polymer.system.vo.SysDictSimpleVO;
import com.polymer.system.vo.SysDictTypeVO;
import com.polymer.system.vo.SysDictVO;

import java.util.List;

/**
 * 数据字典
 *
 * @author polymer
 */
public interface SysDictTypeService {

    /**
     * 根据字典类型查询获取字典类型分页数据
     *
     * @param query 字典类型查询
     * @return PageResult<SysDictTypeVO>
     */
    PageResult<SysDictTypeVO> page(SysDictTypeQuery query);

    /**
     * 根据字典类型主键查询字典类型
     *
     * @param id 字典类型主键
     * @return SysDictTypeVO
     */
    SysDictTypeVO getById(Long id);

    /**
     * 保存字典类型
     *
     * @param vo 字典类型
     */
    SysDictTypeVO save(SysDictTypeVO vo);

    /**
     * 更新字典类型
     *
     * @param vo 字典类型
     */
    SysDictTypeVO update(SysDictTypeVO vo);

    /**
     * 根据字典类型主键集合删除字典类型
     *
     * @param idList 字典类型主键集合
     */
    void delete(List<Long> idList);

    /**
     * 获取动态SQL数据
     *
     * @param id 字典类型主键
     * @return List<SysDictVO.DictData>
     */
    List<SysDictVO.DictData> getDictSql(Long id);

    /**
     * 获取全部字典列表
     *
     * @return List<SysDictVO>
     */
    List<SysDictVO> getDictList();

    /**
     * 刷新字典缓存
     */
    void refreshTransCache();

    /**
     * 获得全部字典类型列表
     *
     * @return 字典类型列表
     */
    List<SysDictSimpleVO> getSimpleDictTypeList();
}