package com.atlantis.mapper;

import com.atlantis.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {

    public List<Category> getAll();

    public Category getByTypeOrRoute(@Param("type") String type, @Param("route") String route);

    public int insert(Category category);

    public int delete(@Param("index") Integer index);
}
