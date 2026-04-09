package com.polymer.system.controller;

import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.logger.annotations.OperateLog;
import com.polymer.framework.logger.enums.OperateTypeEnum;
import com.polymer.system.service.SysDeptService;
import com.polymer.system.vo.SysDeptVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 部门管理
 *
 * @author polymer
 */
@RestController
@RequestMapping("sys/dept")
@Tag(name = "部门管理")
public class SysDeptController {
    @Resource
    private SysDeptService sysDeptService;

    @GetMapping("list")
    @Operation(summary = "列表")
    @PreAuthorize("hasAuthority('sys:dept:list')")
    public Result<List<SysDeptVO>> list() {
        List<SysDeptVO> list = sysDeptService.getSysDeptList();

        return Result.ok(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:dept:info')")
    public Result<SysDeptVO> get(@PathVariable("id") Long id) {
        SysDeptVO vo = sysDeptService.getById(id);

        // 获取上级部门名称
        if (vo.getPid() != null) {
            SysDeptVO parentVO = sysDeptService.getById(vo.getPid());
            vo.setParentName(parentVO.getName());
        }

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:dept:save')")
    public Result<SysDeptVO> save(@RequestBody @Valid SysDeptVO vo) {
        SysDeptVO resVo = sysDeptService.save(vo);

        return Result.ok(resVo);
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:dept:update')")
    public Result<SysDeptVO> update(@RequestBody @Valid SysDeptVO vo) {
        SysDeptVO resVo = sysDeptService.update(vo);

        return Result.ok(resVo);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:dept:delete')")
    public Result<String> delete(@PathVariable("id") Long id) {
        sysDeptService.delete(id);

        return Result.ok();
    }

    @GetMapping("simple-list")
    @Operation(summary = "列表")
    public Result<List<SysDeptVO>> simpleList() {
        List<SysDeptVO> list = sysDeptService.simpleList();

        return Result.ok(list);
    }

}