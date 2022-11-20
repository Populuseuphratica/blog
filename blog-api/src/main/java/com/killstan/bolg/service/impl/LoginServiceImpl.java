package com.killstan.bolg.service.impl;

import com.alibaba.fastjson.JSON;
import com.killstan.bolg.entity.po.SysUser;
import com.killstan.bolg.entity.pojo.LoginParam;
import com.killstan.bolg.entity.vo.ErrorCode;
import com.killstan.bolg.entity.vo.ResultVo;
import com.killstan.bolg.service.LoginService;
import com.killstan.bolg.service.SysUserService;
import com.killstan.bolg.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/4/4 19:26
 */
@Service
public class LoginServiceImpl implements LoginService {

    //加密盐用于加密
    private static final String SLAT = "mszlu!@#";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     *
     * @param loginParam
     * @return
     */
    @Override
    public ResultVo login(LoginParam loginParam) {

        if (loginParam == null || !StringUtils.hasLength(loginParam.getAccount()) || !StringUtils.hasLength(loginParam.getPassword())) {
            return ResultVo.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        String password = DigestUtils.md5DigestAsHex((loginParam.getPassword() + SLAT).getBytes(StandardCharsets.UTF_8));
        loginParam.setPassword(password);
        SysUser sysUser = sysUserService.getSysUserByLoginParam(loginParam);
        if (sysUser == null) {
            return ResultVo.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = getTokenByUserId(sysUser);
        return ResultVo.success(token);
    }

    /**
     * 退出登录
     *
     * @param token
     */
    @Override
    public void logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
    }

    /**
     * 注册用户
     *
     * @param loginParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVo register(LoginParam loginParam) {
        if (!StringUtils.hasText(loginParam.getAccount()) && !StringUtils.hasText(loginParam.getPassword())) {
            return ResultVo.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        SysUser sysUserByAccount = sysUserService.getSysUserByAccount(loginParam.getAccount());
        if (sysUserByAccount == null) {
            return ResultVo.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        SysUser sysUser = new SysUser();
        sysUser.setAccount(loginParam.getAccount());
        sysUser.setAdmin(0);

        Long time = System.currentTimeMillis();
        sysUser.setCreateDate(time);
        sysUser.setLastLogin(time);
        sysUser.setNickname(loginParam.getNickname());

        String password = DigestUtils.md5DigestAsHex((loginParam.getPassword() + SLAT).getBytes(StandardCharsets.UTF_8));
        sysUser.setPassword(password);
        sysUser.setSalt(SLAT);

        sysUserService.saveUser(sysUser);

        String token = getTokenByUserId(sysUser);
        return ResultVo.success(token);
    }

    /**
     * 根据用户id取得token，并将用户对象存入redis中。
     * @param sysUser
     * @return
     */
    private String getTokenByUserId(SysUser sysUser){
        Long id = sysUser.getId();
        String token = JWTUtils.createToken(id);
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 3, TimeUnit.HOURS);
        return token;
    }


}
