package com.killstan.bolg.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/3/24 21:56
 * @Description:
 */
@Data
public class TagVo {

    private Long id;
    private String tagName;
    private String avatar;

}
