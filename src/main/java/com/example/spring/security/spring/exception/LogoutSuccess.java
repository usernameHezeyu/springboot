/**
 * Copyright (C), 2015-2021, 开度
 * FileName: LogoutSuccess
 * Author:   ASUS
 * Date:     2021/4/25 9:54
 * Description: 退出登录拦截器
 * History:
 * <author>          <time>          <version>          <desc>
 * Hezeyu           2021/4/25           1.0              退出登录拦截器
 */
package com.example.spring.security.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 〈退出登录拦截器〉
 *
 * @author ASUS
 * @create 2021/4/25
 * @since 1.0.0
 */
public class LogoutSuccess implements LogoutSuccessHandler {


    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write("退出成功，请重新登录");

    }
}
