package com.killstan.bolg.aop;

import com.alibaba.fastjson.JSON;
import com.killstan.bolg.entity.vo.ResultVo;
import com.killstan.bolg.util.LogConstant;
import lombok.extern.slf4j.Slf4j;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/8/4 15:50
 */
@Aspect
@Component
@Slf4j
public class BolgCacheAspect implements LogConstant {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("@annotation(com.killstan.bolg.aop.BolgCache)")
    public void point() {};

    @Around("point()")
    public Object doAround(ProceedingJoinPoint joinpoint) {

        Signature signature = joinpoint.getSignature();
        // 获取被代理方法名
        String methodName = signature.getName();

        // 获取被代理方法参数
        Object[] args = joinpoint.getArgs();

        Class[] paramTypes = new Class[args.length];
        StringBuilder paramSb = new StringBuilder(args.length);
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg != null) {
                paramSb.append(JSON.toJSONString(arg));
                paramTypes[i] = arg.getClass();
            } else {
                paramTypes[i] = null;
            }
        }

        try {
            // 获取被代理方法
            Class declaringType = signature.getDeclaringType();
            Method method = declaringType.getMethod(methodName, paramTypes);

            // 获取被代理方法的 BolgCache 注解
            BolgCache bolgCache = method.getAnnotation(BolgCache.class);

            // 获取 缓存名称
            String bolgCacheName = bolgCache.name();
            //缓存过期时间
            long expire = bolgCache.expire();

            //　根据缓存名和方法的参数为 key 设置缓存
            // 采用 MD5 加密，减少数据量和长度
            String redisKey = DigestUtils.md5DigestAsHex((bolgCacheName + paramSb.toString()).getBytes("UTF-8"));

            // 从 redis 获取缓存值
            ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
            String redisResult = stringStringValueOperations.get(redisKey);
            if (StringUtils.hasLength(redisResult)) {
                log.info(LOG_DASH + "方法 " + methodName + " 开始获取缓存" + LOG_DASH);
                log.info("参数为 " + paramSb.toString());
                ResultVo resultVo = JSON.parseObject(redisResult, ResultVo.class);
                log.info(LOG_DASH + "方法 " + methodName + " 成功获取缓存" + LOG_DASH);
                return resultVo;
            } else {
                log.info(LOG_DASH + "方法 " + methodName + " 执行开始" + LOG_DASH);
                Object returnValue = joinpoint.proceed();
                log.info(LOG_DASH + "方法 " + methodName + " 执行结束" + LOG_DASH);
                log.info(LOG_DASH + "方法 " + methodName + " 开始存入缓存" + LOG_DASH);
                stringStringValueOperations.set(redisKey, JSON.toJSONString(returnValue), expire, TimeUnit.SECONDS);
                log.info(LOG_DASH + "方法 " + methodName + " 成功存入缓存" + LOG_DASH);
                return returnValue;
            }
        } catch (NoSuchMethodException e) {
            log.error(LOG_DASH + "方法 " + methodName + " 执行异常" + LOG_DASH);
            log.error("异常信息为：" + e.getMessage());
            e.printStackTrace();
        } catch (Throwable throwable) {
            log.error(LOG_DASH + "方法 " + methodName + " 执行异常" + LOG_DASH);
            log.error("异常信息为：" + throwable.getMessage());
            throwable.printStackTrace();
        }
        return null;
    }

}
