package com.killstan.bolg.service.impl;

import com.killstan.bolg.entity.po.Tag;
import com.killstan.bolg.entity.vo.TagVo;
import com.killstan.bolg.mapper.SysUserMapper;
import com.killstan.bolg.mapper.TagMapper;
import com.killstan.bolg.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/3/24 22:24
 * @Description:
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> getTags(){
        List<Tag> tags = tagMapper.selectList(null);
        return copy(tags);
    }

    @Override
    public TagVo getTagById(Long id){
        Tag tag = tagMapper.selectById(id);
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }

    @Override
    /**
     * 根据文章 ID 取得标签
     */
    public List<TagVo> getTagsByArticleId(Long id){
        List<Tag> tags = tagMapper.selectListByArticleId(id);
        return copy(tags);
    }

    @Override
    /**
     * 最热标签
     */
    public List<TagVo> getHotTags(int limit){
        List<Tag> tags = tagMapper.selectHotTags(limit);
        return copy(tags);
    }

    private List<TagVo> copy(List<Tag> tags){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tags) {
            TagVo tagVo = new TagVo();
            BeanUtils.copyProperties(tag,tagVo);
            tagVoList.add(tagVo);
        }

        return tagVoList;

    }
}
