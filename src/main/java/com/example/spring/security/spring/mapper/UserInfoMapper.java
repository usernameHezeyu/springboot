package com.example.spring.security.spring.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spring.security.spring.pojo.UserPojo;

public interface UserInfoMapper extends BaseMapper<UserPojo> {

    UserPojo findOneByLogin(String login);

}