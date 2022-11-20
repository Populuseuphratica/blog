package com.killstan.bolg.entity.vo;

import lombok.Data;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/4/5 12:30
 */
@Data
public class LoginUserVo {
    //与页面交互

    private Long id;

    private String account;

    private String nickname;

    private String avatar;
}

