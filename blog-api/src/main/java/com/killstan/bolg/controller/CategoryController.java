package com.killstan.bolg.controller;

import com.killstan.bolg.aop.BolgCache;
import com.killstan.bolg.entity.po.Category;
import com.killstan.bolg.entity.vo.CategoryVo;
import com.killstan.bolg.entity.vo.ResultVo;
import com.killstan.bolg.mapper.CategoryMapper;
import com.killstan.bolg.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/7/25 11:22
 */
@RestController
@RequestMapping("/categorys")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    /**
     * 获取文章分类
     * @return
     */
    @GetMapping
    @BolgCache(name = "所有文章分类")
    public ResultVo getCategorys(){
        List<CategoryVo> categories = categoryService.getCategorys();
        return ResultVo.success(categories);
    }

    /**
     * 导航-文章分类
     * 查询所有的文章分类
     * @return
     */
    @GetMapping("/detail")
    @BolgCache(name = "所有文章分类")
    public ResultVo getCategorysDetail(){
        List<CategoryVo> categories = categoryService.getCategorys();
        return ResultVo.success(categories);
    }

    /**
     * 导航-文章分类
     * 查询所有的文章分类
     * @return
     */
    @GetMapping("/detail/{id}")
    public ResultVo getCategoryDetailById(@PathVariable("id") Long id){
        CategoryVo categorie = categoryService.getCategoryById(id);
        return ResultVo.success(categorie);
    }

}
