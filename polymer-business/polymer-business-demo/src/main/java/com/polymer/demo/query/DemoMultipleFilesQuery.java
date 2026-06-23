package com.polymer.demo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import com.polymer.framework.common.pojo.PageParam;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
* 多文件上传样例查询
*
* @author polymer polymer@126.com
* @since 1.0.0 2026-06-23
*/
@Schema(description = "多文件上传样例查询")
public class DemoMultipleFilesQuery extends PageParam {
    private static final long serialVersionUID = 1L;



    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        .toString();
    }
}
