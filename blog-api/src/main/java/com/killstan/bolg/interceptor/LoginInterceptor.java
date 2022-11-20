package com.killstan.bolg.interceptor;

import com.alibaba.fastjson.JSON;
import com.killstan.bolg.entity.po.SysUser;
import com.killstan.bolg.entity.vo.ErrorCode;
import com.killstan.bolg.entity.vo.ResultVo;
import com.killstan.bolg.service.SysUserService;
import com.killstan.bolg.util.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/6/14 21:34
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 访问静态资源以外的路径时，登录检查
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 如果不是 HandlerMethod ，放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String requestURI = request.getRequestURI();
        String token = request.getHeader("Authorization");

        log.info("=================request start===========================");
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        SysUser sysUser = sysUserService.checkToken(token);
        // 未登录，返回错误信息
        if (sysUser == null) {
            response.setContentType("application/json;charset=utf-8");
            ResultVo resultVo = ResultVo.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
            response.getWriter().write(JSON.toJSONString(resultVo));
            return false;
        }

        UserThreadLocal.set(sysUser);

        // 登录，放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //　防止内存泄露
        UserThreadLocal.remove();
    }
}
