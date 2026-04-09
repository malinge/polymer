package com.polymer.system.query;

import com.polymer.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * app信息表查询
 *
 * @author polymer admin@126.com
 * @since 1.0.0 2025-05-09
 */
@Schema(description = "app信息表查询")
public class SysAppDetailsQuery extends PageParam {
    private static final long serialVersionUID = 1L;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .toString();
    }
}