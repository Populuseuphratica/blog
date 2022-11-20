package com.killstan.bolg.entity.pojo;

import lombok.Data;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/4/4 14:49
 */
@Data
public class LoginParam {
    private String account;
    private String password;
    private String nickname;
}
