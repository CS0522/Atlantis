package com.atlantis.service.impl;

import com.atlantis.mapper.CommentMapper;
import com.atlantis.mapper.ForumArticleMapper;
import com.atlantis.mapper.UserMapper;
import com.atlantis.mapper.UserMessageMapper;
import com.atlantis.mapper.base.ArticleBaseMapper;
import com.atlantis.mapper.base.BaseMapper;
import com.atlantis.pojo.*;
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
    @Autowired
    private ForumArticleMapper forumArticleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserMessageMapper userMessageMapper;

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
        // 插入留言后，需要在消息中进行反馈
        if (commentMapper.insert(comment) == 1)
        {
            // get comment info:
            // loginType
            String loginType = comment.getLoginType();
            // id(thread's id, for `source`)
            Integer threadId = comment.getId();
            // content(for `content`)
            String content = comment.getContent();
            // name(for `title`)
            String name = comment.getName();

            if ("users".equals(loginType))
            {
                // get destination(user id):
                ForumArticle forumArticle = forumArticleMapper.getById(threadId);
                User user = userMapper.getByName(forumArticle.getAuthor());

                UserMessage userMessage = new UserMessage();
                userMessage.setSource(threadId);
                userMessage.setContent(content);
                userMessage.setTitle(name + "回复了你的帖子：");
                userMessage.setDestination(user.getId());

                // insert
                return (userMessageMapper.insert(userMessage) == 1);
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean update(Comment comment) {
        return (commentMapper.update(comment) == 1);
    }

    @Override
    public boolean delete(Integer index) {
        // 留言删除后，消息也需要进行删除
        return (commentMapper.delete(index) == 1);
    }

    @Override
    public boolean deleteById(Integer id) {
        commentMapper.deleteById(id);

        return true;
    }
}
