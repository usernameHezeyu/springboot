package com.example.spring.security.spring.utils.redis;

import com.example.spring.security.spring.config.RedisLockLuaConfig;
import com.example.spring.security.spring.utils.RequestIdUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: redis锁
 * FileName: RedisLock
 * Author:   ZhaoXiaoman
 * Date:     2020/2/18 10:10
 * History:
 * <author>          <time>          <version>          <desc>
 * Hezeyu      2020/2/18 10:10    1.0
 * 版权:   版权所有(C)2019
 * 公司:   c
 */

@Component
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedisLockLuaConfig redisLockLuaConfig;
    @Autowired
    private RequestIdUtil requestIdUtil;

    private static final int LOCK_MAX_EXIST_TIME = 1000;  // 单位ms，锁的有效期
    private static final String LOCK_PREX = "lock:"; // 作为锁的key的前缀

    /**
     * @Author ZhaoXiaoman
     * @Description //TODO 加锁
     * @Date 17:53 2020/2/18
     * @Param [lock]
     * @Return boolean
     */
    public boolean lock(String lock){
        try {
            if(StringUtils.isBlank(lock)){
                return false;
            }
            String lockKey = getLockKey(lock);
            while(true){
                List<String> keyList = new ArrayList<String>();
                keyList.add(lockKey);
                keyList.add(requestIdUtil.get());

                //执行脚本，添加锁
                if(redisTemplate.execute(redisLockLuaConfig.getLockScript(), keyList, String.valueOf(LOCK_MAX_EXIST_TIME)) > 0){
                    return true;
                }else{
                    return false;
                }
            }
        }catch (Exception e){
            return false;
        }
    }


    /**
     * @Author ZhaoXiaoman
     * @Description //TODO 释放锁
     * @Date 17:27 2020/2/18
     * @Param [lock]
     * @Return void
     */
    public boolean unlock(String lock) {
        try {
            String lockKey = getLockKey(lock);

            List<String> keyList = new ArrayList<String>();
            keyList.add(lockKey);
            keyList.add(requestIdUtil.get());

            //执行脚本，删除锁
            redisTemplate.execute(redisLockLuaConfig.getUnlockScript(), keyList);

            return true;
        }catch (Exception e){
            return false;
        }

    }

    /**
     * @Author ZhaoXiaoman
     * @Description //TODO 添加key的前缀
     * @Date 17:33 2020/2/18
     * @Param [lock]
     * @Return java.lang.String
     */
    private String getLockKey(String lock){
        StringBuilder sb = new StringBuilder();
        sb.append(LOCK_PREX).append(lock);
        return sb.toString();
    }
}

