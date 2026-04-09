package com.polymer.system.query;

import com.polymer.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
* 城市管理查询
*
* @author polymer zhangxf@126.com
* @since 1.0.0 2025-07-18
*/
@Schema(description = "城市管理查询")
public class SysCityQuery extends PageParam {
    private static final long serialVersionUID = 1L;

    @Schema(description = "父级等级（1：省级；城市；3：区县）")
    private Integer level;

    @Schema(description = "城市名称")
    private String name;

    @Schema(description = "父级id")
    private Long pid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .toString();
    }
}