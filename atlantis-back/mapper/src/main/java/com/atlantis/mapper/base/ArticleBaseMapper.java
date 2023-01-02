package com.atlantis.mapper.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

// 这个类用来对资讯和教程两个板块进行泛型
public interface ArticleBaseMapper<T> {
    public T getById(@Param("id") Integer id);

    public List<T> getByIndex(@Param("index") Integer index, @Param("order") String order);

    public T getByTitle(@Param("title") String title);

    public List<T> getAll(@Param("order") String order);

    public int update(T obj);

    public int updateView(T obj);

    public int insert(T obj);

    public int delete(@Param("id") Integer id);

    // 模糊查询
    public List<T> fuzzyQuery(@Param("searchKey") String searchKey);
}
