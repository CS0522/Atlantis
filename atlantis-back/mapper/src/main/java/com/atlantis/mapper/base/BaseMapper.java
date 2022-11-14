package com.atlantis.mapper.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<T> {
    // 查
    public T getById(Integer id);

    public List<T> getAll();

    public T getByName(@Param("name") String name);

    // 模糊查询
    public List<T> fuzzyQuery(@Param("name") String name);

    // 增，返回受影响行数
    public int insert(T obj);

    // 改，返回受影响行数
    // TODO 动态更新
    public int update(T obj);

    public int updateInfo(T obj);

    public int updatePwd(T obj);

    // 删，返回受影响行数 = 0
    public int delete(Integer id);

    public T getByUsernameAndPassword(@Param("username") String username,
                                      @Param("password") String password);
}
