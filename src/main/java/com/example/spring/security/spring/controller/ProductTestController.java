/**
 * Copyright (C), 2015-2021, 开度
 * FileName: ProductTestController
 * Author:   ASUS
 * Date:     2021/4/14 13:59
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Hezeyu           2021/4/14           1.0
 */
package com.example.spring.security.spring.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/product")
public class ProductTestController {

    @RequestMapping("/info")
    @ResponseBody
    public String productInfo(){
        String currentUser = "";
        Object principl = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principl instanceof UserDetails) {
            currentUser = ((UserDetails)principl).getUsername();
        }else {
            currentUser = principl.toString();
        }
        return " some product info,currentUser is: "+currentUser;
    }
}
