package com.polymer.system.controller;

import com.polymer.framework.common.pojo.Result;
import com.polymer.framework.logger.annotations.OperateLog;
import com.polymer.framework.logger.enums.OperateTypeEnum;
import com.polymer.framework.security.core.user.SecurityUser;
import com.polymer.framework.security.core.user.UserDetail;
import com.polymer.system.enums.MenuTypeEnum;
import com.polymer.system.service.SysMenuService;
import com.polymer.system.vo.SysMenuVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.Set;

/**
 * 菜单管理
 *
 * @author polymer
 */
@RestController
@RequestMapping("sys/menu")
@Tag(name = "菜单管理")
public class SysMenuController {
    @Resource
    private SysMenuService sysMenuService;

    @GetMapping("nav")
    @Operation(summary = "菜单导航")
    public Result<List<SysMenuVO>> nav() {
        UserDetail user = SecurityUser.getUser();
        List<SysMenuVO> list = sysMenuService.getUserMenuList(user, MenuTypeEnum.MENU.getValue());

        return Result.ok(list);
    }

    @GetMapping("authority")
    @Operation(summary = "用户权限标识")
    public Result<Set<String>> authority() {
        UserDetail user = SecurityUser.getUser();
        Set<String> set = sysMenuService.getUserAuthority(user);

        return Result.ok(set);
    }

    @GetMapping("list")
    @Operation(summary = "菜单列表")
    @Parameter(name = "type", description = "菜单类型 0：菜单 1：按钮  2：接口  null：全部")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result<List<SysMenuVO>> list(Integer type) {
        List<SysMenuVO> list = sysMenuService.getMenuList(type);

        return Result.ok(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:menu:info')")
    public Result<SysMenuVO> get(@PathVariable("id") Long id) {
        SysMenuVO vo = sysMenuService.getById(id);

        // 获取上级菜单名称
        if (vo.getPid() != null) {
            SysMenuVO parentVO = sysMenuService.getById(vo.getPid());
            vo.setParentName(parentVO.getName());
        }

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public Result<SysMenuVO> save(@RequestBody @Valid SysMenuVO vo) {
        SysMenuVO resVo = sysMenuService.save(vo);

        return Result.ok(resVo);
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public Result<SysMenuVO> update(@RequestBody @Valid SysMenuVO vo) {
        SysMenuVO resVo = sysMenuService.update(vo);

        return Result.ok(resVo);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public Result<String> delete(@PathVariable("id") Long id) {
        // 判断是否有子菜单或按钮
        int count = sysMenuService.getSubMenuCount(id);
        if (count > 0) {
            return Result.error("请先删除子菜单");
        }

        sysMenuService.delete(id);

        return Result.ok();
    }
}