package com.mc.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/** 
* @author yf 
* redis Object Service 模板类
* 依赖ValueOperations接口，并实现部分功能，调用者需要指定具体的T类型。
* 主要用于存储简单的 <k,v>对象。如需增加其他操作，请自行补全此工具类（多key值操作,getAndSet,bit操作,操作字符串指定坐标内容）
* 提供单key值操作
* string append
* int提供redis原子操作
* @date 创建时间：2017年12月5日 上午11:01:00 
* @version 1.0   
*/
@Component
public class RedisObjectService<T> {

    @Autowired
    protected @Qualifier("redisTemplate")RedisTemplate<String, Object> redisTemplate;

    @Autowired
    protected @Qualifier("redisTemplateDb1") RedisTemplate<String, Object> redisTemplateDb1;
    
    @Resource
    protected @Qualifier("valueOperations")ValueOperations<String, T> valueOperations;

    @Resource
    protected @Qualifier("valueOperationsDB1")ValueOperations<String, T> valueOperationsDB1;
    
    
    /**
     * @功能描述 基本插入操作
     * @param key
     * @param obj
     */
    public void set(String key,T obj){
        RedisExceptionUtil.checkKeyException(key);
        valueOperations.set(key, obj);
    }

    public void setDB1(String key,T obj){
        RedisExceptionUtil.checkKeyException(key);
        valueOperationsDB1.set(key, obj);
    }
    
    /**
     * @功能描述
     * @param key
     * @param obj
     * @param timeout 过期时间
     * @param unit    时间类型TimeUnit 枚举类型
     */
    public void set(String key,T obj,long timeout, TimeUnit unit){
        RedisExceptionUtil.checkKeyException(key);
        RedisExceptionUtil.checkObjectIsNull(unit);
        //如果时间传入时间小于0。抛出不支持操作异常。
        if(timeout<0){
            throw new UnsupportedOperationException(); 
        }
        valueOperations.set(key, obj,timeout,unit);
    }


    /**
     * @功能描述 根据key值获取指定对象
     * @param key
     * @return
     */
    public T get(String key){
        RedisExceptionUtil.checkKeyException(key);
        return valueOperations.get(key);
    }
    public T getDB1(String key){
        RedisExceptionUtil.checkKeyException(key);
        return valueOperationsDB1.get(key);
    }
    
    /**
     * @功能描述 给指定字符串后继续添加字符
     * 因为需要判断T是否为String所以需要强制传入实际的T类型 （String.class）。
     * 工具类GenericsUtils.getSuperClassGenricType只能通过子类获取父类模板的T类型。所以选择这种主动传入类类型模式
     * @param key
     * @param value
     * @param clazz
     */
    public void stringAppend(String key,String value,Class<T> clazz){
        RedisExceptionUtil.checkKeyException(key);
        //如果时间传入类型不为String。抛出不支持操作异常。
        if(!clazz.getName().equals("java.lang.String")){
            throw new UnsupportedOperationException(); 
        }
        valueOperations.append(key, value);
    }
    
    /**
     * @功能描述 redis 自增操作 
     * @param key
     * @param value
     * @return
     */
    public Long LongIncrement(String key,Long value){
        //synchronized(RedisObjectService.class){
            RedisExceptionUtil.checkKeyException(key);
            return valueOperations.increment(key, value);
        //}
    }
    
    /**
     * @功能描述 redis 自增操作
     * @param key
     * @param value
     * @return
     */
    public Double DoubleIncrement(String key,Double value){
        //synchronized(RedisObjectService.class){
            RedisExceptionUtil.checkKeyException(key);
            return valueOperations.increment(key, value);
        //}
    }
    
    /**
     * @功能描述 删除指定key
     * @param key
     */
    public void remove(String key){
        RedisExceptionUtil.checkKeyException(key);
        valueOperations.getOperations().delete(key);
    }
    public void removeDB1(String key){
        RedisExceptionUtil.checkKeyException(key);
        valueOperationsDB1.getOperations().delete(key);
    }

    /**
     * @功能描述 判断key是否存在
     * @param key
     * @return
     */
    public boolean exist(String key){
        RedisExceptionUtil.checkKeyException(key);
        if(get(key) != null){
            return true;
        }
        return false;
    }
    public boolean existDB1(String key){
        RedisExceptionUtil.checkKeyException(key);
        if(getDB1(key) != null){
            return true;
        }
        return false;
    }
}
