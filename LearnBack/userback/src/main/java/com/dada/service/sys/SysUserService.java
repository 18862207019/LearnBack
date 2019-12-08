package com.dada.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dada.entity.sys.SysUser;
import com.dada.entity.vo.UserInfoVO;

public interface SysUserService extends IService<SysUser>{

    /**
     * 根据名称获取用户详细信息
     */
    UserInfoVO getUserInfo(String username);


    void deleteRelation(String userId) throws Exception;
}
