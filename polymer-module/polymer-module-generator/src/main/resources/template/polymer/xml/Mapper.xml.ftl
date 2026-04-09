<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${package}.${moduleName}.mapper.${ClassName}Mapper">

    <resultMap type="${ClassName}Entity" id="${ClassName}Result">
        <#list fieldList as field>
        <result property="${field.attrName}" column="${field.fieldName}"/>
        </#list>
    </resultMap>

    <sql id="select${ClassName}Vo">
        select <#list fieldList as field>${field.fieldName}<#sep>,</#list>
        from ${tableName}
    </sql>

    <!--查询${tableComment}列表-->
    <select id="select${ClassName}List" parameterType="${package}.${moduleName}.query.${ClassName}Query" resultMap="${ClassName}Result">
        <include refid="select${ClassName}Vo"/>
        <where>
        <#list queryList as field>
            <#if field.queryType == '='>
            <if test="${field.attrName} != null <#if field.attrType == 'String'> and ${field.attrName} != ''</#if>"> and ${field.fieldName} = ${r"#{"}${field.attrName}${r"}"}</if>
            <#elseif field.queryType == '!='>
            <if test="${field.attrName} != null <#if field.attrType == 'String'> and ${field.attrName} != ''</#if>"> and ${field.fieldName} != ${r"#{"}${field.attrName}${r"}"}</if>
            <#elseif field.queryType == '>'>
            <if test="${field.attrName} != null <#if field.attrType == 'String'> and ${field.attrName} != ''</#if>"> and ${field.fieldName} &gt; ${r"#{"}${field.attrName}${r"}"}</if>
            <#elseif field.queryType == '>='>
            <if test="${field.attrName} != null <#if field.attrType == 'String'> and ${field.attrName} != ''</#if>"> and ${field.fieldName} &gt;= ${r"#{"}${field.attrName}${r"}"}</if>
            <#elseif field.queryType == '<'>
            <if test="${field.attrName} != null <#if field.attrType == 'String'> and ${field.attrName} != ''</#if>"> and ${field.fieldName} &lt; ${r"#{"}${field.attrName}${r"}"}</if>
            <#elseif field.queryType == '<='>
            <if test="${field.attrName} != null <#if field.attrType == 'String'> and ${field.attrName} != ''</#if>"> and ${field.fieldName} &lt;= ${r"#{"}${field.attrName}${r"}"}</if>
            <#elseif field.queryType == 'like'>
            <if test="${field.attrName} != null <#if field.attrType == 'String'> and ${field.attrName} != ''</#if>"> and ${field.fieldName} like concat('%', ${r"#{"}${field.attrName}${r"}"}, '%')</if>
            <#elseif field.queryType == 'between'>
            <if test="begin${field.attrName?cap_first} != null and end${field.attrName?cap_first} != null"> and ${field.fieldName} between ${r"#{"}begin${field.attrName?cap_first}${r"}"} and ${r"#{"}end${field.attrName?cap_first}${r"}"}</if>
            </#if>
        </#list>
        </where>
    </select>

    <!--查询${tableComment}-->
    <select id="select${ClassName}By${primary.attrName?cap_first}" parameterType="${primary.attrType}" resultMap="${ClassName}Result">
        <include refid="select${ClassName}Vo"/>
        where ${primary.fieldName} = ${r"#{"}${primary.attrName}${r"}"}
    </select>

    <!--新增${tableComment}-->
    <insert id="insert${ClassName}" parameterType="${ClassName}Entity" <#if primary.extra == 'auto_increment'>useGeneratedKeys="true" keyProperty="${primary.attrName}"</#if>>
        insert into ${tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list fieldList as field>
            <#if !field.primaryPk || field.extra != 'auto_increment'>
            <if test="${field.attrName} != null<#if field.attrType == 'String'> and ${field.attrName} != ''</#if>">${field.fieldName},</if>
            </#if>
        </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
        <#list fieldList as field>
            <#if !field.primaryPk || field.extra != 'auto_increment'>
            <if test="${field.attrName} != null<#if field.attrType == 'String'> and ${field.attrName} != ''</#if>">${r"#{"}${field.attrName}${r"}"},</if>
            </#if>
        </#list>
        </trim>
    </insert>

    <!--修改${tableComment}-->
    <update id="update${ClassName}" parameterType="${ClassName}Entity">
        update ${tableName}
        <set>
        <#list fieldList as field>
            <#if !field.primaryPk>
            <if test="${field.attrName} != null<#if field.attrType == 'String'> and ${field.attrName} != ''</#if>">${field.fieldName} = ${r"#{"}${field.attrName}${r"}"},</if>
            </#if>
        </#list>
        </set>
        where ${primary.fieldName} = ${r"#{"}${primary.attrName}${r"}"}
    </update>

    <!--删除${tableComment}-->
    <delete id="delete${ClassName}By${primary.attrName?cap_first}" parameterType="${primary.attrType}">
        delete from ${tableName} where ${primary.fieldName} = ${r"#{"}${primary.attrName}${r"}"}
    </delete>

</mapper>