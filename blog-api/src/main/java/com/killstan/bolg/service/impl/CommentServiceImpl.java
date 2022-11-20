package com.killstan.bolg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.killstan.bolg.entity.po.Comment;
import com.killstan.bolg.entity.po.SysUser;
import com.killstan.bolg.entity.pojo.CommentParam;
import com.killstan.bolg.entity.vo.CommentVo;
import com.killstan.bolg.entity.vo.ErrorCode;
import com.killstan.bolg.entity.vo.ResultVo;
import com.killstan.bolg.entity.vo.UserVo;
import com.killstan.bolg.mapper.CommentMapper;
import com.killstan.bolg.service.CommentService;
import com.killstan.bolg.service.SysUserService;
import com.killstan.bolg.util.UserThreadLocal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/7/23 15:31
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 评论
     * @param commentParam
     * @return
     */
    @Override
    public ResultVo doComment(CommentParam commentParam){
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setContent(commentParam.getContent());
        SysUser sysUser = UserThreadLocal.get();
        comment.setAuthorId(sysUser.getId());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        //如果是空，parent就是0
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);

        int insert = commentMapper.insert(comment);
        if(insert==1){
            return ResultVo.success(null);
        }else{
            return ResultVo.fail(ErrorCode.DATABASE_ERROR.getCode(),ErrorCode.DATABASE_ERROR.getMsg());
        }
    }

    /**
     * 获取该文章的评论
     *
     * @param articleId 文章id
     * @return
     */
    @Override
    public ResultVo getCommentsById(Long articleId) {

        // 取得文章评论
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getArticleId, articleId);
        // 只查询一级评论
        lambdaQueryWrapper.eq(Comment::getLevel, 1);
        lambdaQueryWrapper.orderByDesc(Comment::getCreateDate);
        List<Comment> comments = commentMapper.selectList(lambdaQueryWrapper);

        if (comments == null) {
            return ResultVo.success(null);
        }

        List<CommentVo> commentVos = new ArrayList<>();

        for (Comment comment : comments) {
            CommentVo commentVo = copyComment(comment);
            // 获取该评论的子评论
            addChildrenCommentById(commentVo);

            commentVos.add(commentVo);
        }

        return ResultVo.success(commentVos);
    }

    /**
     * 获取该评论的子评论
     *
     * @param commentVo
     */
    private void addChildrenCommentById(CommentVo commentVo) {

        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getParentId, commentVo.getId());
        lambdaQueryWrapper.eq(Comment::getLevel, 2);
        lambdaQueryWrapper.orderByDesc(Comment::getCreateDate);
        List<Comment> comments = commentMapper.selectList(lambdaQueryWrapper);

        List<CommentVo> commentVos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentVo commentVoChild = copyComment(comment);
            commentVos.add(commentVoChild);

        }
        commentVo.setChildrens(commentVos);
    }

    /**
     * 将Comment po 复制为vo
     *
     * @param comment
     * @return
     */
    private CommentVo copyComment(Comment comment) {
        // 设置直接属性
        CommentVo commentVo = new CommentVo();
        commentVo.setId(comment.getId());
        commentVo.setContent(comment.getContent());
        Long createDate = comment.getCreateDate();
        Date date = new Date(createDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(date);
        commentVo.setCreateDate(time);
        commentVo.setLevel(comment.getLevel());

        // 设置评论人信息
        SysUser author = sysUserService.getSysUserById(comment.getAuthorId());
        UserVo authorVo = new UserVo();
        BeanUtils.copyProperties(author, authorVo);
        commentVo.setAuthor(authorVo);

        // 设置被评论者信息
         if (comment.getLevel() == 2) {
             SysUser toUser = sysUserService.getSysUserById(comment.getToUid());
             UserVo toUserVo = new UserVo();
             BeanUtils.copyProperties(toUser, toUserVo);
             commentVo.setToUser(toUserVo);
        }

        return commentVo;
    }

}
