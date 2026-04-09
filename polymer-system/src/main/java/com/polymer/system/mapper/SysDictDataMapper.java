package com.polymer.system.mapper;

import com.polymer.system.entity.SysDictDataEntity;
import com.polymer.system.query.SysDictDataQuery;
import com.polymer.system.vo.SysDictVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 字典数据
 *
 * @author polymer
 */
@Mapper
public interface SysDictDataMapper {

    @Select("${sql}")
    List<SysDictVO.DictData> getListForSql(@Param("sql") String sql);

    /**
     * 查询字典数据列表
     *
     * @param query 字典数据查询
     * @return 字典数据集合
     */
    List<SysDictDataEntity> selectSysDictDataList(SysDictDataQuery query);

    /**
     * 查询字典数据
     *
     * @param id 字典数据主键
     * @return 字典数据
     */
    SysDictDataEntity selectSysDictDataById(Long id);

    /**
     * 新增字典数据
     *
     * @param sysDictData 字典数据
     * @return 结果
     */
    int insertSysDictData(SysDictDataEntity sysDictData);

    /**
     * 修改字典数据
     *
     * @param sysDictData 字典数据
     * @return 结果
     */
    int updateSysDictData(SysDictDataEntity sysDictData);

    /**
     * 删除字典数据
     *
     * @param id 字典数据主键
     * @return 结果
     */
    int deleteSysDictDataById(Long id);

}
