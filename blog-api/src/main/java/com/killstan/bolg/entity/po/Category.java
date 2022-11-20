package com.killstan.bolg.entity.po;

import lombok.Data;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/6/26 15:37
 */
@Data
public class Category {

    //id，图标路径，图标名称
    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
