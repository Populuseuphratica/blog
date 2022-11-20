package com.killstan.bolg.service;

import com.killstan.bolg.entity.po.SysUser;
import com.killstan.bolg.entity.pojo.LoginParam;
import com.killstan.bolg.entity.vo.LoginUserVo;
import com.killstan.bolg.entity.vo.TagVo;

import java.util.List;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/3/24 22:23
 * @Description:
 */
public interface SysUserService {

     SysUser getSysUserById(Long id);

    int saveUser(SysUser sysUser);

    SysUser getSysUserByLoginParam(LoginParam loginParam);

    SysUser getSysUserByAccount(String account);

    LoginUserVo getUserByToken(String token);

    SysUser checkToken(String token);
}
