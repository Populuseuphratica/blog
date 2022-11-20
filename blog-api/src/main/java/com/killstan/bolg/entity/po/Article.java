package com.killstan.bolg.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/3/24 10:39
 * @Description:
 */
@Data
public class Article {

    /** 置顶权重 */
    public static final int Article_TOP = 1;
    /** 普通权重 */
    public static final int Article_Common = 0;

    //TODO Longl类型传入前台会四舍五入失真
    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;

    /**
     * 置顶
     */
    private Integer weight;


    /**
     * 创建时间
     */
    private Long createDate;
}

