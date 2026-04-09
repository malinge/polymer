package com.polymer.quartz.controller;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.quartz.query.ScheduleJobLogQuery;
import com.polymer.quartz.service.ScheduleJobLogService;
import com.polymer.quartz.vo.ScheduleJobLogVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 定时任务日志
 *
 * @author polymer
 */
@RestController
@RequestMapping("schedule/log")
@Tag(name = "定时任务日志")
public class ScheduleJobLogController {
    @Resource
    private ScheduleJobLogService scheduleJobLogService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('schedule:log')")
    public Result<PageResult<ScheduleJobLogVO>> page(@ParameterObject @Valid ScheduleJobLogQuery query) {
        PageResult<ScheduleJobLogVO> page = scheduleJobLogService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('schedule:log')")
    public Result<ScheduleJobLogVO> get(@PathVariable("id") Long id) {
        ScheduleJobLogVO vo = scheduleJobLogService.getById(id);

        return Result.ok(vo);
    }

}