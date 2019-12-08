package com.dada.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dada.entity.sys.SysRole;

import java.util.List;

public interface SysRoleService extends IService<SysRole>{

    List<SysRole> findList(String  name);

    List<SysRole> findListByUserId(String username);

    void saveRoleUser(String roleId, String ids);

    void saveRoleMenu(String roleId, String ids);

//    void saveRoleWxAccount(String roleId, String ids);
}
