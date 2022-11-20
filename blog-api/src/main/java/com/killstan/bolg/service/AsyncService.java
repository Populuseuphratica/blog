package com.killstan.bolg.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.killstan.bolg.entity.po.Article;
import com.killstan.bolg.mapper.ArticleMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/6/28 18:37
 */
//@Service
public class AsyncService {

    /**
     * 此操作在线程池执行不会影响原有主线程
     */
    @Async("asyncPoolTaskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        Article articleUpdate = new Article();
        Integer viewCounts = article.getViewCounts();
        articleUpdate.setViewCounts(viewCounts + 1);

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //根据id更新
        queryWrapper.eq(Article::getId, article.getId());
        //设置一个为了在多线程的环境下线程安全
        //改之前再确认这个值有没有被其他线程抢先修改，类似于CAS操作 cas加自旋，加个循环就是cas（乐观锁）
        queryWrapper.eq(Article::getViewCounts, viewCounts);

        articleMapper.update(articleUpdate, queryWrapper);
    }
}
