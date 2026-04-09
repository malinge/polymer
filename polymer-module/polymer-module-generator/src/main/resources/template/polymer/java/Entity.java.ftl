package ${package}.${moduleName}.entity;

<#list importList as i>
import ${i!};
</#list>
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
<#if baseClass??>
import ${baseClass.packageName}.${baseClass.code};
<#else>
import java.io.Serializable;
</#if>

/**
 * ${tableComment}对象 ${tableName}
 *
 * @author ${author} ${email}
 * @since ${version} ${date}
 */
public class ${ClassName}Entity<#if baseClass??> extends ${baseClass.code} <#else> implements Serializable </#if> {
    private static final long serialVersionUID = 1L;
<#list fieldList as field>
<#if !field.baseField>
	<#if field.fieldComment!?length gt 0>
    /**
    * ${field.fieldComment}
    */
    </#if>
	private ${field.attrType} ${field.attrName};
</#if>
</#list>

<#list fieldList as field>
    <#if !field.baseField>
    <#assign methodSuffix = field.attrName?substring(0,1)?upper_case  + field.attrName?substring(1)>
    public ${field.attrType} get${methodSuffix}() {
    return ${field.attrName};
    }
    public void set${methodSuffix}(${field.attrType} ${field.attrName}) {
        this.${field.attrName} = ${field.attrName};
    }
    </#if>
</#list>

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        <#list fieldList as field>
        <#if !field.baseField>
            <#assign methodSuffix = field.attrName?substring(0,1)?upper_case  + field.attrName?substring(1)>
            .append("${field.attrName}", get${methodSuffix}())
        </#if>
        </#list>
            .toString();
    }
}