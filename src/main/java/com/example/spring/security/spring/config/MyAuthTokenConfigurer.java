/**
 * Copyright (C), 2015-2021, 开度
 * FileName: MyAuthTokenConfigurer
 * Author:   ASUS
 * Date:     2021/4/20 9:56
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Hezeyu           2021/4/20           1.0
 */
package com.example.spring.security.spring.config;

import com.example.spring.security.spring.service.CustomUserDetailsService;
import com.example.spring.security.spring.filter.MyTokenFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 〈过滤器配置，注册过滤器〉
 *
 * @author ASUS
 * @create 2021/4/20
 * @since 1.0.0
 */
public class MyAuthTokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    CustomUserDetailsService userDetailsService;

    public MyAuthTokenConfigurer(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        MyTokenFilter customFilter = new MyTokenFilter(userDetailsService);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
