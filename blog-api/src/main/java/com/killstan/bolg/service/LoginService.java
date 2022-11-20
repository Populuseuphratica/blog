package com.killstan.bolg.service;

import com.killstan.bolg.entity.pojo.LoginParam;
import com.killstan.bolg.entity.vo.ResultVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/4/4 22:45
 * @Description:
 */
public interface LoginService {
    ResultVo login(LoginParam loginParam);

    void logout(String token);

    @Transactional(rollbackFor = Exception.class)
    ResultVo register(LoginParam loginParam);
}
