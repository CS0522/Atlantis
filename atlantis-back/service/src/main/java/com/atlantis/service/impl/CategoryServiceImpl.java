package com.atlantis.service.impl;

import com.atlantis.mapper.CategoryMapper;
import com.atlantis.pojo.Category;
import com.atlantis.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getAll() {
        return (categoryMapper.getAll());
    }

    public Category getByTypeOrRoute(String type, String route)
    {
        return (categoryMapper.getByTypeOrRoute(type, route));
    }

    @Override
    public boolean insert(Category category) {
        // 判断是否已经存在
        if (categoryMapper.getByTypeOrRoute(category.getType(), category.getRoute()) != null)
        {
            return false;
        }
        // 不存在，可以加入
        else
        {
            return (categoryMapper.insert(category) == 1);
        }
    }

    @Override
    public boolean delete(Integer index) {
        return (categoryMapper.delete(index) == 1);
    }
}
