package com.killstan.bolg.controller;

import com.killstan.bolg.aop.BolgCache;
import com.killstan.bolg.entity.vo.ResultVo;
import com.killstan.bolg.entity.vo.TagVo;
import com.killstan.bolg.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/3/25 17:35
 * @Description:
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 获取所有标签
     * @return
     */
    @GetMapping()
    @BolgCache(name = "所有标签")
    public ResultVo getTags(){
        List<TagVo> tags = tagService.getTags();
        return ResultVo.success(tags);
    }

    /**
     * 导航-标签
     * 获取所有标签
     * @return
     */
    @GetMapping("/detail")
    @BolgCache(name = "所有标签")
    public ResultVo getTagDetail(){
        List<TagVo> tags = tagService.getTags();
        return ResultVo.success(tags);
    }

    /**
     * 导航-标签
     * 获取标签
     * @return
     */
    @GetMapping("/detail/{id}")
    public ResultVo getTagDetailById(@PathVariable("id")Long id){
        TagVo tagVo = tagService.getTagById(id);
        return ResultVo.success(tagVo);
    }

    @GetMapping("/hot")
    public ResultVo getHotTags(){
        // 各个最热标签间没有排序
        List<TagVo> hotTags = tagService.getHotTags(5);
        return ResultVo.success(hotTags);
    }
}
