package com.killstan.bolg.service;

import com.killstan.bolg.entity.vo.TagVo;

import java.util.List;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/3/24 22:23
 * @Description:
 */
public interface TagService {
    /**
     * 取得所有标签
     * @return
     */
    List<TagVo> getTags();

    TagVo getTagById(Long id);

    List<TagVo> getTagsByArticleId(Long id);

    List<TagVo> getHotTags(int limit);
}
