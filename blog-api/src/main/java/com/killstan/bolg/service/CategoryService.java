package com.killstan.bolg.service;

import com.killstan.bolg.entity.vo.CategoryVo;

import java.util.List;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/6/28 08:28
 * @Description:
 */
public interface CategoryService {
    CategoryVo getCategoryById(Long id);


    /**
     * 取得所有类别
     * @return
     */
    List<CategoryVo> getCategorys();
}
