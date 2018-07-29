package com.mc.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/** 
* @author yf 
* redis set service
* Set集合的增删改
* 交集，并集，差集
* @date 创建时间：2017年12月6日 上午11:20:32 
* @version 1.0   
*/
@Component
public class RedisSetService<T> {

    @Autowired
    protected @Qualifier("redisTemplate")RedisTemplate<String, Object> redisTemplate;
    
    @Resource
    protected SetOperations<String, T> setOperations;
    
    
    /**
     * @功能描述 获取集合长度
     * @param key
     * @return
     */
    public Long size(String key){
      //为空抛出异常
        RedisExceptionUtil.checkKeyException(key);
        return setOperations.size(key);
    }
    
    /**
     * @功能描述 新增
     * @param key
     * @param values  可变长参数
     */
    @SuppressWarnings("unchecked")
    public void add(String key, T... values){
        //为空抛出异常
        RedisExceptionUtil.checkKeyException(key);
        setOperations.add(key, values);
    }
    
    /**
     * @功能描述 根据key值获取集合
     * @param key
     * @return
     */
    public Set<T> members(String key){
        RedisExceptionUtil.checkKeyException(key);
        return setOperations.members(key);
    }
    
    /**
     * @功能描述 从集合中移除指定对象
     * @param key
     * @param values 可变长参数
     * @return
     */
    @SuppressWarnings("unchecked")
    public Long remove(String key, T... values){
        //为空抛出异常
        RedisExceptionUtil.checkKeyException(key);
        return setOperations.remove(key, values);
    }
    
    /**
     * @功能描述 检查集合中是否存在value对象
     * @param key
     * @param value
     * @return
     */
    public Boolean isMember(String key, T value){
        //为空抛出异常
        RedisExceptionUtil.checkKeyException(key);
        return setOperations.isMember(key, value);
    }
    
    /**
     * @功能描述 取两个集合的交集 两个key值不能为空
     * @param key
     * @param otherKey
     * @return
     */
    public Set<T> intersect(String key,String otherKey){
        //为空抛出异常
        RedisExceptionUtil.checkKeyException(key,otherKey);
        return setOperations.intersect(key, otherKey);
    }
    
    /**
     * @功能描述 获取多个集合的交集
     * @param key
     * @param otherKey
     * @return
     */
    public Set<T> intersect(String key,List<String> otherKeys){
        //为空抛出异常
        RedisExceptionUtil.checkKeyException(key);
        RedisExceptionUtil.checkObjectIsNull(otherKeys);
        otherKeys.stream().forEach(o->RedisExceptionUtil.checkKeyException(o));
        return setOperations.intersect(key, otherKeys);
    }
    
    /**
     * @功能描述 取两个集合的并集
     * @param key
     * @param otherKey
     * @return
     */
    public Set<T> union(String key,String otherKey){
        //为空抛出异常
        RedisExceptionUtil.checkKeyException(key,otherKey);
        return setOperations.union(key, otherKey);
    }
    
    /**
     * @功能描述 取多个集合的并集
     * @param key
     * @param otherKeys
     * @return
     */
    public Set<T> union(String key,List<String> otherKeys){
        //为空抛出异常
        RedisExceptionUtil.checkKeyException(key);
        RedisExceptionUtil.checkObjectIsNull(otherKeys);
        otherKeys.stream().forEach(o->RedisExceptionUtil.checkKeyException(o));
        return setOperations.union(key, otherKeys);
    }
    
    /**
     * @功能描述 取两个集合的差集
     * @param key
     * @param otherKey
     * @return
     */
    public Set<T> difference(String key,String otherKey){
        //为空抛出异常
        RedisExceptionUtil.checkKeyException(key,otherKey);
        return setOperations.difference(key, otherKey);
    }
    
    /**
     * @功能描述 取多个集合的差集
     * @param key
     * @param otherKeys
     * @return
     */
    public Set<T> difference(String key,List<String> otherKeys){
        //为空抛出异常
        RedisExceptionUtil.checkKeyException(key);
        RedisExceptionUtil.checkObjectIsNull(otherKeys);
        otherKeys.stream().forEach(o->RedisExceptionUtil.checkKeyException(o));
        return setOperations.difference(key, otherKeys);
    }
    
    /**
     * @功能描述 删除指定key
     * @param key
     */
    public void remove(String key){
        RedisExceptionUtil.checkKeyException(key);
        setOperations.getOperations().delete(key);
    }
}
