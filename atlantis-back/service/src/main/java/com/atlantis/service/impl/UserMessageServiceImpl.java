package com.atlantis.service.impl;

import com.atlantis.mapper.UserMessageMapper;
import com.atlantis.pojo.UserMessage;
import com.atlantis.service.UserMessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMessageServiceImpl implements UserMessageService {
    @Autowired
    private UserMessageMapper userMessageMapper;

    @Override
    public UserMessage getByIndex(Integer index) {
        return userMessageMapper.getByIndex(index);
    }

    @Override
    public PageInfo<UserMessage> getAllByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserMessage> userMessageList = userMessageMapper.getAll();
        return new PageInfo<UserMessage>(userMessageList);
    }

    @Override
    public PageInfo<UserMessage> getByDestinationByPage(Integer pageNum, Integer pageSize, Integer destination) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserMessage> userMessageList = userMessageMapper.getByDestination(destination);
        return new PageInfo<UserMessage>(userMessageList);
    }

    @Override
    public boolean insert(UserMessage userMessage) {
        return (userMessageMapper.insert(userMessage) == 1);
    }

    @Override
    public boolean updateStatus(UserMessage userMessage) {
        // 设置status为1
//        userMessage.setStatus(1);
        return (userMessageMapper.updateStatus(userMessage) == 1);
    }

    @Override
    public boolean delete(Integer index) {
        return (userMessageMapper.delete(index) == 1);
    }
}
