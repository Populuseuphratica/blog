package com.killstan.bolg.entity.po;

import lombok.Data;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/7/25 20:58
 */
@Data
public class ArticleTag {

    private Long id;

    private Long articleId;

    private Long tagId;

}
