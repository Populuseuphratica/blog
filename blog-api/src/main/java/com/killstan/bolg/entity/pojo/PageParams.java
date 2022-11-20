package com.killstan.bolg.entity.pojo;

import lombok.Data;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/3/24 19:27
 * @Description:
 */
@Data
public class PageParams {
    private Integer page;
    private Integer pageSize;
    //id，图标路径，图标名称
    private Long categoryId;
    private Long tagId;

    private String year;
    private String month;

}
