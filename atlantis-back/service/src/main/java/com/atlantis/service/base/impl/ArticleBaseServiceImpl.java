package com.atlantis.service.base.impl;

import com.atlantis.mapper.base.ArticleBaseMapper;
import com.atlantis.service.base.ArticleBaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class ArticleBaseServiceImpl<T> implements ArticleBaseService<T> {
    @Autowired
    protected ArticleBaseMapper<T> articleBaseMapper;

    @Override
    public T getById(Integer id) {
        return articleBaseMapper.getById(id);
    }

    @Override
    public List<T> getByIndex(Integer index, String order) {
        return articleBaseMapper.getByIndex(index, order);
    }

    @Override
    public List<T> getAll(String order) {
        return articleBaseMapper.getAll(order);
    }

    @Override
    public PageInfo<T> getAllByPage(Integer pageNum, Integer pageSize, String order) {
        PageHelper.startPage(pageNum, pageSize);
        List<T> baseList = articleBaseMapper.getAll(order);
        return new PageInfo<T>(baseList);
    }

    @Override
    public PageInfo<T> getByIndexByPage(Integer pageNum, Integer pageSize, Integer index, String order) {
        PageHelper.startPage(pageNum, pageSize);
        List<T> baseList = articleBaseMapper.getByIndex(index, order);
        return new PageInfo<T>(baseList);
    }

    @Override
    public PageInfo<T> findByPage(Integer pageNum, Integer pageSize, String searchKey) {
        PageHelper.startPage(pageNum, pageSize);
        List<T> baseList = articleBaseMapper.fuzzyQuery(searchKey);
        return new PageInfo<T>(baseList);
    }

    // 需要再重写，检查是否存在相同标题
    @Override
    public boolean insert(T obj) {
        return false;
    }

    // 需要再重写，检查是否存在相同标题
    @Override
    public boolean update(T obj) {
        return false;
    }

    @Override
    public boolean updateView(T obj)
    {
        return (articleBaseMapper.updateView(obj) == 1);
    }

    @Override
    public boolean delete(Integer id) {
        return (articleBaseMapper.delete(id) == 1);
    }
}
