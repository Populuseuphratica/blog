package com.killstan.bolg.service;

import com.killstan.bolg.entity.pojo.CommentParam;
import com.killstan.bolg.entity.vo.ResultVo;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/7/23 15:31
 * @Description:
 */
public interface CommentService {
    ResultVo doComment(CommentParam commentParam);

    ResultVo getCommentsById(Long articleId);
}
