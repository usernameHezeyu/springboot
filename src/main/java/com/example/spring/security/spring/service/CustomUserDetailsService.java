/**
 * Copyright (C), 2015-2021, 开度
 * FileName: CustomUserDetailsService
 * Author:   ASUS
 * Date:     2021/4/19 16:49
 * Description: 自定义UserDetailsService
 * History:
 * <author>          <time>          <version>          <desc>
 * Hezeyu           2021/4/19           1.0              自定义UserDetailsService
 */
package com.example.spring.security.spring.service;

import com.example.spring.security.spring.mapper.UserInfoMapper;
import com.example.spring.security.spring.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 〈自定义UserDetailsService〉
 * 用于校验用户是否存在
 * @author ASUS
 * @create 2021/4/19
 * @since 1.0.0
 */
@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        // 1. 查询用户
        UserPojo userFromDatabase = userInfoMapper.findOneByLogin(login);
        if (userFromDatabase == null) {
            //log.warn("User: {} not found", login);
            throw new UsernameNotFoundException("User " + login + " was not found in db");
            //这里找不到必须抛异常
        }
        // 2. 设置角色
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userFromDatabase.getRole());
        grantedAuthorities.add(grantedAuthority);

        return new org.springframework.security.core.userdetails.User(login,
                userFromDatabase.getPassword(), grantedAuthorities);
    }
}
