package com.dada.feign.user;

import com.dada.entity.sys.SysUser;
import com.dada.entity.vo.UserInfoVO;
import util.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *  用户中心远程调用（调用如下）
 *  1，userback（模块）->com.gomore.feign.User(类)
 */
@FeignClient("userback")
public interface UserRpc {

    /**
     * 功能描述: 通过名称返回当前用户信息
     */
    @PostMapping("/rpc/user/getByName")
    public Result<SysUser> getByName(@RequestParam("userName") String userName);

    /**
     * 功能描述: 获取用户详细信息d
     */
    @PostMapping("/rpc/user/getUserInfo")
    public Result<UserInfoVO> getUserInfo(@RequestParam("userName") String userName);
}
