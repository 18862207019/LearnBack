package com.dada.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dada.entity.sys.SysRole;
import com.dada.entity.sys.SysRolePermission;
import com.dada.entity.sys.SysUserRole;
import com.dada.mapper.SysRoleMapper;
import com.dada.mapper.SysRolePermissionMapper;
import com.dada.mapper.SysUserRoleMapper;
import com.dada.service.sys.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysRolePermissionMapper rolePermissionMapper;


    @Override
    public List<SysRole> findList(String name) {
        return sysRoleMapper.findList(name);
    }

    @Override
    public List<SysRole> findListByUserId(String userId) {
        return null;
    }

    @Override
    public void saveRoleUser(String roleId, String ids) {
        userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId,roleId));
        if (StringUtils.isNotBlank(ids)) {
            String[] idArrays = ids.split(",");
            if (idArrays.length > 0) {
                for (String temp : idArrays) {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setRoleId(roleId);
                    userRole.setUserId(temp);
                    userRole.setCreateTime(new Date());
                    userRole.setUpdateTime(new Date());
                    userRoleMapper.insert(userRole);
                }
            }
        }
    }

    @Override
    public void saveRoleMenu(String roleId, String ids) {
        rolePermissionMapper.delete(new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getRoleId,roleId));
        if (StringUtils.isNotBlank(ids)) {
            String[] idArrays = ids.split(",");
            if (idArrays.length > 0) {
                for (String temp : idArrays) {
                    SysRolePermission rolePermission = new SysRolePermission();
                    rolePermission.setRoleId(roleId);
                    rolePermission.setPermissionId(temp);
                    rolePermission.setCreateTime(new Date());
                    rolePermission.setUpdateTime(new Date());
                    rolePermissionMapper.insert(rolePermission);
                }
            }
        }
    }


}
