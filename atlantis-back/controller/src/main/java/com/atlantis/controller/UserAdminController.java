package com.atlantis.controller;

import com.atlantis.common.Code;
import com.atlantis.common.Result;
import com.atlantis.exception.ServiceException;
import com.atlantis.pojo.UserAdmin;
import com.atlantis.service.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/useradmin")
public class UserAdminController {
    @Autowired
    private UserAdminService userAdminService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) // 从url路径获取id
    {
        // 设置异常情况
        if (id < 0)
        {
            throw new ServiceException("illegal id", Code.SEV_ERR);
        }
        // normal
        UserAdmin userAdmin = userAdminService.getById(id);
        Integer code = userAdmin != null ? Code.GET_OK : Code.GET_ERR;
        String msg = userAdmin != null ? "get succeeded" : "get failed";
        return new Result(code, userAdmin, msg);
    }
}
