package com.dada.controller.sys;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dada.entity.sys.SysRole;
import com.dada.entity.sys.SysRolePermission;
import com.dada.entity.sys.SysUserRole;
import com.dada.service.sys.SysRolePermissionService;
import com.dada.service.sys.SysRoleService;
import com.dada.service.sys.SysUserRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.result.Result;
import util.result.ResultGenerator;
import java.util.Date;
import java.util.List;

/**
 *  角色管理
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private SysRoleService  sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**列表可按名称查询*/
    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer limit,@RequestParam String name) {
        PageHelper.startPage(page, limit);
        List<SysRole> list = sysRoleService.findList(name);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 添加
     */
    @PostMapping("/add")
    public Result add(@RequestBody SysRole role) {
        SysRole sysRole = sysRoleService.getOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getCode, role.getCode()));
        if(sysRole != null){
            return ResultGenerator.genFailResult( "角色编码重复，添加失败！" );
        }
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        sysRoleService.save(role);
        return ResultGenerator.genSuccessResult();
    }

    /**
     *  删除
     *
     */
    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        sysRoleService.removeById(id);
        return ResultGenerator.genSuccessResult();

    }

    /**
     *  修改
     */
    @PostMapping("/update")
    public Result update(@RequestBody SysRole role) {
        role.setUpdateTime(new Date());
        sysRoleService.updateById(role);
        return ResultGenerator.genSuccessResult();
    }

    /**
     *   详情
     */
    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        SysRole role = sysRoleService.getById(id);
        return ResultGenerator.genSuccessResult(role);
    }


    /**
     *   通过角色id获取 角色用户关联信息
     */
    @PostMapping("/getRoleUserByRole")
    public Result getRoleUserByRole(@RequestParam String roleId) {
        List<SysUserRole> list = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>().eq(roleId != null, SysUserRole::getRoleId, roleId));
        return ResultGenerator.genSuccessResult(list);
    }


    /**
     *    通过角色id获取权限信息
     */
    @PostMapping("/getRolePermissionByRole")
    public Result getRolePermissionByRole(@RequestParam String roleId) {
        SysRolePermission rolePermission = new SysRolePermission();
        rolePermission.setRoleId(  roleId );
        List<SysRolePermission> list = sysRolePermissionService.list(new LambdaQueryWrapper<SysRolePermission>().eq(roleId!=null,SysRolePermission::getRoleId,roleId));
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     *   保存用户角色
     */
    @PostMapping("/saveRoleUser")
    public Result saveRoleUser(@RequestParam String roleId,@RequestParam String ids) {
        sysRoleService.saveRoleUser( roleId,ids );
        return ResultGenerator.genSuccessResult();
    }

    /**
     *  保存用户 权限(菜单  --  按钮)
     */
    @PostMapping("/saveRoleMenu")
    public Result saveRoleMenu(@RequestParam String roleId,@RequestParam String ids) {
        sysRoleService.saveRoleMenu( roleId,ids );
        return ResultGenerator.genSuccessResult();
    }

}
