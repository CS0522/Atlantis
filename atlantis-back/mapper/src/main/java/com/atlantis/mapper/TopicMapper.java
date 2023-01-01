package com.atlantis.mapper;

import com.atlantis.pojo.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TopicMapper {

    public List<Topic> getAll();

    public Topic getByIndex(@Param("index") Integer index);

    public Topic getByTypeOrRoute(@Param("type") String type, @Param("route") String route);

    public int insert(Topic category);

    public int delete(@Param("index") Integer index);
}
