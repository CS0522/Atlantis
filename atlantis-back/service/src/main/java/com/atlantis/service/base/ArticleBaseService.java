package com.atlantis.service.base;

import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ArticleBaseService<T> {
    /**
     * 根据Id查询T，返回值为T对象
     * @param id
     * @return
     */
    public T getById(Integer id);

    /**
     * 根据index类别查询
     * @param index
     * @return
     */
    public List<T> getByIndex(Integer index, String order);

    /**
     * 查询全部
     * @return
     */
    public List<T> getAll(String order);

    /**
     * 分页查询全部
     * @param pageNum 第几页
     * @param pageSize 每页几个 == 10
     * @return
     */
    public PageInfo<T> getAllByPage(Integer pageNum, Integer pageSize, String order);

    /**
     * 分页查询所有，附带分页信息
     * @param pageNum 第几页
     * @param pageSize 每页几个 == 10
     * @param index 类别
     * @return
     */
    public PageInfo<T> getByIndexByPage(Integer pageNum, Integer pageSize, Integer index, String order);

    /**
     * 分页查询，模糊查询
     * @param pageNum 第几页
     * @param pageSize 每页几个 == 10
     * @param searchKey 模糊查询的值
     * @return
     */
    public PageInfo<T> findByPage(Integer pageNum, Integer pageSize, String searchKey);

    /**
     * 增加新T，根据dao层返回值进行操作是否成功
     * @param obj
     * @return
     */
    // check if exists before insert a new account
    public boolean insert(T obj);

    /**
     * 根据Id，更新T，根据dao层返回值进行操作是否成功
     * @param obj
     * @return
     */
    public boolean update(T obj);

    /**
     * 根据Id，删除T，根据dao层返回值进行操作是否成功
     * @param id
     * @return
     */
    public boolean delete(Integer id);
}
