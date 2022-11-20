package com.killstan.bolg.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.killstan.bolg.entity.po.ArticleTag;
import com.killstan.bolg.mapper.ArticleTagMapper;
import com.killstan.bolg.service.ArticleService;
import com.killstan.bolg.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/7/26 7:36
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper,ArticleTag> implements ArticleTagService {


}
