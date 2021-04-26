/**
 * Copyright (C), 2015-2021, 开度
 * FileName: TestEndpoints
 * Author:   ASUS
 * Date:     2021/4/13 17:30
 * Description: 外部访问层
 * History:
 * <author>          <time>          <version>          <desc>
 * Hezeyu           2021/4/13           1.0              外部访问层
 */
package com.example.spring.security.spring.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈外部访问层〉
 *
 * @author ASUS
 * @create 2021/4/13
 * @since 1.0.0
 */
@RestController
public class TestEndpoints {

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id){
        //for debug
        Authentication authenticator= SecurityContextHolder.getContext().getAuthentication();
        return "product id : " + id;
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        //for debug
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "order id : " + id;
    }

    @GetMapping("/helloUser")
    @PreAuthorize("@permission.hasPermi('monitor:online:list')")
    public String helloUser() {
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
