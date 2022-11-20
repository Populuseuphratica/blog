package com.killstan.bolg.entity.pojo;

import lombok.Data;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/7/24 17:57
 */
@Data
public class CommentParam {

    /** 文章id */
    private Long articleId;

    private String content;

    /** 父评论id */
    private Long parent;

    /** 被评论的用户id */
    private Long toUserId;
}

