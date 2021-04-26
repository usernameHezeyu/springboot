package com.example.spring.security.spring.utils.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @ClassName:RedisSetUtils
 * @Description:  操作redis 无序集合工具类
 * @Author:lxw
 * @Date:2019/9/15 9:40
 * @Version: 1.0
 **/
@Component
public class RedisSetUtils {
    @Autowired
    private StringRedisTemplate template;

    /**
     * 批量添加元素,集合中之前就已经有数据了，那么返回0，否则返回1
     * @param key
     * @param values
     */
    public long setKey(String key, String... values){
        SetOperations<String, String> ops = template.opsForSet();
        return ops.add(key, values);
    }

    /**
     * 获取集合中的值
     * @param key
     * @return
     */
    public Set<String> getValue(String key){
        SetOperations<String, String> ops = template.opsForSet();
        return ops.members(key);
    }

    /**
     * 批量删除数据
     * @param key
     * @param values
     * @return   删除的数量
     */
    public long remove(String key,Object ...values){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.remove(key, values);
    }

    /**
     * 判断目标集合是否包含某个元素
     *
     * @param key
     * @param value
     */
    public boolean contains(String key, Object value) {
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.isMember(key, value);
    }

    /**
     * 获取目标集合的长度
     * @param key
     * @return
     */
    public long getSize(String key){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.size(key);
    }

    /**
     * 随机获取目标集合中的元素
     * @param key
     * @return
     */
    public String getRandom(String key){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.randomMember(key);
    }

    /**
     * 随机获取目标集合中一定数量的元素  (可重复)
     * @param key
     * @param count
     * @return
     */
    public List<String> getRandom(String key, long count){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.randomMembers(key, count);
    }

    /**
     * 随机获取集合中count个元素并且去除重复的
     * @param key
     * @param count
     * @return
     */
    public Set<String> getRandomOnly(String key,long count){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.distinctRandomMembers(key, count);
    }

    /**
     * 将目标集合中的指定元素转移到其他集合
     * @param key  				目标集合
     * @param value				指定元素
     * @param otherKey			其他集合
     * @return
     */
    public boolean moveToOther(String key,String value,String otherKey){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.move(otherKey, value, otherKey);
    }

    /**
     * 随机移除目标集合中的一个元素
     * @param key
     * @return     移除的元素
     */
    public String removeRandom(String key){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.pop(key);
    }

    /**
     * 使用迭代器获取元素
     * @param key
     * @param options     匹配规则
     * @return
     */
    public Cursor<String> sScan(String key, ScanOptions options) {
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.scan(key, options);
    }

    /**
     * 2个集合的差集  属于集合key1 不属于key2的元素
     * @param key1
     * @param key2
     * @return
     */
    public Set<String> difference(String key1,String key2){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.difference(key1, key2);
    }
    /**
     * 获取多个集合的差集  属于key 不属于otherkeys的元素
     * @param key
     * @param otherKeys
     * @return
     */
    public Set<String> difference(String key,Collection<String> otherKeys){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.difference(key, otherKeys);
    }

    /**
     * 取出2个集合的差集  并存入storekey集合中
     * @param key1
     * @param key2
     * @param storeKey
     * @return       			返回存入个数
     */
    public long differenceAndStore(String key1,String key2,String storeKey){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.differenceAndStore(key1, key2, storeKey);
    }
    /**
     * 取出key与其他无序集合的差集 并存入storekey集合中
     * @param key
     * @param otherkeys
     * @param storeKey
     * @return					返回存入个数
     */
    public long differenceAndStore(String key,Collection<String> otherkeys,String storeKey){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.differenceAndStore(key, otherkeys, storeKey);
    }

    /**
     * 获取2个无序集合的交集
     * @param key
     * @param otherkey
     * @return
     */
    public Set<String> intersec(String key, String otherkey){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.intersect(key, otherkey);

    }
    /**
     * 获取多个无序集合的交集
     * @param key
     * @param otherkeys  多个无序集合
     * @return
     */
    public Set<String> intersec(String key,Collection<String> otherkeys){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.intersect(key, otherkeys);

    }

    /**
     * 获取2个无序集合的交集  并存到目标集合上
     * @param key
     * @param otherKey
     * @param storeKey 			目标集合
     * @return					交集的长度
     */
    public long insertAndStore(String key ,String otherKey,String storeKey){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.intersectAndStore(key, otherKey, storeKey);
    }
    /**
     * 获取多个无序集合的交集  并存到目标集合上
     * @param key
     * @param otherKeys
     * @param storeKey 			目标集合
     * @return					交集的长度
     */
    public long insertAndStore(String key ,Collection<String> otherKeys,String storeKey){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.intersectAndStore(key, otherKeys, storeKey);
    }

    /**
     * 获取2个集合的并集
     * @param key
     * @param otherKey
     * @return
     */
    public Set<String> union(String key,String otherKey){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.union(key, otherKey);
    }

    /**
     * 获取多个集合的并集
     * @param key
     * @param otherKeys
     * @return
     */
    public Set<String> union(String key, Collection<String> otherKeys){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.union(key, otherKeys);
    }

    /**
     * 获取2个集合的并集 并保存在目标集合上
     * @param key
     * @param otherKey
     * @param storeKey				目标集合
     * @return
     */
    public long unionAndStore(String key,String otherKey,String storeKey){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.unionAndStore(key, otherKey, storeKey);
    }
    /**
     * 获取多个集合的并集 并保存在目标集合上
     * @param key
     * @param otherKeys
     * @param storeKey				目标集合
     * @return
     */
    public long unionAndStore(String key,Collection<String> otherKeys,String storeKey){
        SetOperations<String, String> opsForSet = template.opsForSet();
        return opsForSet.unionAndStore(key, otherKeys, storeKey);
    }


}
