package com.polymer.system.controller;

import com.polymer.framework.common.pojo.PageResult;
import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.logger.annotations.OperateLog;
import com.polymer.framework.logger.enums.OperateTypeEnum;
import com.polymer.framework.security.core.user.SecurityUser;
import com.polymer.framework.security.core.user.UserDetail;
import com.polymer.system.query.SysRoleQuery;
import com.polymer.system.query.SysRoleUserQuery;
import com.polymer.system.service.SysMenuService;
import com.polymer.system.service.SysRoleService;
import com.polymer.system.service.SysUserRoleService;
import com.polymer.system.service.SysUserService;
import com.polymer.system.vo.SysMenuVO;
import com.polymer.system.vo.SysRoleDataScopeVO;
import com.polymer.system.vo.SysRoleVO;
import com.polymer.system.vo.SysUserVO;
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
 * 角色管理
 *
 * @author polymer
 */
@RestController
@RequestMapping("sys/role")
@Tag(name = "角色管理")
public class SysRoleController {
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysUserRoleService sysUserRoleService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:role:page')")
    public Result<PageResult<SysRoleVO>> page(@ParameterObject @Valid SysRoleQuery query) {
        PageResult<SysRoleVO> page = sysRoleService.page(query);

        return Result.ok(page);
    }

    @GetMapping("list")
    @Operation(summary = "列表")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public Result<List<SysRoleVO>> list() {
        List<SysRoleVO> list = sysRoleService.getList(new SysRoleQuery());

        return Result.ok(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:role:info')")
    public Result<SysRoleVO> get(@PathVariable("id") Long id) {
        SysRoleVO role = sysRoleService.getById(id);
        return Result.ok(role);
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:role:save')")
    public Result<SysRoleVO> save(@RequestBody @Valid SysRoleVO vo) {
        SysRoleVO resVo = sysRoleService.save(vo);

        return Result.ok(resVo);
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result<SysRoleVO> update(@RequestBody @Valid SysRoleVO vo) {
        SysRoleVO resVo = sysRoleService.update(vo);

        return Result.ok(resVo);
    }

    @PutMapping("data-scope")
    @Operation(summary = "数据权限")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result<String> dataScope(@RequestBody @Valid SysRoleDataScopeVO vo) {
        sysRoleService.dataScope(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        sysRoleService.delete(idList);

        return Result.ok();
    }

    @GetMapping("menu")
    @Operation(summary = "角色菜单")
    @PreAuthorize("hasAuthority('sys:role:menu')")
    public Result<List<SysMenuVO>> menu() {
        UserDetail user = SecurityUser.getUser();
        List<SysMenuVO> list = sysMenuService.getUserMenuList(user, null);

        return Result.ok(list);
    }

    @GetMapping("user/page")
    @Operation(summary = "角色用户-分页")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result<PageResult<SysUserVO>> userPage(@Valid SysRoleUserQuery query) {
        PageResult<SysUserVO> page = sysUserService.roleUserPage(query);

        return Result.ok(page);
    }

    @DeleteMapping("user/{roleId}")
    @Operation(summary = "删除角色用户")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result<String> userDelete(@PathVariable("roleId") Long roleId, @RequestBody List<Long> userIdList) {
        sysUserRoleService.deleteByUserIdList(roleId, userIdList);

        return Result.ok();
    }

    @PostMapping("user/{roleId}")
    @Operation(summary = "分配角色给用户列表")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result<String> userSave(@PathVariable("roleId") Long roleId, @RequestBody List<Long> userIdList) {
        sysUserRoleService.saveUserList(roleId, userIdList);

        return Result.ok();
    }
}