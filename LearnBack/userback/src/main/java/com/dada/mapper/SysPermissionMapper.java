package com.dada.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dada.entity.sys.SysPermission;

import java.util.List;

public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    List<SysPermission> findList(SysPermission permission);

    List<SysPermission> findListByRoleId(String roleId);

    List<SysPermission> listTreePermission();

}