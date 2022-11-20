package com.killstan.bolg.controller;

import com.killstan.bolg.entity.pojo.CommentParam;
import com.killstan.bolg.entity.vo.ResultVo;
import com.killstan.bolg.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/7/23 15:29
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 根据文章 id 取得该文章的评论
     * @param id
     * @return
     */
    @GetMapping("/article/{id}")
    public ResultVo getCommentById(@PathVariable("id")Long id){
        ResultVo commentsById = commentService.getCommentsById(id);
        return commentsById;
    }

    // TODO 前端传参数不匹配
    @PostMapping("/create/change")
    public ResultVo doComment(@RequestBody CommentParam commentParam){
        ResultVo resultVo = commentService.doComment(commentParam);
        return resultVo;
    }

}
