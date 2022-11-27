package com.atlantis.service;

import com.atlantis.pojo.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> getAll();

    public Category getByTypeOrRoute(String type, String route);

    public boolean insert(Category category);

    public boolean delete(Integer index);
}
