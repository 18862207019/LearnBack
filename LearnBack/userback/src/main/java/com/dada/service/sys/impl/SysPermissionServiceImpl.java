package com.dada.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dada.entity.sys.SysPermission;
import com.dada.entity.sys.SysRolePermission;
import com.dada.mapper.SysPermissionMapper;
import com.dada.service.sys.SysPermissionService;
import com.dada.service.sys.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService{


    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRolePermissionService rolePermissionService;

    @Override
    public List<SysPermission> findList(SysPermission sysPermission){ return sysPermissionMapper.findList(sysPermission); }


    @Override
    public List <SysPermission> listTreePermission() {
        return sysPermissionMapper.listTreePermission();
    }

    @Override
    public void deleteRelation(String sysPermissionId) throws Exception {
        /*解除资源关联的角色*/
        List<SysRolePermission> rolePermissionList =  rolePermissionService.list(new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getPermissionId,sysPermissionId));
        if(rolePermissionList != null && rolePermissionList.size() > 0){
            rolePermissionList.forEach( temp ->{
                rolePermissionService.removeById( temp.getId() );
            } );
        }
        this.removeById( Integer.valueOf( sysPermissionId ) );
    }

}
