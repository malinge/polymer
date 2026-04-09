package com.polymer.quartz.query;

import com.polymer.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 定时任务日志查询
 *
 * @author polymer
 */
@Schema(description = "定时任务日志查询")
public class ScheduleJobLogQuery extends PageParam {
    @Schema(description = "任务id")
    private Long jobId;

    @Schema(description = "任务名称")
    private String jobName;

    @Schema(description = "任务组名")
    private String jobGroup;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }
}