package com.killstan.bolg.service.impl;

import com.killstan.bolg.entity.po.Category;
import com.killstan.bolg.entity.vo.CategoryVo;
import com.killstan.bolg.mapper.CategoryMapper;
import com.killstan.bolg.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/6/28 8:20
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo getCategoryById(Long id){
        Category category = categoryMapper.selectById(id);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

    @Override
    public List<CategoryVo> getCategorys(){
        // getCategorys 和 getCategorysDetail同时调用该方法，但getCategorys需要的字段少，可以指定查询其字段提高数据库效率
        List<Category> categories = categoryMapper.selectList(null);

        List<CategoryVo> categorieVos = new ArrayList<>();

        for (Category category : categories) {
            CategoryVo categoryVo = new CategoryVo();
            BeanUtils.copyProperties(category,categoryVo);
            categorieVos.add(categoryVo);
        }

        return categorieVos;
    }
}
