package com.atlantis.service;

import com.atlantis.pojo.Comment;
import com.atlantis.pojo.CommentJoinForumArticle;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CommentService {
    public List<CommentJoinForumArticle> getAll();

    public PageInfo<CommentJoinForumArticle> getAllByPage(Integer pageNum, Integer pageSize);

    public Comment getByIndex(Integer index);

    public List<Comment> getById(Integer id);

    public PageInfo<Comment> getByIdByPage(Integer pageNum, Integer pageSize, Integer id);

    public List<CommentJoinForumArticle> getByName(String name, String loginType);

    public PageInfo<CommentJoinForumArticle> getByNameByPage(Integer pageNum, Integer pageSize,
                                             String name, String loginType);

    public PageInfo<CommentJoinForumArticle> findByPage(Integer pageNum, Integer pageSize, String searchKey);

    public boolean insert(Comment comment);

    public boolean update(Comment comment);

    public boolean delete(Integer index);

    public boolean deleteById(Integer id);
}
