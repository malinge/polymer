package com.polymer.system.controller;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.logger.annotations.OperateLog;
import com.polymer.framework.logger.enums.OperateTypeEnum;
import com.polymer.system.query.SysDictTypeQuery;
import com.polymer.system.service.SysDictTypeService;
import com.polymer.system.vo.SysDictSimpleVO;
import com.polymer.system.vo.SysDictTypeVO;
import com.polymer.system.vo.SysDictVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
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
 * 字典类型
 *
 * @author polymer
 */
@RestController
@RequestMapping("sys/dict/type")
@Tag(name = "字典类型")
public class SysDictTypeController {
    @Resource
    private SysDictTypeService sysDictTypeService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:dict:page')")
    public Result<PageResult<SysDictTypeVO>> page(@ParameterObject @Valid SysDictTypeQuery query) {
        PageResult<SysDictTypeVO> page = sysDictTypeService.page(query);

        return Result.ok(page);
    }

    @GetMapping("list/sql")
    @Operation(summary = "动态SQL数据")
    @PreAuthorize("hasAuthority('sys:dict:page')")
    public Result<PageResult<SysDictVO.DictData>> listSql(Long id) {
        List<SysDictVO.DictData> list = sysDictTypeService.getDictSql(id);

        PageResult<SysDictVO.DictData> page = new PageResult<>(list, list.size());

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:dict:info')")
    public Result<SysDictTypeVO> gvoet(@PathVariable("id") Long id) {
        SysDictTypeVO vo = sysDictTypeService.getById(id);

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:dict:save')")
    public Result<SysDictTypeVO> save(@RequestBody @Valid SysDictTypeVO vo) {
        SysDictTypeVO resVo = sysDictTypeService.save(vo);

        return Result.ok(resVo);
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:dict:update')")
    public Result<SysDictTypeVO> update(@RequestBody @Valid SysDictTypeVO vo) {
        SysDictTypeVO resVo = sysDictTypeService.update(vo);

        return Result.ok(resVo);
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:dict:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        sysDictTypeService.delete(idList);

        return Result.ok();
    }

    @GetMapping("all")
    @Operation(summary = "全部字典数据")
    public Result<List<SysDictVO>> all() {
        List<SysDictVO> dictList = sysDictTypeService.getDictList();

        return Result.ok(dictList);
    }

    @GetMapping("refreshTransCache")
    @Operation(summary = "刷新字典翻译缓存数据")
    @PreAuthorize("hasAuthority('sys:dict:refreshTransCache')")
    public Result<String> refreshTransCache() {
        sysDictTypeService.refreshTransCache();
        return Result.ok();
    }

    @GetMapping("list-all-simple")
    @Operation(summary = "全部字典数据")
    public Result<List<SysDictSimpleVO>> getSimpleDictTypeList() {
        List<SysDictSimpleVO> dictList = sysDictTypeService.getSimpleDictTypeList();

        return Result.ok(dictList);
    }


}