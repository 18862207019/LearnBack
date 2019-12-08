package com.dada.controller.sys;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    /**当前登陆用户信息*/
    @GetMapping("/userme")
    public Authentication principal() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
