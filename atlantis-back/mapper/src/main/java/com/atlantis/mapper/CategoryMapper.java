package com.atlantis.mapper;

import com.atlantis.pojo.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    public List<Category> getAll();
}
