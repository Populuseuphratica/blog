package com.killstan.bolg.controller;

import com.killstan.bolg.entity.vo.ResultVo;
import com.killstan.bolg.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/4/5 15:36
 */
@RestController
@RequestMapping("/logout")
public class LogoutController {

    @Autowired
    private LoginService loginService;

    @GetMapping
    public ResultVo logout(@RequestHeader("Authorization") String token){
        loginService.logout(token);
        return ResultVo.success(null);
    }
}
