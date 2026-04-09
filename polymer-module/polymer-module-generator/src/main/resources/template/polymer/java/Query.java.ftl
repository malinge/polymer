package ${package}.${moduleName}.query;

import io.swagger.v3.oas.annotations.media.Schema;
import ${package}.framework.common.pojo.PageParam;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

<#list importQueryList as i>
import ${i!};
</#list>

/**
* ${tableComment}查询
*
* @author ${author} ${email}
* @since ${version} ${date}
*/
@Schema(description = "${tableComment}查询")
public class ${ClassName}Query extends PageParam {
    private static final long serialVersionUID = 1L;

<#list queryList as field>
    <#if field.queryType != 'between'>
    <#if field.fieldComment!?length gt 0>
    @Schema(description = "${field.fieldComment}")
    </#if>
    private ${field.attrType} ${field.attrName};

    <#else>
    <#-- 范围查询字段拆分为两个 -->
    <#if field.fieldComment!?length gt 0>
    @Schema(description = "开始${field.fieldComment}")
    </#if>
    private ${field.attrType} begin${field.attrName?cap_first};

    <#if field.fieldComment!?length gt 0>
    @Schema(description = "结束${field.fieldComment}")
    </#if>
    private ${field.attrType} end${field.attrName?cap_first};
    </#if>
</#list>

<#list queryList as field>
    <#if field.queryType != 'between'>
    <#-- 普通字段的 getter/setter -->
    <#assign methodSuffix = field.attrName?substring(0,1)?upper_case  + field.attrName?substring(1)>
    public ${field.attrType} get${methodSuffix}() {
        return ${field.attrName};
    }
    public void set${methodSuffix}(${field.attrType} ${field.attrName}) {
        this.${field.attrName} = ${field.attrName};
    }
    <#else>
    <#-- 范围查询字段的 getter/setter -->
    <#assign capName = field.attrName?substring(0,1)?upper_case  + field.attrName?substring(1)>
    public ${field.attrType} getBegin${capName}() {
        return begin${capName};
    }
    public void setBegin${capName}(${field.attrType} begin${capName}) {
        this.begin${capName} = begin${capName};
    }

    public ${field.attrType} getEnd${capName}() {
        return end${capName};
    }
    public void setEnd${capName}(${field.attrType} end${capName}) {
        this.end${capName} = end${capName};
    }
    </#if>
</#list>

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        <#list queryList as field>
            <#if field.queryType != 'between'>
                .append("${field.attrName}", get${field.attrName?substring(0,1)?upper_case}${field.attrName?substring(1)}())
            <#else>
                .append("begin${field.attrName?cap_first}", getBegin${field.attrName?cap_first}())
                .append("end${field.attrName?cap_first}", getEnd${field.attrName?cap_first}())
            </#if>
        </#list>
        .toString();
    }
}