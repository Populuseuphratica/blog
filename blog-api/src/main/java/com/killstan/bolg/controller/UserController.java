package com.killstan.bolg.controller;

import com.killstan.bolg.entity.vo.ErrorCode;
import com.killstan.bolg.entity.vo.LoginUserVo;
import com.killstan.bolg.entity.vo.ResultVo;
import com.killstan.bolg.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/4/5 12:30
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/currentUser")
    public ResultVo getUserInfo(@RequestHeader("Authorization") String token){
        LoginUserVo userByToken = sysUserService.getUserByToken(token);
        if(userByToken == null){
            return ResultVo.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
        }

        return ResultVo.success(userByToken);
    }
}
