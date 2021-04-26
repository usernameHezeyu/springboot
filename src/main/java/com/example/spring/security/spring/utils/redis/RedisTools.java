package com.example.spring.security.spring.utils.redis;

import com.example.spring.security.spring.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Description: redis业务工具类
 * FileName: RedisTools
 * Date:     2020/2/20 15:49
 * History:
 * <author>          <time>          <version>          <desc>
 * Zhao Xiaoman      2020/2/20 15:49    1.0
 * 版权:   版权所有(C)2019
 */

@Component
@Slf4j
public class RedisTools {
    @Autowired
    private RedisStringUtils redisStringUtils;
    @Autowired
    private RedisLock redisLock;

    //大于
    public static final String GREATER = "GREATER";
    //小于
    public static final String LESS = "LESS";
    //大于等于
    public static final String NOLESS = "NOLESS";
    //小于等于
    public static final String NOGREATER = "NOGREATER";
    //等于
    public static final String EQUAL = "EQUAL";

    /**
     * @Author ZhaoXiaoman
     * @Description //TODO 比较缓存中时间，晚于或缓存时间不存在则更新缓存，返回true,其他则返回false
     * @Description //TODO 各服务共用
     * @Date 15:52 2020/2/20
     * @Param key 缓存的key，时间缓存和锁共用
     * @Param time 时间戳，与缓存数据比较的时间，在比较式的第一位
     * @Param compareCode 比较符
     * @Return boolean
     */
    public boolean compareCacheTime(String key, Long time){
        return this.compareCacheTime(key,time,GREATER);
    }

    /**
     * @Author ZhaoXiaoman
     * @Description //TODO 比较缓存时间和状态，成立或缓存不存在则更新缓存，返回true,不成立则返回false
     * @Description //TODO 基础设施接入服务专用
     * @Date 15:52 2020/2/20
     * @Param key 缓存的key，时间缓存和锁共用
     * @Param time 时间戳，与缓存数据比较的时间，在比较式的第一位
     * @Param status 设备状态
     * @Return boolean
     */
    public boolean compareCacheTimeAndStatus(String key, Long time, String status){
        try {
            //校验数据
            if (time <= 0 || key == null || status == null){
                return false;
            }
            //加锁
            if (redisLock.lock(key)){
                //获取缓存数据
                String cacheStr = redisStringUtils.getValue(key);

                //当缓存数据存在，比较缓存时间时间和状态，不成立则直接返回
                if (cacheStr != null){
                    //拆分缓存时间和状态
                    String[] list = cacheStr.split(",");
                    String cacheTimeStr = list[0];
                    String cacheStatus = list[1];
                    //比较缓存时间，早于缓存时间则直接返回
                    if (this.compareTime(time, LESS, cacheTimeStr)){
                        log.info("事件时间不能早于Redis缓存时间!");
                        return false;
                    }
                    //比较缓存时间，大于缓存时间则比较状态，状态无变化则直接返回
                    if (this.compareTime(time, GREATER, cacheTimeStr)){
                        if (status.equals(cacheStatus)){
                            return false;
                        }
                    }
                }
                //更新缓存
                redisStringUtils.setKey(key, this.getNewCacheData(time, status) );
                return true;
            } else {
                throw new BusinessException("获取redis锁失败！", new Throwable());
            }
        }catch (Exception e){
            throw new BusinessException("与缓存redis时间比较出现异常！", new Throwable());
        }finally {
            //释放锁
            redisLock.unlock(key);
        }
    }

    /**
     * @Author ZhaoXiaoman
     * @Description //TODO 比较缓存中时间，成立或缓存时间不存在则更新缓存，返回true,其他则返回false
     * @Date 15:52 2020/2/20
     * @Param key 缓存的key，时间缓存和锁共用
     * @Param time 时间戳，与缓存数据比较的时间，在比较式的第一位
     * @Param compareCode 比较符
     * @Return boolean
     */
    public boolean compareCacheTime(String key, Long time, String compareCode){
        try {
            //校验数据
            if (time <= 0 || key == null){
                return false;
            }
            //加锁
            if (redisLock.lock(key)){
                //获取缓存时间
                String cacheTimeStr = redisStringUtils.getValue(key);
                //当缓存时间存在，根据比较符比较时间，不成立则直接返回false，成立则继续
                if (cacheTimeStr != null && !this.compareTime(time, compareCode, cacheTimeStr)){
                    return false;
                }
                //更新缓存
                redisStringUtils.setKey(key, String.valueOf(time));
                return true;
            } else {
                throw new BusinessException("获取redis锁失败！", new Throwable());
            }
        }catch (Exception e){
            throw new BusinessException("与缓存redis时间比较出现异常！", new Throwable());
        }finally {
            //释放锁
            redisLock.unlock(key);
        }
    }

    /**
    * @Author ZhaoXiaoman
    * @Description //TODO 私有方法，比较时间和缓存时间
    * @Date 16:37 2020/2/20
    * @Param time 时间
    * @Param cacheTimeStr 缓存时间,字符串型
    * @Param compareCode 比较符编码
    * @Return boolean
    */
    private boolean compareTime(Long time, String compareCode, String cacheTimeStr ){

        Long cacheTime = Long.parseLong(cacheTimeStr);

        //条件满足，直接返回true,其余最终返回false
        switch (compareCode){
            case GREATER :
                if (time > cacheTime){
                    return true;
                }
                break;
            case NOGREATER :
                if (time <= cacheTime){
                    return true;
                }
                break;
            case LESS :
                if (time < cacheTime){
                    return true;
                }
                break;
            case NOLESS :
                if (time >= cacheTime){
                    return true;
                }
                break;
            case EQUAL :
                if (time == cacheTime){
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }

    /**
    * @Author ZhaoXiaoman
    * @Description //TODO 私有方法，取得新的缓存数据
    * @Date 18:36 2020/2/20
    * @Param [time, status]
    * @Return java.lang.String
    */
    private String getNewCacheData(Long time, String status){
        StringBuilder value = new StringBuilder();
        value.append(time).append(",").append(status);
        return value.toString();
    }
}

