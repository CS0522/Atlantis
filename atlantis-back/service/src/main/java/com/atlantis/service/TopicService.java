package com.atlantis.service;

import com.atlantis.pojo.Topic;

import java.util.List;

public interface TopicService {
    public List<Topic> getAll();

    public Topic getByIndex(Integer index);

    public Topic getByTypeOrRoute(String type, String route);

    public boolean insert(Topic category);

    public boolean delete(Integer index);
}
