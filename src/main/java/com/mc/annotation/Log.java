package com.mc.annotation;

import java.lang.annotation.*;

/**
 * 日志自定义注解
 * @author cj
 * @date 2018年3月27日
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
@Documented
public @interface Log {
    String logStr() default "";
}
