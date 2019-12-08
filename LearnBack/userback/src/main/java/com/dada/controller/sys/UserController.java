package com.dada.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dada.controller.base.BaseController;
import com.dada.entity.sys.SysUser;
import com.dada.entity.vo.UserInfoVO;
import com.dada.entity.sys.tree.UserTreeNode;
import com.dada.feign.rpc.OauthRpc;
import com.dada.service.sys.SysUserService;
import com.dada.service.redis.RedisService;
import com.dada.util.EhcacheUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import constant.EhcacheContant;
import constant.RedisContant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.web.bind.annotation.*;
import util.http.IPUtil;
import util.result.Result;
import util.result.ResultGenerator;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/user")
@RestController
public class UserController extends BaseController {

    @Autowired
    private OauthRpc oauthRpc;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisService redisService;

    /**
     * 获取当前用户 tips Authentication是一个接口，通过该接口可以获得用户相关信息、安全实体的标识以及认证请求的上下文信息等
     */
    @PreAuthorize("hasRole('SystemUserUpdate')")
    @GetMapping("/userMe")
    public Result userMe() {
        return new Result().setData(SecurityContextHolder.getContext().getAuthentication());
    }

    /**
     * 登陆
     */
    @PostMapping("/login")
    public Result login(String username, String password) {
        log.info("UserController=>login() 【Start】");
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, "system");
        parameters.put("client_secret", "secret");
        parameters.put(OAuth2Utils.SCOPE, "system");
        parameters.put("username", username);
        parameters.put("password", password);
        String ipAdd = IPUtil.getIpAdd();

        log.info("UserController=>login() 【用户:{} 开始登陆 时间:{} {}",username,new Date(),"】");
        /*获取Oauth返回的Token信息*/
        return new Result().setData(oauthRpc.postAccessToken(parameters));
    }

    /**
     * 退出登陆
     */
    @PostMapping("/sys/logout")
    public Result logout(String access_token) {
        log.info("UserController=>logout() 【Start】");
        /*获取当前用户名称*/
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        /*redis删除当前用户信息*/
        redisService.delete(RedisContant.USER_KEY+userName);
        EhcacheUtil.remove(EhcacheContant.DEFAULT_CACHE_NAME,EhcacheContant.USER_KEY+userName);
        /*Spring解除当前用户登录状态*/
        oauthRpc.removeToken(access_token);
        log.info("UserController=>logout() 【end】");
        return ResultGenerator.genSuccessResult( );
    }

    /**
     * 功能描述: 通过用户名获取当前用户 springSecrity内部会自动判断密码是否一致 密码加密判断
     */
    @PostMapping("/getByName")
    public Result<SysUser> getByName(@RequestParam(value = "userName") String userName) {
        SysUser sysUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, userName));
        return new Result<SysUser>().setData(sysUser);
    }

    /**
     * 功能描述: 获取当前用户所拥有的权限
     */
    @PostMapping("/getUserInfo")
    public Result<UserInfoVO> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new Result<UserInfoVO>().setData(sysUserService.getUserInfo(authentication.getName()));
    }

    /**
     * 用户列表按照名称查询
     */
    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer limit, @RequestParam String username) {
        PageHelper.startPage(page, limit);
        List<SysUser> list = sysUserService.list(new LambdaQueryWrapper<SysUser>().eq(StringUtils.isNotBlank(username), SysUser::getUsername, username));
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    /**
     * 角色权限管理  关联用户
     */
    @PostMapping("/listTreeUser")
    public Result listTreeUser() {
        List<SysUser> list = sysUserService.list();
        List<UserTreeNode> userTreeNodeList = Lists.newArrayList();
        list.forEach(temp -> {
            UserTreeNode userTreeNode = new UserTreeNode();
            BeanUtils.copyProperties(temp, userTreeNode);
            userTreeNodeList.add(userTreeNode);
        });
        JSONObject json = new JSONObject();
        json.put("userList", list);
        json.put("userTree", userTreeNodeList);
        return ResultGenerator.genSuccessResult(json);
    }

    /**
     * 添加用户信息
     */
    @PostMapping("/add")
    public Result add(@RequestBody SysUser user) {
        SysUser userTemp = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, user.getUsername()));
        if (userTemp != null && StringUtils.isNotBlank(userTemp.getId() + "")) {

            return ResultGenerator.genFailResult("登陆名重复，添加失败！");
        }
        /*加密存储*/
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        sysUserService.save(user);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 修改用户信息
     */
    @PostMapping("/update")
    public Result update(@RequestBody SysUser user) {
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        /*编辑的时候，密码为空，不做更新*/
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        }
        user.setUpdateTime(new Date());
        sysUserService.updateById(user);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 删除用户信息
     */
    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) throws Exception{
        sysUserService.deleteRelation(String.valueOf( id ));
        return ResultGenerator.genSuccessResult();
    }

}
