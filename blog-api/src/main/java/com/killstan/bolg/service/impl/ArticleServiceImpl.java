package com.killstan.bolg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.killstan.bolg.entity.po.*;
import com.killstan.bolg.entity.pojo.PageParams;
import com.killstan.bolg.entity.vo.*;
import com.killstan.bolg.mapper.ArticleBodyMapper;
import com.killstan.bolg.mapper.ArticleMapper;
import com.killstan.bolg.service.*;
import com.killstan.bolg.util.UserThreadLocal;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/3/24 15:53
 * @Description:
 */
@EnableAsync
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Autowired
    private CategoryService categoryService;

//    @Autowired
//    private AsyncService asyncService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发布文章
     *
     * @param articleVo
     * @return
     */
    @Transactional()
    @Override
    public ResultVo doPublishArticle(ArticleVo articleVo) {

        // 更新Article表
        Article article = new Article();
        article.setTitle(articleVo.getTitle());
        article.setSummary(articleVo.getSummary());
        article.setCommentCounts(0);
        article.setViewCounts(0);

        // 作者id
        SysUser sysUser = UserThreadLocal.get();
        article.setAuthorId(sysUser.getId());

        CategoryVo categoryVo = articleVo.getCategory();
        article.setCategoryId(categoryVo.getId());

        // 设置普通权重
        article.setWeight(Article.Article_Common);

        article.setCreateDate(System.currentTimeMillis());

        // 插入后会自动将自增id返回到article中
        int insert = articleMapper.insert(article);
        if (insert != 1) {
            return ResultVo.fail(ErrorCode.DATABASE_ERROR.getCode(), ErrorCode.DATABASE_ERROR.getMsg());
        }

        // 设置标签 article_tag 表
        List<TagVo> tags = articleVo.getTags();
        List<ArticleTag> articleTags = new ArrayList<>();
        for (TagVo tag : tags) {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(article.getId());
            articleTag.setTagId(tag.getId());
            articleTags.add(articleTag);
        }
        articleTagService.saveBatch(articleTags);

        // 设置文章内容 article_body 表
        ArticleBodyVo bodyVo = articleVo.getBody();
        ArticleBody articleBody = new ArticleBody();
        BeanUtils.copyProperties(bodyVo, articleBody);
        articleBody.setArticleId(article.getId());
        articleBodyMapper.insert(articleBody);

        // 更新 article 表中的 body_id
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        ArticleVo articleVoReturn = new ArticleVo();
        articleVoReturn.setId(article.getId());
        return ResultVo.success(articleVoReturn);
    }

    /**
     * @throws
     * @title 获取首页文章
     * @description
     */
    @Override
    public List<ArticleVo> getArticles(PageParams pageParams) {

        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        // 因为导航栏中首页、文章分类、标签都需要获取文章列表，所以根据参数不同得到不同数据。
        IPage<Article> articleIPage = articleMapper.selectArticles(page, pageParams.getCategoryId(), pageParams.getTagId(), pageParams.getYear(), pageParams.getMonth());

        List<Article> records = articleIPage.getRecords();
        // 要返回我们定义的vo数据，就是对应的前端数据，不应该只返回现在的数据需要进一步进行处理
        List<ArticleVo> articleVoList = copyToArticleVoList(records, true, true);

        return articleVoList;
    }

    /**
     * @throws
     * @title 取得最热文章
     * @description
     * @author admin
     * @updateTime
     */
    @Override
    public List<ArticleVo> getHotArticles() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("view_counts");
        queryWrapper.last("limit 5");
        List<Article> articles = articleMapper.selectList(queryWrapper);

        List<ArticleVo> articleVoList = copyToArticleVoList(articles, false, false);
        return articleVoList;
    }

    /**
     * @throws
     * @title 取得最热文章
     * @description
     * @author admin
     * @updateTime
     */
    @Override
    public List<ArticleVo> getLatestArticles() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_date");
        queryWrapper.last("limit 5");
        List<Article> articles = articleMapper.selectList(queryWrapper);

        List<ArticleVo> articleVoList = copyToArticleVoList(articles, false, false);
        return articleVoList;
    }

    /**
     * @throws
     * @title 获取文章归档
     * @description
     * @author admin
     * @updateTime
     */
    @Override
    public List<Archive> getArchive() {
        List<Archive> archives = articleMapper.selectArchive();
        return archives;
    }

    /**
     * 更新 viewCount
     *
     * @param id
     * @param viewCount
     */
    @Override
    public void updateViewCount(Long id, Integer viewCount) {
        // 获取文章
        Article article = articleMapper.selectById(id);

        // 更新viewCount
        if (article != null) {
            Integer viewCounts = article.getViewCounts();
            if (!viewCount.equals(viewCounts)) {
                article.setViewCounts(viewCount);
                articleMapper.updateById(article);
            }
        }
    }

    /**
     * 获取文章详情
     *
     * @param id
     * @return
     */
    @Override
    public ArticleVo getArticleById(Long id) {
        // 获取文章详情
        Article article = articleMapper.selectById(id);

        if (article == null) {
            return null;
        }

        // redis 缓存文章阅读量
        String key = "article_" + id;
        if (redisTemplate.hasKey(key)) {
            // redis 中存在该文章阅读量时，redis中 +1后赋值给article
            Long increment = redisTemplate.opsForValue().increment(key, 1L);
            article.setViewCounts(increment.intValue());
        } else {
            Integer viewCounts = article.getViewCounts();
            if (viewCounts == null) {
                viewCounts = 1;
            }
            redisTemplate.opsForValue().increment(key, viewCounts.longValue());
            article.setViewCounts(viewCounts + 1);
        }


//        // 线程池异步更新文章数量
//        asyncService.updateArticleViewCount(articleMapper, article);

        // 返回文章
        ArticleVo articleVo = copyToArticleVo(article, true, true, true, true);
        return articleVo;
    }

    private ArticleVo copyToArticleVo(Article article, boolean tagNeed, boolean authorNeed) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);

        // 时间为 bigint 型，利用 joda-time 进行转换
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

        if (tagNeed) {
            List<TagVo> tagsByArticleId = tagService.getTagsByArticleId(article.getId());
            articleVo.setTags(tagsByArticleId);
        }

        if (authorNeed) {
            SysUser sysUserById = sysUserService.getSysUserById(article.getAuthorId());
            articleVo.setAuthor(sysUserById.getNickname());
        }

        return articleVo;
    }

    private ArticleVo copyToArticleVo(Article article, boolean tagNeed, boolean authorNeed, boolean isArticleBody, boolean isCategory) {
        ArticleVo articleVo = copyToArticleVo(article, tagNeed, authorNeed);

        if (isArticleBody) {
            LambdaQueryWrapper<ArticleBody> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(ArticleBody::getArticleId, article.getId());
            lambdaQueryWrapper.last("limit 1");
            ArticleBody articleBody = articleBodyMapper.selectOne(lambdaQueryWrapper);
            ArticleBodyVo articleBodyVo = new ArticleBodyVo();
            articleBodyVo.setContent(articleBody.getContentHtml());
            articleVo.setBody(articleBodyVo);
        }

        if (isCategory) {
            CategoryVo categoryVo = categoryService.getCategoryById(article.getCategoryId());
            articleVo.setCategory(categoryVo);
        }

        return articleVo;
    }

    private List<ArticleVo> copyToArticleVoList(List<Article> articleList, boolean tagNeed, boolean authorNeed) {
        List<ArticleVo> articleVoList = new ArrayList<>();

        for (Article article : articleList) {
            ArticleVo articleVo = copyToArticleVo(article, tagNeed, authorNeed);
            articleVoList.add(articleVo);
        }

        return articleVoList;
    }

}
