package com.atlantis.service.base;

import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BaseService<T> {
    /**
     * 根据Id查询T，返回值为T对象
     * @param id
     * @return
     */
    public T getById(Integer id);

    /**
     * 查询全部
     * @return
     */
    public List<T> getAll();

    /**
     * 分页查询所有，附带分页信息
     * @param pageNum 第几页
     * @param pageSize 每页几个 == 10
     * @return
     */
    public PageInfo<T> getAllByPage(int pageNum, int pageSize);

    /**
     * 分页查询，模糊查询
     * @param pageNum 第几页
     * @param pageSize 每页几个 == 10
     * @param name 模糊查询的值
     * @return
     */
    public PageInfo<T> findByPage(int pageNum, int pageSize, String name);

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
     * 根据Id，更新个人信息，根据dao层返回值进行操作是否成功
     * @param obj
     * @return
     */
    public boolean updateInfo(T obj);

    /**
     * 根据Id，更新密码，根据dao层返回值进行操作是否成功
     * @param obj
     * @return
     */
    public boolean updatePwd(T obj);

    /**
     * 根据Id，删除T，根据dao层返回值进行操作是否成功
     * @param id
     * @return
     */
    // TODO 需要修改，因为int返回值始终为 0
    public boolean delete(Integer id);

    public T login(T login);
}
