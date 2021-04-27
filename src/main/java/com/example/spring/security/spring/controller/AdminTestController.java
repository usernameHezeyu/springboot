/**
 * Copyright (C), 2015-2021, 开度
 * FileName: AdminTestController
 * Author:   ASUS
 * Date:     2021/4/14 13:59
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Hezeyu           2021/4/14           1.0
 */
package com.example.spring.security.spring.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 〈〉
 *
 * @author ASUS
 * @create 2021/4/14
 * @since 1.0.0
 */
@Controller
@RequestMapping("/admin")
public class AdminTestController {

    @RequestMapping("/home")
    @ResponseBody
    public String productInfo(){
        return " admin home page ";

    }

    @GetMapping("/helloUser")
    @PreAuthorize("hasAnyRole('normal','USER')")
    public String helloUser() {
        return "hello,user";
    }
}