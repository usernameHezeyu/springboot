package com.example.spring.security.spring.utils.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @ClassName:RedisZSetUtils
 * @Description:  操作redis 有序集合工具类
 * @Author:lxw
 * @Date:2019/9/15 9:40
 * @Version: 1.0
 **/
@Component
public class RedisZSetUtils {
    @Autowired
    private StringRedisTemplate template;

    /**
     * 新增一个有序集合，VALUE存在的话为false，不存在的话为true
     * 若key value相同  score不同 则以现在的score覆盖之前的
     * @param key
     * @param value
     * @param score
     * @return
     */
    public boolean  add(String key,String value,double score){
        return template.opsForZSet().add(key, value, score);
    }


    /**
     * 增加一个有序集合
     * @param key
     * @param tuples
     * @return
     */
    public long add(String key, Set<ZSetOperations.TypedTuple<String>> tuples){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.add(key, tuples);
    }

    /**
     * 移除指定key中一个或者多个成员
     * @param key
     * @param values
     * @return
     */
    public long remove(String key,String ...values){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.remove(key, values);
    }

    /**
     * 移除指定索引位置的成员，其中有序集成员按分数值递增(从小到大)顺序排列
     * @param key
     * @param start
     * @param end
     * @return
     */
    public long removeRang(String key,long start,long end){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.removeRange(key, start, end);
    }

    /**
     * 获取有序集合的成员数
     * @param key
     * @return
     */
    public long zCard(String key){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.zCard(key);
    }

    /**
     * 获取指定成员的score值
     * @param key
     * @param value
     * @return
     */
    public double getScore(String key,String value){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.score(key, value);
    }

    /**
     * 返回有序集中指定成员的排名，其中有序集成员按分数值递增(从小到大)顺序排列
     * @param key
     * @param value
     * @return
     */
    public long getRank(String key,String value){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.rank(key, value);
    }

    /**
     * 返回有序集中指定成员的排名，其中有序集成员按分数值递增(从大到小)顺序排列
     * @param key
     * @param value
     * @return
     */
    public long getReverseRank(String key,String value){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.reverseRank(key, value);
    }

    /**
     * 增加元素的score值，并返回增加后的值
     * @param key
     * @param value
     * @param delta
     * @return
     */
    public double incrementScore(String key,String value,double delta){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.incrementScore(key, value, delta);
    }

    /**
     * 获取key对应的有序集合排序（从小到大）  （只有成员value）
     * @param key
     * @return
     */
    public Set<String> getRange(String key){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.range(key, 0, -1);
    }

    /**
     * 获取key对应的有序集合指定区间排序（从小到大）  （只有成员value）
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> getRange(String key,long start,long end){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.range(key, start, end);
    }

    /**
     * 获取key对应的有序集合排序（从小到大）  （返回结果有key value score）
     * @param key
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> getRangeWithScore(String key){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.rangeWithScores(key, 0, -1);
    }

    /**
     * 获取key对应的有序集合指定区间排序（从小到大）  （返回结果有key value score）
     * @param key
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> getRangeWithScore(String key, long start, long end){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.rangeWithScores(key, start, end);
    }

    /**
     * 通过分数返回有序集合指定区间内的成员对象，其中有序集成员按分数值递增(从小到大)顺序排列
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> getRangeWithScoreByScore(String key, double min, double max){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.rangeByScoreWithScores(key, min, max);
    }


    /**
     * 通过分数返回有序集合指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> getRangeByScore(String key,double min,double max){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.rangeByScore(key, min, max);
    }

    /**
     * 通过分数返回有序集合指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
     * @param key
     * @param min
     * @param max
     * @param offset   偏移量
     * @param count		查询数量
     * @return
     */
    public Set<String> getRangeByScore(String key,double min,double max,long offset,long count){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.rangeByScore(key, min, max,offset,count);
    }

    /**
     * 获取key对应的有序集合排序（从大到小）  （只有成员value）
     * @param key
     * @return
     */
    public Set<String> getReverseRange(String key){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.reverseRange(key, 0, -1);
    }

    /**
     * 获取key对应的有序集合指定区间排序（从大到小）  （只有成员value）
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> getReverseRange(String key, long start,long end){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.reverseRange(key, start, end);
    }

    /**
     * 获取key对应的有序集合排序（从大到小）  （有key 成员value  分数score）
     * @param key
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> getReverseRangeWithScore(String key){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return	operations.reverseRangeWithScores(key, 0, -1);
    }

    /**
     * 获取key对应的有序集合指定区间排序（从大到小）  （有key 成员value  分数score）
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> getReverseRangeWithScore(String key, long start, long end){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return	operations.reverseRangeWithScores(key, start, end);
    }

    /**
     * 获取key对应的有序集合指定分数范围排序（从大到小）  （只有成员value）
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> getReverseRangeByScore(String key,double min,double max){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.reverseRangeByScore(key, min, max);
    }

    /**
     * 获取key对应的有序集合指定分数范围排序（从大到小）  （只有成员value）
     * @param key
     * @param min
     * @param max
     * @param offset 偏移量
     * @param count  数量
     * @return
     */
    public Set<String> getReverseRangeByScore(String key,double min,double max,long offset,long count){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.reverseRangeByScore(key, min, max,offset,count);
    }


    /**
     * 获取key对应的有序集合指定分数段排序（从大到小）  （有key 成员value  分数score）
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> getReverseRangeWithScoreByScore(String key, double min, double max){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.reverseRangeByScoreWithScores(key, min, max);

    }

    /**
     * 获取key对应的有序集合指定分数段排序  偏移量开始 （从大到小）  （有key 成员value  分数score）
     * @param key
     * @param min
     * @param max
     * @param offset 偏移量
     * @param count  数量
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> getReverseRangeWithScoreByScore(String key, double min, double max, long offset, long count){
        ZSetOperations<String, String> operations = template.opsForZSet();
        return operations.reverseRangeByScoreWithScores(key, min, max,offset,count);
    }

}
