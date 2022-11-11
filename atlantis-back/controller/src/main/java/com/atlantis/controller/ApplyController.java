package com.atlantis.controller;

import com.atlantis.common.Code;
import com.atlantis.common.Result;
import com.atlantis.controller.base.BaseController;
import com.atlantis.exception.SystemException;
import com.atlantis.pojo.Admin;
import com.atlantis.pojo.Apply;
import com.atlantis.pojo.User;
import com.atlantis.pojo.UserAdmin;
import com.atlantis.service.AdminService;
import com.atlantis.service.UserAdminService;
import com.atlantis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/applies")
public class ApplyController extends BaseController<Apply> {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserAdminService userAdminService;

    @GetMapping("/agree/{id}")
    public Result agreeApply(@PathVariable Integer id)
    {
        // TODO 给usermessage发送消息
        // 确认id正确，在apply中
        try
        {
            if (baseService.getById(id) == null)
            {
                return new Result(Code.GET_ERR, null, "null id");
            }

            // 根据id获取用户数据
            User user = userService.getById(id);
            // admin item
            Admin admin = new Admin();
            admin.setUsername(user.getUsername());
            admin.setPassword(user.getPassword());
            // 加入 admin 表，密码、名字保持不变，返回id主键
            // 如果插入的是否发现已经存在，返回失败
            if (!adminService.insert(admin)){
                return new Result(Code.GET_ERR, null, "failed");
            }

            Integer adminId = admin.getId();
            // id 添加到 user_admin 表中
            UserAdmin userAdmin = new UserAdmin();
            userAdmin.setUserId(id);
            userAdmin.setAdminId(adminId);
            boolean result = userAdminService.insert(userAdmin);

            return new Result(result ? Code.GET_OK : Code.GET_ERR, null,
                    result ? "succeeded" : "failed");
        }catch (Exception e)
        {
            throw new SystemException("unknown exception occurred", e, Code.SYS_ERR);
        }
    }
}
