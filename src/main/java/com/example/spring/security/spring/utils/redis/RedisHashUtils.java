package com.example.spring.security.spring.utils.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName:RedisHashUtils
 * @Description:  操作redis hashmap工具类
 * @Author:Hezeyu
 * @Date:2019/9/15 9:40
 * @Version: 1.0
 **/
@Component
public class RedisHashUtils {
    @Autowired
    private StringRedisTemplate template;
    /**
     * 设置散列hashKey的值
     * @param key          键
     * @param hashKey	   hash键
     * @param value			值
     */
    public void setKey(String key,Object hashKey, Object value){
        HashOperations<String, Object, Object> opsForHash = template.opsForHash();
        opsForHash.put(key, hashKey, value);

    }

    /**
     * 以map集合的形式设置散列hashKey的值
     * @param key
     * @param map
     */
    public void setKey(String key,Map<String, Object> map){
        HashOperations<String, Object, Object> opsForHash = template.opsForHash();
        opsForHash.putAll(key, map);
    }

    /**
     * 仅当hashKey不存在时才设置散列hashKey的值
     * @param key
     * @param hashKey
     * @param value
     * @return     hashkey不存在则设置成功返回true
     * 						存在则返回false
     */
    public boolean setKeyIfNone(String key,Object hashKey,Object value){
        HashOperations<String, Object, Object> opsForHash = template.opsForHash();
        return opsForHash.putIfAbsent(key, hashKey, value);
    }

    /**
     *  获取变量中的指定map键是否有值,如果存在该map键则获取值，没有则返回null
     * @param key
     * @param hashKey
     * @return
     */
    public Object getValue(String key, String hashKey){
        HashOperations<String, Object, Object> opsForHash = template.opsForHash();
        return opsForHash.get(key, hashKey);
    }


    /**
     * 判断指定map中是否有某个hashkey
     * @param key
     * @param hashKey
     * @return
     */
    public boolean isExists(String key, String hashKey){
        HashOperations<String, Object, Object> opsForHash = template.opsForHash();
        return opsForHash.hasKey(key, hashKey);
    }

    /**
     * 删除指定hashmap中的多个键值对
     * @param key
     * @param hashKeys
     * @return      影响的数量
     */
    public long deleteHashKeys(String key,Object ... hashKeys){
        HashOperations<String, Object, Object> opsForHash = template.opsForHash();
        return opsForHash.delete(key, hashKeys);
    }


    /**
     * 获取指定的hashmap键值对
     * @param key
     * @return
     */
    public Map getHashmap(String key){
        HashOperations<String, Object, Object> opsForHash = template.opsForHash();
        return opsForHash.entries(key);
    }

    /**
     * 获取键值散列的键集(字段)(hashkeys)
     * @param key
     * @return
     */
    public Set<Object> getHashKeys(String key){
        HashOperations<String, Object, Object> opsForHash = template.opsForHash();
        return opsForHash.keys(key);

    }

    /**
     * 获取目标hashmap中所有的values
     * @param key       	目标hashmap
     * @return
     */
    public List<Object> getValues(String key){
        HashOperations<String, Object, Object> opsForHash = template.opsForHash();
        return opsForHash.values(key);
    }

    /**
     * 在指定的hashmap中获取指定hashkey对应的值
     * @param key
     * @param hashKeys
     * @return
     */
    public List<Object>  getValues(String key, Collection<Object> hashKeys){
        HashOperations<String, Object, Object> opsForHash = template.opsForHash();
        return opsForHash.multiGet(key, hashKeys);
    }

    /**
     * 迭代获取匹配的键值对
     * @param key
     * @param options				匹配规则
     * @return
     */
    public Cursor<Map.Entry<Object, Object>> getHashMap(String key, ScanOptions options){
        HashOperations<String, Object, Object> opsForHash = template.opsForHash();
        return opsForHash.scan(key, options);
    }

    /**
     * 通过给定的delta增加散列hashKey的值（整型）
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    public long increase(String key,String hashKey,long delta){
        HashOperations<String, Object, Object> opsForHash = template.opsForHash();
        return opsForHash.increment(key, hashKey, delta);
    }
    /**
     * 通过给定的delta增加散列hashKey的值（浮点型）
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    public Double increase(String key,String hashKey,Double delta){
        HashOperations<String, Object, Object> opsForHash = template.opsForHash();
        return opsForHash.increment(key, hashKey, delta);
    }

    /**
     * 获取key对应的hashmap表的大小个数
     * @param key
     * @return
     */
    public long getSize(String key){
        HashOperations<String, Object, Object> opsForHash = template.opsForHash();
        return opsForHash.size(key);
    }


}
