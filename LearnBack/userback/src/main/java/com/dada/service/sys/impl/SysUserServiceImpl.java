package com.dada.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dada.entity.sys.tree.TreeBuilder;
import com.dada.entity.sys.SysPermission;
import com.dada.entity.sys.SysRole;
import com.dada.entity.sys.SysUser;
import com.dada.entity.sys.SysUserRole;
import com.dada.entity.vo.ButtonVO;
import com.dada.entity.vo.MenuVO;
import com.dada.entity.vo.UserInfoVO;
import com.dada.mapper.SysPermissionMapper;
import com.dada.mapper.SysRoleMapper;
import com.dada.mapper.SysUserMapper;
import com.dada.service.sys.SysUserRoleService;
import com.dada.service.sys.SysUserService;
import com.google.common.collect.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPermissionMapper permissionMapper;

    @Autowired
    private SysUserRoleService sysUserRoleService;


    /**
     * 根据名称获取用户详细信息
     */
    @Override
    public UserInfoVO getUserInfo(String username) {
        SysUser sysUser = this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(sysUser, userInfoVO);

        /*角色*/
        Set<String> roles = Sets.newHashSet();
        /*菜单*/
        Set<MenuVO> menuVOS = Sets.newHashSet();
        /*按钮(接口)*/
        Set<ButtonVO> buttonVOS = Sets.newHashSet();
        /*查询某个用户的角色*/
        List<SysRole> roleList = roleMapper.findListByUserId(String.valueOf(sysUser.getId()));
        if (!roleList.isEmpty()) {
            roleList.forEach(temp -> {
                roles.add(temp.getCode());
                //查询某个角色的资源
                List<SysPermission> permissionList = permissionMapper.findListByRoleId(String.valueOf(temp.getId()));
                if (permissionList != null && permissionList.size() > 0) {
                    permissionList.forEach(permission -> {
                        if (permission.getType().toLowerCase().equals("button")) {
                            //如果权限是按钮，就添加到按钮里面
                            ButtonVO buttonVO = new ButtonVO();
                            BeanUtils.copyProperties(permission, buttonVO);
                            buttonVOS.add(buttonVO);
                        }
                        if (permission.getType().toLowerCase().equals("menu")) {
                            //如果权限是菜单，就添加到菜单里面
                            MenuVO menuVO = new MenuVO();
                            BeanUtils.copyProperties(permission, menuVO);
                            menuVOS.add(menuVO);
                        }
                    });
                }
            });
        }
        /*封装数据*/
        userInfoVO.getRoles().addAll(roles);
        userInfoVO.getButtons().addAll(buttonVOS);
        userInfoVO.getMenus().addAll(TreeBuilder.buildTree(menuVOS));
        return userInfoVO;
    }

    @Override
    public void deleteRelation(String userId) throws Exception {
        /*解除与角色的关联*/
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(userId);
        List<SysUserRole> userRoleList = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
        if (userRoleList != null && userRoleList.size() > 0) {
            userRoleList.forEach(temp -> {
                sysUserRoleService.removeById(temp.getId());
            });
        }
        this.removeById(Integer.valueOf(userId));
    }

}
