package com.killstan.bolg.entity.po;

import lombok.Data;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/7/23 15:25
 */
@Data
public class Comment {

    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer level;
}