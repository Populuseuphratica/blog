package com.killstan.bolg.controller;

import com.killstan.bolg.aop.BolgCache;
import com.killstan.bolg.aop.LogAnnotation;
import com.killstan.bolg.entity.po.Archive;
import com.killstan.bolg.entity.po.Article;
import com.killstan.bolg.entity.pojo.PageParams;
import com.killstan.bolg.entity.vo.ArticleVo;
import com.killstan.bolg.entity.vo.ResultVo;
import com.killstan.bolg.mapper.ArticleMapper;
import com.killstan.bolg.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/3/24 10:41
 * @Description:
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping()
    @LogAnnotation(module = "文章",operation = "获取文章列表")
    @BolgCache(name = "getArticles")
    public ResultVo getArticles(@RequestBody PageParams pageParams) {
        List<ArticleVo> articles = articleService.getArticles(pageParams);
        return ResultVo.success(articles);
    }

    @PostMapping("/hot")
    public ResultVo getHotArticles(){
        List<ArticleVo> hotArticles = articleService.getHotArticles();
        return ResultVo.success(hotArticles);
    }

    @PostMapping("/new")
    public ResultVo getLatestArticles(){
        List<ArticleVo> hotArticles = articleService.getLatestArticles();
        return ResultVo.success(hotArticles);
    }

    /**
     * 发布文章
     *
     * @param articleVo
     * @return articleId
     */
    @PostMapping("/publish")
    public ResultVo publishArticle(@RequestBody ArticleVo articleVo){
        ResultVo resultVo = articleService.doPublishArticle(articleVo);
        return resultVo;
    }

    /**
     * @title 首页-文章档案
     * @description 每一篇文章根据创建时间某年某月发表多少篇文章
     * @author admin
     * @updateTime
     * @throws
     */
    @PostMapping("/listArchives")
    public ResultVo getListArchives(){
        List<Archive> archive = articleService.getArchive();
        return ResultVo.success(archive);
    }

    @PostMapping("/view/{id}")
    public ResultVo getArticle(@PathVariable("id")Long id){
        ArticleVo articleVo = articleService.getArticleById(id);

        // 线程池异步更新文章数量

        return ResultVo.success(articleVo);
    }
}
