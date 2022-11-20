package com.killstan.bolg.service;

import com.killstan.bolg.entity.po.Archive;
import com.killstan.bolg.entity.pojo.PageParams;
import com.killstan.bolg.entity.vo.ArticleVo;
import com.killstan.bolg.entity.vo.ResultVo;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/3/24 15:53
 * @Description:
 */
public interface ArticleService {
    @Transactional()
    ResultVo doPublishArticle(ArticleVo articleVo);

    List<ArticleVo> getArticles(PageParams pageParams);

    List<ArticleVo> getHotArticles();

    List<ArticleVo> getLatestArticles();

    List<Archive> getArchive();

    void updateViewCount(Long id, Integer viewCount);

    ArticleVo getArticleById(Long id);
}
