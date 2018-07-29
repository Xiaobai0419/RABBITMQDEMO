package com.mc.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/** 
* @author yf 
* redis List Service 模板类
* 依赖ListOperations接口，并实现部分功能，调用者需要指定具体的T类型。
* 队列正常的操作是 左进右出。
* ListOperations 提供了左进右出和右进左出两种模式。
* 本方法只实现
* 0、获取list总数
* 1、数据左入栈（单个入栈，批量入栈），暂时不提供右出栈（单个出栈，批量出栈）。
* 2、在指定对象前，后插入对象
* 3、根据下标获取对象，根据指定下标写入，移除操作，支持按下标范围获取，删除集合。
* @date 创建时间：2017年12月2日 下午4:30:07 
* @version 1.0   
*/
@Component
public class RedisListService<T> {
    @Autowired
    protected @Qualifier("redisTemplate")RedisTemplate<String, Object> redisTemplate;
    
     
    @Resource
    protected ListOperations<String, T> listOperations;
    
    public Long size(String key){
        return listOperations.size(key);
    }
    
    /**
     * @功能描述 写入
     * @param key
     * @param t
     * @return 返回写入数量
     */
    public Long push(String key,T t){
        RedisExceptionUtil.checkKeyException(key);
        return listOperations.leftPush(key, t);
    }
    
    /**
     * @功能描述 批量写入
     * @param key
     * @param lists
     * @return
     */
    public Long pushAll(String key,List<T> lists){
        RedisExceptionUtil.checkKeyException(key);
        RedisExceptionUtil.checkObjectIsNull(lists);
        return listOperations.leftPushAll(key, lists);
    }
    
    /**
     * @功能描述 在指定对象前插入数据
     * @param key
     * @param pivot
     * @param value
     * @return
     */
    public Long pushBeforObject(String key,T pivot, T value){
        RedisExceptionUtil.checkKeyException(key);
        return listOperations.leftPush(key, pivot,value);
    }
    
    /**
     * @功能描述 在指定对象后插入数据
     * @param key
     * @param pivot
     * @param value
     * @return
     */
    public Long pushAfterObject(String key,T pivot, T value){
        RedisExceptionUtil.checkKeyException(key);
        return listOperations.rightPush(key, pivot, value);
    }
    
    /**
     * @功能描述 根据下标获取对象
     * @param key
     * @param index
     * @return
     */
    public T getByindex(String key,long index){
        RedisExceptionUtil.checkKeyException(key);
        return listOperations.index(key, index);
    }
    
    /**
     * @功能描述 根据下标移除
     * @param key
     * @param index
     */
    public void removeByindex(String key,long index){
        RedisExceptionUtil.checkKeyException(key);
        listOperations.trim(key, index, index);
    }
    
    /**
     * @功能描述 获取集合所有数据
     * @param key
     * @return
     */
    public List<T> getAll(String key){
        RedisExceptionUtil.checkKeyException(key);
        if(size(key)==0){
            return new ArrayList<>();
        }
        return listOperations.range(key, 0, size(key)-1);
    }
    
    /**
     * @功能描述 根据范围获取集合
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<T> getByRange(String key,long start,long end){
        RedisExceptionUtil.checkKeyException(key);
        if(start<0 || this.size(key)< end){
            throw new ArrayIndexOutOfBoundsException();
        }
        return listOperations.range(key, start,end);
    }
    
    /**
     * @功能描述 根据范围移除对象
     * @param key
     * @param start
     * @param end
     */
    public void removeByRange(String key,long start,long end){
        RedisExceptionUtil.checkKeyException(key);
        if(start<0 || this.size(key)< end){
            throw new ArrayIndexOutOfBoundsException();
        }
         listOperations.trim(key, end,this.size(key));
    }
    
    /**
     * @功能描述 删除指定key
     * @param key
     */
    public void removekey(String key){
        RedisExceptionUtil.checkKeyException(key);
        listOperations.getOperations().delete(key);
    }
}
