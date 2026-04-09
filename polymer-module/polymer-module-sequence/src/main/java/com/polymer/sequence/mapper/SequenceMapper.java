package com.polymer.sequence.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* 发号器表Mapper接口
*
* @author polymer polymer@126.com
* @since 1.0.0 2026-03-27
*/
@Mapper
public interface SequenceMapper {
    Long selectCurrentValue(@Param("name") String name);

    void insertIfNotExists(@Param("name") String name, @Param("currentValue") long currentValue);

    int updateCurrentValue(@Param("name") String name, @Param("newValue") long newValue, @Param("oldValue") long oldValue);
}
