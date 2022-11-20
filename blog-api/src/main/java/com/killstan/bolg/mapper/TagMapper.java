package com.killstan.bolg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.killstan.bolg.entity.po.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/3/24 22:24
 * @Description:
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> selectListByArticleId(Long id);
    List<Tag> selectHotTags(int limit);
}
