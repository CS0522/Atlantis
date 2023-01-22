package com.atlantis.service.impl;

import com.atlantis.mapper.ForumArticleMapper;
import com.atlantis.mapper.TopicMapper;
import com.atlantis.pojo.ForumArticle;
import com.atlantis.pojo.Topic;
import com.atlantis.pojo.TopicJoinForumArticle;
import com.atlantis.service.ForumArticleService;
import com.atlantis.service.TopicService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private ForumArticleService forumArticleService;

    @Override
    public List<Topic> getAll() {
        return (topicMapper.getAll());
    }

    @Override
    public PageInfo<Topic> getAllByPage(Integer pageNum, Integer pageSize)
    {
        PageHelper.startPage(pageNum, pageSize);
        List<Topic> topicList = topicMapper.getAll();
        return new PageInfo<Topic>(topicList);
    }

    @Override
    public Topic getByIndex(Integer index)
    {
        return (topicMapper.getByIndex(index));
    }

    @Override
    public Topic getByType(String type)
    {
        return (topicMapper.getByType(type));
    }

    @Override
    public PageInfo<TopicJoinForumArticle> findByPage(Integer pageNum, Integer pageSize, String search)
    {
        PageHelper.startPage(pageNum, pageSize);
        List<TopicJoinForumArticle> topicList = topicMapper.fuzzyQuery(search);
        return new PageInfo<TopicJoinForumArticle>(topicList);
    }

    @Override
    public List<TopicJoinForumArticle> getCount()
    {
        return (topicMapper.getCount());
    }

    @Override
    public PageInfo<TopicJoinForumArticle> getCountByPage(Integer pageNum, Integer pageSize)
    {
        PageHelper.startPage(pageNum, pageSize);
        List<TopicJoinForumArticle> topicJoinForumArticleList = topicMapper.getCount();
        return new PageInfo<TopicJoinForumArticle>(topicJoinForumArticleList);
    }

    @Override
    public boolean insert(Topic topic) {
        // 判断是否已经存在
        if (topicMapper.getByType(topic.getType()) != null)
        {
            return false;
        }
        // 不存在，可以加入
        else
        {
            return (topicMapper.insert(topic) == 1);
        }
    }

    @Override
    public boolean update(Topic topic) {
        // if exists
        if (topicMapper.getByType(topic.getType()) != null)
        {
            return false;
        }
        // 不存在，可以加入
        else
        {
            return (topicMapper.update(topic) == 1);
        }
    }

    @Override
    public boolean delete(Integer index) {
        // 删除该话题下的所有帖子
        if (!forumArticleService.deleteByIndex(index))
        {
            return false;
        }
        return (topicMapper.delete(index) == 1);
    }
}
