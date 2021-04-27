package com.example.spring.security.spring;

import com.example.spring.security.spring.mapper.UserInfoMapper;
import com.example.spring.security.spring.utils.redis.RedisStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ApplicationTests {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisStringUtils redisStringUtils;

    @Test
    void contextLoads() {
//        UserPojo d = userInfoMapper.findOneByLogin("d");
        redisStringUtils.setKey("kk","ddd");
        String kk = redisStringUtils.getValue("kk");
        log.info(kk);
    }

}
