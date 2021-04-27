package com.example.spring.security.spring.utils.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName:RedisStringUtils
 * @Description:  redis操作string类型工具类
 * @Author:Hezeyu
 * @Date:2019/9/15 9:40
 * @Version: 1.0
 **/

@Component
public class RedisStringUtils {
    @Autowired
    private StringRedisTemplate template;
    /**
     * 存入缓存 不设置过期时间
     * @param key
     * @param value
     */
    public void setKey(String key,String value){
        ValueOperations<String, String> operations = template.opsForValue();
        operations.set(key,value);
    }

    /**
     * 获取key过期时间
     * @param key
     */
    public String  getKeyTime(String key){
        return template.getExpire(key,TimeUnit.MICROSECONDS).toString();
    }

    /**
     * 存入缓存 设置过期时间 单位(S)
     * @param key
     * @param value
     * @param timeOut
     */
    public void setKey(String key,String value,Long timeOut){
        ValueOperations<String, String> operations = template.opsForValue();
        if (timeOut>0) {
            operations.set(key, value, timeOut, TimeUnit.SECONDS);
        }else {
            setKey(key, value);
        }
    }

    /**
     * 从偏移量offset开始用value重写key对应的值
     * @param key
     * @param value
     * @param offset
     */
    public void setOff(String key,String value,Long offset){
        ValueOperations<String, String> operations = template.opsForValue();
        operations.set(key, value, offset);
    }


    /**
     * key键对应的值value对应的ascii码,在offset的位置(从左向右数)变为value
     * @param key
     * @param offset
     * @param boo
     */
    public void setBit(String key, Long offset,boolean boo){
        ValueOperations<String, String> operations = template.opsForValue();
        operations.setBit(key, offset, boo);
    }

    /**
     *  获取key键对应的值
     * @param key
     * @return
     */
    public String getValue(String key){
        ValueOperations<String, String> operations = template.opsForValue();
        return operations.get(key);
    }


    /**
     * 截取key键对应值得字符串，从开始下标位置开始到结束下标的位置(包含结束下标)的字符串。
     * @param key
     * @param start
     * @param end
     * @return
     */
    public String getValue(String key,long start,long end){
        ValueOperations<String, String> operations = template.opsForValue();
        return operations.get(key, start, end);
    }

    /**
     * 获取指定key对应的value的长度
     * @param key
     * @return
     */
    public long size(String key){
        ValueOperations<String, String> operations = template.opsForValue();
        return operations.size(key);
    }

    /**
     * 在原有的值基础上新增字符串到末尾
     * 如果键不存在，则它被创建并设置为空字符串，因此APPEND在这种特殊情况下将类似于SET。
     * @param key
     * @param value
     * @return
     */
    public int append(String key,String value) {
        ValueOperations<String, String> operations = template.opsForValue();
        return operations.append(key, value);
    }

    /**
     * 重新给key赋值并返回原来的值
     * @param key
     * @param value
     * @return
     */
    public String getAndSet(String key,String value){
        ValueOperations<String, String> operations = template.opsForValue();
        return operations.getAndSet(key, value);
    }

    /**
     * 如果键不存在则新增,存在则不改变已经有的值
     * @param key
     * @param value
     * @return   不存在返回true,存在返回false
     */
    public boolean setIfAbsent(String key,String value){
        ValueOperations<String, String> operations = template.opsForValue();
        return operations.setIfAbsent(key, value);
    }

    /**
     * 以增量的方式将long值存储在变量中
     * @param key
     * @param delta
     * @return
     */
    public long increment(String key,long delta){
        ValueOperations<String, String> operations = template.opsForValue();
        return operations.increment(key, delta);
    }

    /**
     * 以增量的方式将double值存储在变量中
     * @param key
     * @param delta
     * @return
     */
    public double increment(String key, double delta){
        ValueOperations<String, String> operations = template.opsForValue();
        return operations.increment(key, delta);
    }

    /**
     * 设置map集合到redis   将多个key-value缓存redis
     * @param map
     */
    public void setMap(Map<String,String> map){
        ValueOperations<String, String> operations = template.opsForValue();
        operations.multiSet(map);
    }


    /**
     * 通过集合取出对应的value
     * @param Collection
     * @return
     */
    public Collection<String> getList(Collection<String> Collection){
        ValueOperations<String, String> operations = template.opsForValue();
        return operations.multiGet(Collection);
    }

}
