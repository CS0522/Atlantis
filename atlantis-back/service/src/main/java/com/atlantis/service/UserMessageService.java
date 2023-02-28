package com.atlantis.service;

import com.atlantis.pojo.UserMessage;
import com.github.pagehelper.PageInfo;

public interface UserMessageService {

    public UserMessage getByIndex(Integer index);

    public PageInfo<UserMessage> getAllByPage(Integer pageNum, Integer pageSize);

    public PageInfo<UserMessage> getByDestinationByPage(Integer pageNum, Integer pageSize, Integer destination);

    public boolean insert(UserMessage userMessage);

    public boolean updateStatus(UserMessage userMessage);

    public boolean delete(Integer index);
}
