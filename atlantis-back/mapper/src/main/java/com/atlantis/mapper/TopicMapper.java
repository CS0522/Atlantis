package com.atlantis.mapper;

import com.atlantis.pojo.Topic;
import com.atlantis.pojo.TopicJoinForumArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TopicMapper {

    public List<Topic> getAll();

    public Topic getByIndex(@Param("index") Integer index);

    public Topic getByType(@Param("type") String type);

    public List<TopicJoinForumArticle> fuzzyQuery(@Param("search") String search);

    public List<TopicJoinForumArticle> getCount();

    public int insert(Topic topic);

    public int update(Topic topic);

    public int delete(@Param("index") Integer index);
}
