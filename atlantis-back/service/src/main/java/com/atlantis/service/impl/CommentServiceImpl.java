package com.atlantis.service.impl;

import com.atlantis.mapper.CommentMapper;
import com.atlantis.pojo.Comment;
import com.atlantis.pojo.CommentJoinForumArticle;
import com.atlantis.service.CommentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<CommentJoinForumArticle> getAll() {
        return (commentMapper.getAll());
    }

    @Override
    public PageInfo<CommentJoinForumArticle> getAllByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CommentJoinForumArticle> commentList = commentMapper.getAll();
        return new PageInfo<CommentJoinForumArticle>(commentList);
    }

    @Override
    public Comment getByIndex(Integer index) {
        return (commentMapper.getByIndex(index));
    }

    @Override
    public List<CommentJoinForumArticle> getByName(String name, String loginType) {
        return (commentMapper.getByName(name, loginType));
    }

    @Override
    public PageInfo<CommentJoinForumArticle> getByNameByPage(Integer pageNum, Integer pageSize, String name, String loginType) {
        PageHelper.startPage(pageNum, pageSize);
        List<CommentJoinForumArticle> commentList = commentMapper.getByName(name, loginType);
        return new PageInfo<CommentJoinForumArticle>(commentList);
    }

    @Override
    public PageInfo<Comment> getByIdByPage(Integer pageNum, Integer pageSize, Integer id) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> commentList = commentMapper.getById(id);
        return new PageInfo<Comment>(commentList);
    }

    @Override
    public PageInfo<CommentJoinForumArticle> findByPage(Integer pageNum, Integer pageSize, String searchKey) {
        PageHelper.startPage(pageNum, pageSize);
        List<CommentJoinForumArticle> commentList = commentMapper.fuzzyQuery(searchKey);
        return new PageInfo<CommentJoinForumArticle>(commentList);
    }

    @Override
    public List<Comment> getById(Integer id) {
        return (commentMapper.getById(id));
    }

    @Override
    public boolean insert(Comment comment) {
        return (commentMapper.insert(comment) == 1);
    }

    @Override
    public boolean update(Comment comment) {
        return (commentMapper.update(comment) == 1);
    }

    @Override
    public boolean delete(Integer index) {
        return (commentMapper.delete(index) == 1);
    }

    @Override
    public boolean deleteById(Integer id) {
        commentMapper.deleteById(id);

        return true;
    }
}
