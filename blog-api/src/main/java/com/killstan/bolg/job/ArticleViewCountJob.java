package com.killstan.bolg.job;

import com.killstan.bolg.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/9/12 19:01
 */
@Component
public class ArticleViewCountJob {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 0 3 * * ?")
    public void UpdateViewCount(){
        Set<String> keys = redisTemplate.keys("article_*");
        for (String key : keys) {
            String[] s = key.split("_");
            long articleId = Long.parseLong(s[1]);
            Integer count = (Integer) redisTemplate.opsForValue().get(key);
            articleService.updateViewCount(articleId,count);
        }
    }

}
