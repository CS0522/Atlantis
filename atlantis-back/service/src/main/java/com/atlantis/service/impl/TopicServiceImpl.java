package com.atlantis.service.impl;

import com.atlantis.mapper.TopicMapper;
import com.atlantis.pojo.Topic;
import com.atlantis.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicMapper topicMapper;

    @Override
    public List<Topic> getAll() {
        return (topicMapper.getAll());
    }

    @Override
    public Topic getByIndex(Integer index)
    {
        return (topicMapper.getByIndex(index));
    }

    @Override
    public Topic getByTypeOrRoute(String type, String route)
    {
        return (topicMapper.getByTypeOrRoute(type, route));
    }

    @Override
    public boolean insert(Topic topic) {
        // 判断是否已经存在
        if (topicMapper.getByTypeOrRoute(topic.getType(), topic.getRoute()) != null)
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
    public boolean delete(Integer index) {
        return (topicMapper.delete(index) == 1);
    }
}
