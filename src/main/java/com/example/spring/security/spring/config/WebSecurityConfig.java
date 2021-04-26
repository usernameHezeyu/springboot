/**
 * Copyright (C), 2015-2021, 开度
 * FileName: WebSecurityConfig
 * Author:   ASUS
 * Date:     2021/4/14 10:29
 * Description: 测试
 * History:
 * <author>          <time>          <version>          <desc>
 * Hezeyu           2021/4/14           1.0              测试
 */
package com.example.spring.security.spring.config;

import com.example.spring.security.spring.service.CustomUserDetailsService;
import com.example.spring.security.spring.configurer.MyAuthTokenConfigurer;
import com.example.spring.security.spring.exception.CustomAccessDeineHandler;
import com.example.spring.security.spring.exception.CustomAuthenticationEntryPoint;
import com.example.spring.security.spring.exception.LogoutSuccess;
import com.example.spring.security.spring.service.UserInfoServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 〈测试〉
 *
 * @author ASUS
 * @create 2021/4/14
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
////会拦截注解了@PreAuthrize注解的配置.
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    UserInfoServie userInfoServie;

    private static String REALM="MY_TEST_REALM";

    /**
     * 配置路径权限、白名单、认证方式、session等操作
     * @Author:hezeyu
     * @Date: 2021/4/20 17:38
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/product/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/*").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .successForwardUrl("/user/main")
            .and()
                .logout()   //退出登录相关配置
                .logoutUrl("/user/signOut")   //自定义退出登录页面
                .logoutSuccessHandler(new LogoutSuccess()) //退出成功后要做的操作（如记录日志），和logoutSuccessUrl互斥

            .and()
                .httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().apply(securityConfigurerAdapter());

        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeineHandler());
        http.csrf().disable();
    }

    /**
     * 认证用户信息、权限
     * @since: 1.0.0
     * @Author:hezeyu
     * @Date: 2021/4/20 17:35
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)// 设置自定义的userDetailsService
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 声明过滤器，上面http注入
     * @since: 1.0.0
     * @Author:hezeyu
     * @Date: 2021/4/20 17:33
     */
    private MyAuthTokenConfigurer securityConfigurerAdapter() {
        return new MyAuthTokenConfigurer(userDetailsService);
    }


    /**
     * 注册密码校验方式
     * @since: 1.0.0
     * @Author:hezeyu
     * @Date: 2021/4/20 17:33
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器
     * @since: 1.0.0
     * @Author:hezeyu
     * @Date: 2021/4/20 17:33
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * 声明拦截器
     * @since: 1.0.0
     * @Author:hezeyu
     * @Date: 2021/4/20 17:33
     */
    @Bean
    public CustomAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }

}
