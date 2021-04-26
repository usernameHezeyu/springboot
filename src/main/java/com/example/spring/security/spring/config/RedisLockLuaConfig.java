package com.example.spring.security.spring.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

/**
 * Description: redis锁执行脚本初始化类
 * FileName: RedisLockLuaConfig
 * Date:     2020/2/18 16:55
 * History:
 * <author>          <time>          <version>          <desc>
 * Zhao Xiaoman      2020/2/18 16:55    1.0
 * 版权:   版权所有(C)2019
 */
@Component
public class RedisLockLuaConfig {
    // 锁脚本
    private DefaultRedisScript<Long> lockScript;
    // 解锁脚本
    private DefaultRedisScript<Long> unlockScript;

    public RedisLockLuaConfig(){
        // Lock script
        lockScript = new DefaultRedisScript<Long>();
        lockScript.setScriptSource(
                new ResourceScriptSource(new ClassPathResource("/lock.lua")));
        lockScript.setResultType(Long.class);
        // unlock script
        unlockScript = new DefaultRedisScript<Long>();
        unlockScript.setScriptSource(
                new ResourceScriptSource(new ClassPathResource("/unlock.lua")));
        unlockScript.setResultType(Long.class);
    }

    public DefaultRedisScript<Long> getLockScript() {
        return lockScript;
    }

    public void setLockScript(DefaultRedisScript<Long> lockScript) {
        this.lockScript = lockScript;
    }

    public DefaultRedisScript<Long> getUnlockScript() {
        return unlockScript;
    }

    public void setUnlockScript(DefaultRedisScript<Long> unlockScript) {
        this.unlockScript = unlockScript;
    }
}

