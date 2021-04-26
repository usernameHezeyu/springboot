/**
 * Copyright (C), 2015-2021, 开度
 * FileName: CustomAuthenticationEntryPoint
 * Author:   ASUS
 * Date:     2021/4/20 14:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Hezeyu           2021/4/20           1.0
 */
package com.example.spring.security.spring.exception;

import com.alibaba.fastjson.JSONObject;
import com.example.spring.security.spring.utils.BaseResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 〈用来解决匿名用户访问无权限资源时的异常〉
 *
 * @author ASUS
 * @create 2021/4/20
 * @since 1.0.0
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/javascript;charset=utf-8");
        BaseResponse<Object> objectBaseResponse = new BaseResponse<>();
        objectBaseResponse.setFail("没有访问权限!"+authException.getMessage(),null,"001","2");
        response.getWriter().print(JSONObject.toJSONString(objectBaseResponse));
    }
}
