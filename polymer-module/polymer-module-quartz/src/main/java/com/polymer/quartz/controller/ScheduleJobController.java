package com.polymer.quartz.controller;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.logger.annotations.OperateLog;
import com.polymer.framework.logger.enums.OperateTypeEnum;
import com.polymer.quartz.query.ScheduleJobQuery;
import com.polymer.quartz.service.ScheduleJobService;
import com.polymer.quartz.vo.ScheduleJobVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 定时任务
 *
 * @author polymer
 */
@RestController
@RequestMapping("schedule")
@Tag(name = "定时任务")
public class ScheduleJobController {
    @Resource
    private ScheduleJobService scheduleJobService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('schedule:page')")
    public Result<PageResult<ScheduleJobVO>> page(@ParameterObject @Valid ScheduleJobQuery query) {
        PageResult<ScheduleJobVO> page = scheduleJobService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('schedule:info')")
    public Result<ScheduleJobVO> get(@PathVariable("id") Long id) {
        ScheduleJobVO vo = scheduleJobService.getById(id);

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('schedule:save')")
    public Result<ScheduleJobVO> save(@RequestBody ScheduleJobVO vo) {
        ScheduleJobVO resVo = scheduleJobService.save(vo);

        return Result.ok(resVo);
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('schedule:update')")
    public Result<ScheduleJobVO> update(@RequestBody @Valid ScheduleJobVO vo) {
        ScheduleJobVO resVo = scheduleJobService.update(vo);

        return Result.ok(resVo);
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('schedule:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        scheduleJobService.delete(idList);

        return Result.ok();
    }

    @PutMapping("run")
    @Operation(summary = "立即执行")
    @OperateLog(type = OperateTypeEnum.OTHER)
    @PreAuthorize("hasAuthority('schedule:run')")
    public Result<String> run(@RequestBody ScheduleJobVO vo) {
        scheduleJobService.run(vo);

        return Result.ok();
    }

    @PutMapping("change-status")
    @Operation(summary = "修改状态")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('schedule:update')")
    public Result<String> changeStatus(@RequestBody ScheduleJobVO vo) {
        scheduleJobService.changeStatus(vo);

        return Result.ok();
    }
}