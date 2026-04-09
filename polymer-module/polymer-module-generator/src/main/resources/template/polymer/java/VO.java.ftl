package ${package}.${moduleName}.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.io.Serializable;
<#list importList as i>
import ${i!};
</#list>

/**
* ${tableComment}
*
* @author ${author} ${email}
* @since ${version} ${date}
*/
@Schema(description = "${tableComment}")
public class ${ClassName}VO implements Serializable {
	private static final long serialVersionUID = 1L;

<#list fieldList as field>
	<#if field.fieldComment!?length gt 0>
	@Schema(description = "${field.fieldComment}")
	</#if>
	private ${field.attrType} ${field.attrName};

</#list>

<#list fieldList as field>
	<#assign methodSuffix = field.attrName?substring(0,1)?upper_case  + field.attrName?substring(1)>
	public ${field.attrType} get${methodSuffix}() {
		return ${field.attrName};
	}
	public void set${methodSuffix}(${field.attrType} ${field.attrName}) {
		this.${field.attrName} = ${field.attrName};
	}
</#list>

	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
		<#list fieldList as field>
			<#assign methodSuffix = field.attrName?substring(0,1)?upper_case  + field.attrName?substring(1)>
			.append("${field.attrName}", get${methodSuffix}())
		</#list>
			.toString();
	}
}