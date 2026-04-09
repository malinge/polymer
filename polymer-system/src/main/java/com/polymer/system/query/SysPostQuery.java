package com.polymer.system.query;

import com.polymer.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 岗位查询
 *
 * @author polymer
 */
@Schema(description = "岗位管理查询")
public class SysPostQuery extends PageParam {
    private List<Long> idList;

    @Schema(description = "岗位编码")
    private String postCode;

    @Schema(description = "岗位名称")
    private String postName;

    @Schema(description = "状态  0：停用   1：正常")
    private Integer status;

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
