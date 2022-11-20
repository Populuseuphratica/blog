package com.killstan.bolg.aop;

import java.lang.annotation.*;

/**
 * @author Kill_Stan
 * @Date: 2022/7/31 20:06
 * @Description: AOP用 日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    /**
     * 当前类别
     * @return
     */
    String module() default "";

    /**
     * 执行的操作
     * @return
     */
    String operation() default "";
}
