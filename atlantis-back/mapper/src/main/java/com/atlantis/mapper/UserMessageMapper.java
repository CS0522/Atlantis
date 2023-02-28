package com.atlantis.mapper;

import com.atlantis.pojo.UserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
// 自动代理
public interface UserMessageMapper {
    public UserMessage getByIndex(@Param("index") Integer index);

    public List<UserMessage> getAll();

    public List<UserMessage> getByDestination(@Param("destination") Integer destination);

    public int insert(UserMessage userMessage);

    public int updateStatus(UserMessage userMessage);

    public int delete(@Param("index") Integer index);
}
