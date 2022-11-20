package com.killstan.bolg.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.killstan.bolg.entity.po.SysUser;
import com.killstan.bolg.entity.po.Tag;
import com.killstan.bolg.entity.pojo.LoginParam;
import com.killstan.bolg.entity.vo.LoginUserVo;
import com.killstan.bolg.entity.vo.TagVo;
import com.killstan.bolg.mapper.SysUserMapper;
import com.killstan.bolg.service.SysUserService;
import com.killstan.bolg.util.JWTUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/3/24 22:23
 * @Description:
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getSysUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        return sysUser;
    }

    /**
     * 保存用户
     * @param sysUser
     * @return
     */
    @Override
    public int saveUser(SysUser sysUser){
        //保存用户这 id会自动生成
        //mybatis-plus  默认生成的id是 分布式id 雪花算法
        return sysUserMapper.insert(sysUser);
    }

    /**
     * 根据登录名和密码获取用户对象
     *
     * @param loginParam
     * @return
     */
    @Override
    public SysUser getSysUserByLoginParam(LoginParam loginParam) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
        queryWrapper.eq("account", loginParam.getAccount());
        queryWrapper.eq("password", loginParam.getPassword());
        queryWrapper.last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        return sysUser;
    }

    /**
     * 根据用户名获取用户对象
     * @param account
     * @return
     */
    @Override
    public SysUser getSysUserByAccount(String account) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
        queryWrapper.eq("account", account);
        queryWrapper.last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        return sysUser;
    }

    @Override
    public LoginUserVo getUserByToken(String token) {
        SysUser sysUser = checkToken(token);
        if (sysUser == null) {
            return null;
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        BeanUtils.copyProperties(sysUser, loginUserVo);

        return loginUserVo;
    }

    /**
     * 检验 redis 中是否存在该 token 的用户
     * @param token
     * @return
     */
    @Override
    public SysUser checkToken(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }

        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (userJson == null) {
            return null;
        }
        //解析回sysUser对象
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;

    }


}
