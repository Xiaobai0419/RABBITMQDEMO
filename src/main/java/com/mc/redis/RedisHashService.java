package com.mc.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/** 
* @author yf
* redis Hash Service 模板类
* 依赖HashOperations接口。调用者需要指定具体的T类型。
* @date 创建时间：2017年12月2日 上午11:21:24 
* @version 1.0   
*/
@Component
public class RedisHashService<T> {

    @Autowired
    protected @Qualifier("redisTemplate") RedisTemplate<String, Object> redisTemplate;

    @Autowired
    protected @Qualifier("redisTemplateDb1") RedisTemplate<String, Object> redisTemplateDb1;
    
    @Resource
    protected @Qualifier("hashOperations") HashOperations<String, String, T> hashOperations;

    @Resource
    protected @Qualifier("hashOperationsDb1") HashOperations<String, String, T> hashOperationsDb1;

     /**
     * 添加
     * @param redisKey 传入redisKey的名称
     * @param key    key
     * @param doamin 对象
     * @param expire 过期时间(单位:秒),传入 -1 时表示不设置过期时间
     */
    public void put(String redisKey,String key, T doamin, long expire) {
        RedisExceptionUtil.checkKeyException(redisKey,key);
        hashOperations.put(redisKey, key, doamin);
        if (expire != -1) {
            redisTemplate.expire(redisKey, expire, TimeUnit.SECONDS);
        }
    }
    /**
     * 添加 db1
     * @param redisKey 传入redisKey的名称
     * @param key    key
     * @param doamin 对象
     * @param expire 过期时间(单位:秒),传入 -1 时表示不设置过期时间
     */
    public void putDb1(String redisKey,String key, T doamin, long expire) {
        RedisExceptionUtil.checkKeyException(redisKey,key);
        hashOperationsDb1.put(redisKey, key, doamin);
        if (expire != -1) {
            redisTemplateDb1.expire(redisKey, expire, TimeUnit.SECONDS);
        }
    }
    
    /**
     * 删除
     * @param redisKey 传入redisKey的名称
     * @param key 传入key的名称
     */
    public void remove(String redisKey,String key) {
        RedisExceptionUtil.checkKeyException(redisKey,key);
        hashOperations.delete(redisKey, key);
    }
    
    /**
     * 查询
     * @param redisKey 传入redisKey的名称
     * @param key 查询的key
     * @return
     */
    public T get(String redisKey,String key) {
        RedisExceptionUtil.checkKeyException(redisKey,key);
        return hashOperations.get(redisKey, key);
    }
    /**
     * 查询 db1
     * @param redisKey 传入redisKey的名称
     * @param key 查询的key
     * @return
     */
    public T getDb1(String redisKey,String key) {
        RedisExceptionUtil.checkKeyException(redisKey,key);
        return hashOperationsDb1.get(redisKey, key);
    }
    
    
    /**
     * @功能描述 
     * 将Map集合数据写入Redis
     * @param redisKey
     * @param map
     * @param expire
     */
    public void putAll(String redisKey,Map<String,T> map, long expire){
        RedisExceptionUtil.checkKeyException(redisKey);
        RedisExceptionUtil.checkObjectIsNull(map);
        hashOperations.putAll(redisKey, map);
        if (expire != -1) {
            redisTemplate.expire(redisKey, expire, TimeUnit.SECONDS);
        }
    }
    
    /**
     * @功能描述 获取HashMap list集合,读取putAll写入的数据
     * @param redisKey
     * @param keys
     * @return
     */
    public List<T> multiGet(String redisKey,List<String> keys) {
        RedisExceptionUtil.checkKeyException(redisKey);
        RedisExceptionUtil.checkObjectIsNull(keys);
        keys.stream().forEach(key -> RedisExceptionUtil.checkObjectIsNull(keys));
        return hashOperations.multiGet(redisKey, keys);
    }
    
    
    /**
     * @功能描述 获取HashMap集合,读取putAll写入的数据
     * @param redisKey
     * @return
     */
    public Map<String, T> entries(String redisKey) {
        RedisExceptionUtil.checkKeyException(redisKey);
        return hashOperations.entries(redisKey);
    }
    
    /**
     * 获取当前redis库下所有对象
     * @param redisKey 传入redisKey的名称
     * @return
     */
    public List<T> getAll(String redisKey) {
        RedisExceptionUtil.checkKeyException(redisKey);
        return hashOperations.values(redisKey);
    }

    
    /**
     * 查询查询当前redis库下所有key
     * @param redisKey 传入redisKey的名称
     * @return
     */
    public Set<String> getKeys(String redisKey) {
        RedisExceptionUtil.checkKeyException(redisKey);
        return hashOperations.keys(redisKey);
    }
    
    /**
     * 判断key是否存在redis中
     * @param redisKey 传入redisKey的名称
     * @param key 传入key的名称
     * @return
     */
    public boolean isKeyExists(String redisKey,String key) {
        RedisExceptionUtil.checkKeyException(redisKey,key);
        return hashOperations.hasKey(redisKey, key);
    }
    
    /**
     * 查询当前key下缓存数量
     * @param redisKey 传入redisKey的名称
     * @return
     */
    public long count(String redisKey) {
        return hashOperations.size(redisKey);
    }
    
    /**
     * @param redisKey 传入redisKey的名称
     * 清空redis
     */
    public void empty(String redisKey) {
        RedisExceptionUtil.checkKeyException(redisKey);
        Set<String> set = hashOperations.keys(redisKey);
        set.stream().forEach(key -> hashOperations.delete(redisKey, key));
    }
    
}
