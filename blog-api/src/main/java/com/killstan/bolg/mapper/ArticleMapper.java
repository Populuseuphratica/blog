package com.killstan.bolg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.killstan.bolg.entity.po.Archive;
import com.killstan.bolg.entity.po.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/3/24 12:55
 * @Description:
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    List<Archive> selectArchive();

    IPage<Article> selectArticles(Page<Article> page, Long categoryId, Long tagId, String year, String month);
}
