package com.dada.feign.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dada.entity.sys.SysUser;
import com.dada.entity.vo.UserInfoVO;
import com.dada.service.sys.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.result.Result;

/**
 *  远程调用用户信息
 */
@RequestMapping("/rpc/user")
@RestController
public class User {
    @Autowired
    private SysUserService sysUserService;


    /**
     * 功能描述:  通过用户名获取当前用户 springSecrity内部会自动判断密码是否一致 密码加密判断
     */
    @PostMapping("/getByName")
    public Result<SysUser> getByName(@RequestParam("userName") String userName){
        SysUser sysUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, userName));
        return new Result<SysUser>().setData(sysUser);
    }


    /**
     * 功能描述:   获取当前用户所拥有的权限
     */
    @PostMapping("/getUserInfo")
    public Result<UserInfoVO> getUserInfo(@RequestParam("userName") String userName){
        return new Result<UserInfoVO>().setData(sysUserService.getUserInfo(userName));
    }
}
