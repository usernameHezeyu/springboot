/**
 * Copyright (C), 2015-2021, 开度
 * FileName: UserInfoServie
 * Author:   ASUS
 * Date:     2021/4/19 17:56
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Hezeyu           2021/4/19           1.0
 */
package com.example.spring.security.spring.service;

import com.example.spring.security.spring.mapper.UserInfoMapper;
import com.example.spring.security.spring.pojo.UserPojo;
import com.example.spring.security.spring.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 〈〉
 *
 * @author ASUS
 * @create 2021/4/19
 * @since 1.0.0
 */
@Service
public class UserInfoServie {

    @Autowired
    UserInfoMapper userInfoMapper;

    private final static Map<Integer, String> ENCODER_TYPE = new HashMap<>();

    private final static Map<String, PasswordEncoder> ENCODER_MAP = new HashMap<>();

    private final static String PASSWORD_FORMAT = "{%s}%s";

    static {
        ENCODER_TYPE.put(0, "noop");
        ENCODER_TYPE.put(1, "bcrypt");
        ENCODER_TYPE.put(2, "pbkdf2");
        ENCODER_TYPE.put(3, "scrypt");
        ENCODER_TYPE.put(4, "sha256");
        ENCODER_MAP.put("noop", NoOpPasswordEncoder.getInstance());
        ENCODER_MAP.put("bcrypt", new BCryptPasswordEncoder());
        ENCODER_MAP.put("pbkdf2", new Pbkdf2PasswordEncoder());
        ENCODER_MAP.put("scrypt", new SCryptPasswordEncoder());
        ENCODER_MAP.put("sha256", new StandardPasswordEncoder());
    }

    public UserPojo getUserInfo(String username){
        return userInfoMapper.findOneByLogin(username);
    }

    public BaseResponse inset(UserPojo userPojo){
        BaseResponse baseResponse=new BaseResponse();
        if(exist(userPojo.getUsername())){
            baseResponse.setFail("用户名已存在",null,"002","1211212");
        }else {
            // 随机使用加密方式
            Random random = new Random();
            int x = random.nextInt(5);
            String encoderType = ENCODER_TYPE.get(x);
            PasswordEncoder passwordEncoder = ENCODER_MAP.get(encoderType);
            userPojo.setPassword(passwordEncoder.encode(userPojo.getPassword()));
            baseResponse.setSuccess("创建成功",userInfoMapper.insert(userPojo),"111");
        }
        return baseResponse;
    }


    /**
     * 判断用户是否存在
     */
    private boolean exist(String username) {
        UserPojo userDO = userInfoMapper.findOneByLogin(username);
        return (userDO != null);
    }

}
