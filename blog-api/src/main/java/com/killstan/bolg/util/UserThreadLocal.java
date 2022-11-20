package com.killstan.bolg.util;

import com.killstan.bolg.entity.po.SysUser;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/6/20 14:20
 */
public class UserThreadLocal {

    /** 线程变量隔离 **/
    private static final ThreadLocal<SysUser> THREADLOCAL = new ThreadLocal<>();

    public static void set(SysUser sysUser){
        THREADLOCAL.set(sysUser);
    }

    public static SysUser get(){
        return THREADLOCAL.get();
    }
    
    public static void remove(){
        THREADLOCAL.remove();
    }



}
