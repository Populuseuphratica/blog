package com.killstan.bolg.controller;

import com.killstan.bolg.entity.pojo.LoginParam;
import com.killstan.bolg.entity.vo.ResultVo;
import com.killstan.bolg.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/4/4 14:51
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginParam loginParam){
        return loginService.login(loginParam);
    }

    @PostMapping("/register")
    public ResultVo register(@RequestBody LoginParam loginParam){
        return loginService.register(loginParam);
    }
}
