package com.polymer.system.mapper;

import com.polymer.system.entity.SysDictTypeEntity;
import com.polymer.system.query.SysDictTypeQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 字典类型
 *
 * @author polymer
 */
@Mapper
public interface SysDictTypeMapper {

    /**
     * 查询字典类型列表
     *
     * @param query 字典类型查询
     * @return 字典类型集合
     */
    List<SysDictTypeEntity> selectSysDictTypeList(SysDictTypeQuery query);

    /**
     * 查询字典类型
     *
     * @param id 字典类型主键
     * @return 字典类型
     */
    SysDictTypeEntity selectSysDictTypeById(Long id);

    /**
     * 新增字典类型
     *
     * @param sysDictType 字典类型
     * @return 结果
     */
    int insertSysDictType(SysDictTypeEntity sysDictType);

    /**
     * 修改字典类型
     *
     * @param sysDictType 字典类型
     * @return 结果
     */
    int updateSysDictType(SysDictTypeEntity sysDictType);

    /**
     * 删除字典类型
     *
     * @param id 字典类型主键
     * @return 结果
     */
    int deleteSysDictTypeById(Long id);

}
