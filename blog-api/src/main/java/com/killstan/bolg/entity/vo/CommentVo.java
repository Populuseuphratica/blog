package com.killstan.bolg.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/7/23 15:42
 */
@Data
public class CommentVo  {

    // 防止前端精度损失
    private Long id;

    /**
     * 评论者
     */
    private UserVo author;

    private String content;

    private List<CommentVo> childrens;

    private String createDate;

    private Integer level;

    /**
     * 被评论者
     */
    private UserVo toUser;
}

