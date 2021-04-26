package com.example.spring.security.spring.utils.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName:RedisListUtils
 * @Description:   操作redis list工具类
 * @Author:lxw
 * @Date:2019/9/15 9:40
 * @Version: 1.0
 **/

@Component
public class RedisListUtils {
    @Autowired
    private StringRedisTemplate template;

    /**
     * 设置key列表中指定位置的值为value index不能大于列表长度。大于抛出异常
     * 为负数则从右边开始计算
     * @param key
     * @param index
     * @param value
     */
    public void setKey(String key, long index, String value){
        ListOperations<String, String> ops = template.opsForList();
        ops.set(key, index, value);
    }

    /**
     * 获取指定范围内的元素
     * @param hashKey
     * @param start
     * @param end
     * @return
     */
    public Object getValue(String hashKey, long start, long end){
        ListOperations<String, String> ops = template.opsForList();
        return ops.range(hashKey, start, end);
    }


    /**
     * 获取列表指定位置的值
     * @param key
     * @param index
     * @return
     */
    public Object getIndex(String key,long index ){
        ListOperations<String, String> opsForList = template.opsForList();

        return opsForList.index(key, index);

    }
    /**
     * 保留集合指定范围内的值，其他都删除
     * @param key       not null
     * @param start		起始位置
     * @param end		结束位置
     */
    public void  retain(String key,long start,long end){
        ListOperations<String, String> opsForList = template.opsForList();
        opsForList.trim(key, start, end);
    }

    /**
     * 获取列表的长度
     * @param key        not null
     * @return
     */
    public long getSize(String key){
        ListOperations<String, String> opsForList = template.opsForList();
        return opsForList.size(key);
    }

    /**
     * 在列表的左部(头部)插入值
     * @param key
     * @param value
     * @return   插入后列表的长度
     */
    public long insertLeft(String key,String value){
        ListOperations<String, String> opsForList = template.opsForList();
        return opsForList.leftPush(key, value);
    }

    /**
     * 在列表左部(头部)插入多个值
     * @param key
     * @param values
     * @return   返回插入后列表的长度
     */
    public long insertLeftAll(String key,String ...values){
        ListOperations<String, String> opsForList = template.opsForList();
        return opsForList.leftPushAll(key, values);
    }

    /**
     * 在列表的左部(头部)依次插入集合的值
     * @param key
     * @param values
     * @return  返回插入后列表的长度
     */
    public Long insertLeftCollection(String key , Collection<String> values){
        ListOperations<String, String> opsForList = template.opsForList();
        return opsForList.leftPushAll(key, values);
    }

    /**
     * 如果列表存在，则在列表左边插入值value
     * @param key
     * @param value
     * @return
     */
    public long insertLeftIfExist(String key,String value){
        ListOperations<String, String> opsForList = template.opsForList();
        return opsForList.leftPushIfPresent(key, value);
    }

    /**
     * 在key的列表中指定的value左边（前面）插入一个新的value.
     *  如果 指定的value不存在则不插入任何值。
     * @param key
     * @param pivot      指定的值
     * @param value		  插入的值
     * @return
     */
    public long insertLeft(String key,String pivot,String value){
        ListOperations<String, String> opsForList = template.opsForList();
        return opsForList.leftPush(key, pivot, value);
    }

    /**
     * 在列表的右部(尾部)插入值
     * @param key
     * @param value
     * @return   插入后列表的长度
     */
    public long insertRight(String key,String value){
        ListOperations<String, String> opsForList = template.opsForList();
        return opsForList.rightPush(key, value);
    }
    /**
     * 在列表的右部(尾部)插入多个值
     * @param key
     * @param values
     * @return
     */
    public long insertRightAll(String key,String ...values){
        ListOperations<String, String> opsForList = template.opsForList();
        return opsForList.rightPushAll(key, values);
    }
    /**
     * 列表的右部(尾部)依次插入集合的值
     * @param key
     * @param values
     * @return
     */
    public long insertRightCollection(String key,Collection<String> values){
        ListOperations<String, String> opsForList = template.opsForList();
        return opsForList.rightPushAll(key, values);
    }
    /**
     * 如果列表存在，则在列表右边插入值value
     * @param key
     * @param value
     * @return
     */
    public long insertRightIfExist (String key,String value){
        ListOperations<String, String> opsForList = template.opsForList();
        return opsForList.rightPushIfPresent(key, value);
    }
    /**
     * 在key的列表中指定的value左边（前面）插入一个新的value.
     * 如果 指定的value不存在则不插入任何值。
     * @param key
     * @param pivot			指定的值
     * @param value			插入的值
     * @return				插入后列表的长度
     */
    public long insertRight(String key,String pivot,String value){
        ListOperations<String, String> opsForList = template.opsForList();
        return opsForList.rightPush(key, pivot, value);
    }

    /**
     * 从存储在键中的列表中删除等于值的元素的第一个计数事件
     * @param hashKey
     * @param count     移除的数量
     * 					count>0 :从表头开始查找，移除count个
     *  				count=0 :从表头开始查找，移除所有等于value的
     *  				count<0 :从表尾开始查找，移除count绝对值的个数
     * @param value
     * @return
     */
    public Long remove(String hashKey, long count, Object value){
        ListOperations<String, String> ops = template.opsForList();
        return ops.remove(hashKey, count, value);
    }

    /**
     * 移除列表中(头部)的第一个值，并返回该值
     * @param key
     * @return
     */
    public String removeLeft(String key){
        ListOperations<String, String> opsForList = template.opsForList();
        return opsForList.leftPop(key);
    }

    /**
     * 阻塞移除列表头部第一个值，并返回
     * 若列表不存在则一直阻塞指定的时间单位
     * @param key
     * @param timeout   单位S
     * @return
     */
    public String removeLeft(String key,long timeout){
        ListOperations<String, String> opsForList = template.opsForList();
        return opsForList.leftPop(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 移除列表中(尾部)的最后一个值，并返回该值
     * @param key
     * @return
     */
    public String removeRight(String key){
        ListOperations<String, String> opsForList = template.opsForList();
        return opsForList.rightPop(key);
    }

    /**
     * 阻塞移除列表(尾部)最后一个值，并返回
     * 若列表不存在则一直阻塞指定的时间单位
     * @param key
     * @param timeout   单位S
     * @return
     */
    public String removeRight(String key,long timeout){
        ListOperations<String, String> opsForList = template.opsForList();
        return opsForList.rightPop(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 从指定列表中从右边（尾部）移除第一个值，并将这个值从左边（头部）插入目标列表
     *
     * @param removeKey      	移除的列表key
     * @param insertKey			插入的列表key
     * @return					返回移除（插入）的值
     */
    public String removeRightAndInsertLeft(String removeKey,String insertKey){
        ListOperations<String, String> opsForList = template.opsForList();
        return opsForList.rightPopAndLeftPush(removeKey, insertKey);

    }

    /**
     * 阻塞方法从指定列表中从右边（尾部）移除第一个值，并将这个值从左边（头部）插入目标列表
     * 如果移除的列表中没有值，则一直阻塞指定的单位时间
     * @param removeKey      	移除的列表key
     * @param insertKey			插入的列表key
     * @param timeout			单位s
     * @return					返回移除（插入）的值
     */
    public String removeRightAndInsertLeft(String removeKey,String insertKey,long timeout){
        ListOperations<String, String> opsForList = template.opsForList();
        return opsForList.rightPopAndLeftPush(removeKey, insertKey,timeout, TimeUnit.SECONDS);

    }

}
