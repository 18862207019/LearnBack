package com.dada.controller.base;


import com.dada.entity.vo.UserInfoVO;
import com.dada.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @Autowired
    private UserService userService;

    /**
     * 获取当前登录用户信息
     */
    public UserInfoVO getCurrentUser() {
        return userService.getCurrentUser();
    }

}
