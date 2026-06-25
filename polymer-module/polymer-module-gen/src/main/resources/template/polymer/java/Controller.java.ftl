package ${package}.${moduleName}.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ${package}.framework.common.pojo.PageResult;
import ${package}.framework.common.pojo.Result;
import ${package}.${moduleName}.service.${ClassName}Service;
import ${package}.${moduleName}.query.${ClassName}Query;
import ${package}.${moduleName}.vo.${ClassName}VO;
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
* ${tableComment}Controller
*
* @author ${author} ${email}
* @since ${version} ${date}
*/
@RestController
@RequestMapping("${moduleName}/${functionName}")
@Tag(name="${tableComment}")
public class ${ClassName}Controller {
    @Resource
    private ${ClassName}Service ${className}Service;

    @GetMapping("/page")
    @Operation(summary = "查询${tableComment}分页")
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:page')")
    public Result<PageResult<${ClassName}VO>> page(@ParameterObject @Valid ${ClassName}Query query){
        PageResult<${ClassName}VO> page = ${className}Service.page(query);

        return Result.ok(page);
    }

    @GetMapping("/{${primary.attrName}}")
    @Operation(summary = "获取${tableComment}详细信息")
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:info')")
    public Result<${ClassName}VO> getInfo(@PathVariable("${primary.attrName}") ${primary.attrType} ${primary.attrName}){
        ${ClassName}VO vo = ${className}Service.select${ClassName}By${primary.attrName?cap_first}(${primary.attrName});

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "新增${tableComment}")
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:save')")
    public Result<${ClassName}VO> save(@RequestBody ${ClassName}VO vo){
        ${ClassName}VO resVo = ${className}Service.insert${ClassName}(vo);

        return Result.ok(resVo);
    }

    @PutMapping
    @Operation(summary = "修改${tableComment}")
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:update')")
    public Result<${ClassName}VO> update(@RequestBody @Valid ${ClassName}VO vo){
        ${ClassName}VO resVo = ${className}Service.update${ClassName}(vo);

        return Result.ok(resVo);
    }

    @DeleteMapping
    @Operation(summary = "删除${tableComment}")
    @PreAuthorize("hasAuthority('${moduleName}:${functionName}:delete')")
    public Result<Integer> delete(@RequestBody List<${primary.attrType}> ${primary.attrName}List){
        int num = ${className}Service.delete${ClassName}By${primary.attrName?cap_first}List(${primary.attrName}List);

        return Result.ok(num);
    }
}