package com.mc.redis;

import org.springframework.util.StringUtils;

/** 
* @author yf
* redis 异常检测工具类，主要校验调用spring-boot-starter-data-redis提供的接口参数合法性
* @date 创建时间：2017年12月7日 上午11:14:39 
* @version 1.0   
*/
public class RedisExceptionUtil {
    /**
     * @功能描述 检测keys值为空的情况。keys为不定长参数
     * @param keys
     */
    public static void checkKeyException(String ...keys){
        for(String key : keys){
            //为空抛出异常
            if(StringUtils.isEmpty(key)){
                throw new NullPointerException("redis->key不能为空 ");
            }
        }
    }
    
    /**
     * @功能描述 检测传入对象是否为null
     * @param obj
     */
    public static void checkObjectIsNull(Object obj){
        if(obj == null){
            throw new NullPointerException("对象不能为空 ");
        }
    }
}
