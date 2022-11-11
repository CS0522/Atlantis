package com.atlantis.mapper;

import com.atlantis.pojo.UserAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserAdminMapper {
    public UserAdmin getById(Integer userId);

    public List<UserAdmin> getAll();

    public int insert(@Param("userId")Integer userId, @Param("adminId")Integer adminId);

    public int delete(@Param("userId")Integer userId);
}
