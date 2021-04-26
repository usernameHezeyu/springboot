/**
 * Copyright (C), 2015-2021, 开度
 * FileName: CustomAccessDeineHandler
 * Author:   ASUS
 * Date:     2021/4/20 14:15
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Hezeyu           2021/4/20           1.0
 */
package com.example.spring.security.spring.exception;

import com.alibaba.fastjson.JSONObject;
import com.example.spring.security.spring.utils.BaseResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 〈用来解决认证过的用户访问无权限资源时的异常〉
 *
 * @author ASUS
 * @create 2021/4/20
 * @since 1.0.0
 */
public class CustomAccessDeineHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("text/javascript;charset=utf-8");
        BaseResponse<Object> objectBaseResponse = new BaseResponse<>();
        objectBaseResponse.setFail(e.getMessage(),null,"001","2");
        httpServletResponse.getWriter().print(JSONObject.toJSONString(objectBaseResponse));
    }
}
