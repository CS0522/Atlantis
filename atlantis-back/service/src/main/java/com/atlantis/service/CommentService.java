package com.atlantis.service;

import com.atlantis.pojo.Comment;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CommentService {
    public List<Comment> getAll();

    public PageInfo<Comment> getAllByPage(Integer pageNum, Integer pageSize);

    public Comment getByIndex(Integer index);

    public List<Comment> getById(Integer id);

    public PageInfo<Comment> getByIdByPage(Integer pageNum, Integer pageSize, Integer id);

    public List<Comment> getByName(String name, String loginType);

    public PageInfo<Comment> getByNameByPage(Integer pageNum, Integer pageSize,
                                             String name, String loginType);

    public boolean insert(Comment comment);

    public boolean update(Comment comment);

    public boolean delete(Integer index);
}
