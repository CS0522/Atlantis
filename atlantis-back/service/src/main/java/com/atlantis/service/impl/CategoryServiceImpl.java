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
}
