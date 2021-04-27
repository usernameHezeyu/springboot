package com.example.spring.security.spring.utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @ClassName:RequestIdUtil
 * @Description:
 * @Author:Hezeyu
 * @Date:2019/9/12 10:07
 * @Version: 1.0
 **/
@Component
@Slf4j
public class RequestIdUtil {


    /**
     * @Description //TODO 取得唯一访问ID
     * @Date 10:49 2020/2/25
     * @Param []
     * @Return java.lang.String
     */
    public String get() {
        UUID uuid = UUID.randomUUID();
        log.info(uuid.toString());
        return uuid.toString();
    }

    /**
     * @Description //TODO 取得事件ID
     * @Date 10:52 2020/2/25
     * @Param []
     * @Return java.lang.String
     */
    public String getEventId() {
        return this.get() + "1";
    }

    /**
     * @Description //TODO 按序号取得事件ID
     * @Date 10:53 2020/2/25
     * @Param [num]
     * @Return java.lang.String
     */
    public String getEventId(Integer num) {
        return this.get() + String.valueOf(num);
    }
}
