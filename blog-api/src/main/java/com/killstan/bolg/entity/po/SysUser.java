package com.killstan.bolg.entity.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/3/24 10:40
 * @Description:
 */
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 6329291090563107337L;

    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}

