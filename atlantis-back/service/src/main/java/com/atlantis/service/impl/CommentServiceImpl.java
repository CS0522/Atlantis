package com.atlantis.service.impl;

import com.atlantis.mapper.CommentMapper;
import com.atlantis.pojo.Comment;
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
    public List<Comment> getAll() {
        return (commentMapper.getAll());
    }

    @Override
    public PageInfo<Comment> getAllByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> commentList = commentMapper.getAll();
        return new PageInfo<Comment>(commentList);
    }

    @Override
    public Comment getByIndex(Integer index) {
        return (commentMapper.getByIndex(index));
    }

    @Override
    public List<Comment> getByName(String name, String loginType) {
        return (commentMapper.getByName(name, loginType));
    }

    @Override
    public PageInfo<Comment> getByNameByPage(Integer pageNum, Integer pageSize, String name, String loginType) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> commentList = commentMapper.getByName(name, loginType);
        return new PageInfo<Comment>(commentList);
    }

    @Override
    public PageInfo<Comment> getByIdByPage(Integer pageNum, Integer pageSize, Integer id) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> commentList = commentMapper.getById(id);
        return new PageInfo<Comment>(commentList);
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
}
