package com.dada.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dada.entity.sys.SysPermission;

import java.util.List;

public interface SysPermissionService extends IService<SysPermission>{

    List<SysPermission> findList(SysPermission sysPermission);

    List<SysPermission> listTreePermission();

    void deleteRelation(String sysPermissionId) throws Exception;

}
