package com.example.spring.security.spring.utils.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName:RedisCommonUtils
 * @Description: redis所有数据类型通用操作
 * @Author:lxw
 * @Date:2019/9/15 9:40
 * @Version: 1.0
 **/
@Component
public class RedisCommonUtils {
    @Autowired
    private StringRedisTemplate template;

    /**
     * 查找匹配的key
     * @param pattern
     * @return
     */
    public Set<String> getAllKeys(String pattern) {
        return template.keys(pattern);
    }

    /**
     * 判断Key是否存在
     * @param key
     * @return true 存在  false 不存在
     */
    public boolean isExists(String key) {
        return template.hasKey(key);
    }

    /**
     * 指定失效时间  单位（S）
     * @param key
     * @param timeout
     */
    public boolean setExpire(String key,Long timeout){
        return template.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取过期时间
     * @param key
     * @return  时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key){
        return	template.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 设置key为永久有效
     * @param key
     * @return
     */
    public boolean setPersist(String key){
        return template.persist(key);
    }


    /**
     * 是否存在指定的key
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        return template.hasKey(key);
    }


    /**
     * 删除指定缓存
     * @param key 可以传一个值 或多个
     */
    public void del(String ... key){
        if(key!=null&&key.length>0){
            if(key.length==1){
                template.delete(key[0]);

            }else{
                template.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }
    /**
     * 从当前数据库随机返回一个key值
     * @return
     */
    public String getKeyRandom(){
        return template.randomKey();
    }

    /**
     * 修改key的名称
     * @param oldKey
     * @param newKey
     */
    public void rename(String oldKey,String newKey){
        template.rename(oldKey, newKey);
    }

    /**
     * 仅当 newkey 不存在时，将 oldKey 改名为 newkey
     * @param oldKey
     * @param newKey
     * @return      newkey  若不存在则修改名称 返回true
     * 						若存在则返回false
     */
    public  boolean renameOnly(String oldKey,String newKey){
        return template.renameIfAbsent(oldKey, newKey);
    }

    /**
      * @Author ZhaoXiaoman
      * @Description //TODO 定时任务分布式锁
      * @Date 11:15 2019/12/5
      * @param key
      * @param value
      * @Return boolean
      */
    public boolean sock(String key, String value){
        if (StringUtils.isBlank(value)){
            return false;
        }else {
            String oldValue = template.opsForValue().getAndSet(key,value);
            if (value.equals(oldValue)){
                return false;
            }else {
                return true;
            }
        }
    }
}
