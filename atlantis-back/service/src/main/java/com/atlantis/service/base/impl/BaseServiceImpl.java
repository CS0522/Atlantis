package com.atlantis.service.base.impl;

import com.atlantis.mapper.base.BaseMapper;
import com.atlantis.service.base.BaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {
    @Autowired
    // 代理对象自动装配，SpringBoot无法识别
    // userDao实例可能会报错，修改检查配置
    // protected 子类可继承
    protected BaseMapper<T> baseMapper;

    @Override
    public T getById(Integer id) {
        return baseMapper.getById(id);
    }

    @Override
    public List<T> getAll() {
        return baseMapper.getAll();
    }

    @Override
    public PageInfo<T> getAllByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<T> baseList = baseMapper.getAll();
        return new PageInfo<T>(baseList);
    }

    @Override
    public PageInfo<T> findByPage(int pageNum, int pageSize, String name) {
        PageHelper.startPage(pageNum, pageSize);
        List<T> baseList = baseMapper.fuzzyQuery(name);
        return new PageInfo<T>(baseList);
    }

    @Override
    // check if exists before insert a new account
    public boolean insert(T obj) {
        return false;
    }

    @Override
    public boolean update(T obj) {
        return false;
    }

    // 进行重写
    @Override
    public boolean updateInfo(T obj) {
        return false;
    }

    // 进行重写
    @Override
    public boolean updatePwd(T obj)
    {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return (baseMapper.delete(id) == 1);
    }

    @Override
    public T login(T login) {
        return null;
    }
}
