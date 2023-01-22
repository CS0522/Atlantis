package com.atlantis.service;

import com.atlantis.pojo.Topic;
import com.atlantis.pojo.TopicJoinForumArticle;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TopicService {
    public List<Topic> getAll();

    public PageInfo<Topic> getAllByPage(Integer pageNum, Integer pageSize);

    public Topic getByIndex(Integer index);

    public Topic getByType(String type);

    public PageInfo<TopicJoinForumArticle> findByPage(Integer pageNum, Integer pageSize, String search);

    public List<TopicJoinForumArticle> getCount();

    public PageInfo<TopicJoinForumArticle> getCountByPage(Integer pageNum, Integer pageSize);

    public boolean insert(Topic topic);

    public boolean update(Topic topic);

    public boolean delete(Integer index);
}
