package com.killstan.bolg.aop;

import java.lang.annotation.*;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/8/4 12:06
 * @Description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BolgCache {

    /** 过期时间 单位s */
    long expire() default 1 * 60 * 60;

    /** 缓存名 */
    String name();

}
