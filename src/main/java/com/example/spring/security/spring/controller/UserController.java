/**
 * Copyright (C), 2015-2021, 开度
 * FileName: UserLoginController
 * Author:   ASUS
 * Date:     2021/4/14 10:59
 * Description: 测试用户登录
 * History:
 * <author>          <time>          <version>          <desc>
 * Hezeyu           2021/4/14           1.0              测试用户登录
 */
package com.example.spring.security.spring.controller;

import com.example.spring.security.spring.pojo.UserPojo;
import com.example.spring.security.spring.service.UserInfoServie;
import com.example.spring.security.spring.utils.BaseResponse;
import com.example.spring.security.spring.utils.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 〈测试用户登录〉
 *
 * @author ASUS
 * @create 2021/4/14
 * @since 1.0.0
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserInfoServie userInfoServie;

    @PostMapping("/register")
    public BaseResponse doRegister(@RequestBody UserPojo userDO){
       return userInfoServie.inset(userDO);
    }

    @RequestMapping("/main")
    public String toMain(Authentication authentication){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();  // 正确方式
        System.out.println("登录用户：" + name);
        return "html/login.html";
    }

    /**
     * 〈登录接口，根据账号密码返回token，并且保持登录信息到session〉
     * @param username
     * @param password
     * @return:java.lang.String
     * @since: 1.0.0
     * @Author:hezeyu
     * @Date: 2021/4/20 14:36
     */
    @RequestMapping(value = "/authenticate",method = RequestMethod.GET)
    public String authorize(@RequestParam String username, @RequestParam String password) {
        // 1 创建UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(username, password);
        // 2 认证
        Authentication authentication = authenticationManager.authenticate(token);
        // 3 保存认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 4 加载UserDetails
//        UserDetails details = this.userDetailsService.loadUserByUsername(username);
        // 5 生成自定义token
        return TokenProvider.token(username,password);
    }
}
