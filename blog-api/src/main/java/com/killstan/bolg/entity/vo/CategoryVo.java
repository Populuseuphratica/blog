package com.killstan.bolg.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/6/26 15:37
 */
@Data
public class CategoryVo {

    //id，图标路径，图标名称
    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
