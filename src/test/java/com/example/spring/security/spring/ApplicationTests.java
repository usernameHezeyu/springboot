package com.example.spring.security.spring;

import com.alibaba.fastjson.JSONObject;
import com.example.spring.security.spring.mapper.UserInfoMapper;
import com.example.spring.security.spring.pojo.UserPojo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ApplicationTests {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    void contextLoads() {
        UserPojo d = userInfoMapper.findOneByLogin("d");
        log.info(JSONObject.toJSONString(d));
    }

}
