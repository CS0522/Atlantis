package com.atlantis.controller;

import com.atlantis.common.Code;
import com.atlantis.common.Result;
import com.atlantis.controller.base.BaseController;
import com.atlantis.exception.ServiceException;
import com.atlantis.exception.SystemException;
import com.atlantis.pojo.Admin;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
// REST风格
@RequestMapping("/admins")
public class AdminController extends BaseController<Admin> {

    // 对insert进行重载，因为有主键返回
    @Override
    @PostMapping
    public Result insert(@RequestBody Admin admin)
    {
        // 设置异常情况，获取到的obj == null
        if (admin == null)
        {
            throw new ServiceException("null object", Code.SEV_ERR);
        }
        // normal
        try
        {
            // check if exists before insert a new account
            boolean result = baseService.insert(admin);
            // 返回主键id
            return new Result(result ? Code.INSERT_OK : Code.INSERT_ERR, admin.getId(),
                    result ? "insert succeeded" : "insert failed");
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @PutMapping("/info")
    public Result updateInfo(@RequestBody Admin admin)
    {
        return updateByType(admin, "info");
    }

    @PutMapping("/password")
    public Result updatePwd(@RequestBody Admin admin)
    {
        return updateByType(admin, "pwd");
    }

    /*
      1. 密码进行加密处理
      2. 查询数据库
      3. 无结果，返回失败 == LOGIN_ERR
      4. 密码不一致，返回失败 == LOGIN_ERR
      5. 员工状态，返回失败 == STATUS_OFF
      6. 登陆成功，将id存储到session中 == Code.LOGIN_OK
   */
    @PostMapping("/login") // request用来保存session
    public Result login(HttpServletRequest request, @RequestBody Admin login)
    {
        // login function
//        System.out.println(admin);
        // 在业务层中进行以上处理，返回结果代码
        System.out.println("logging in...");
        Admin result = baseService.login(login);
        // 3
        if (result == null || !result.getPassword().equals(login.getPassword()))
        {
            return new Result(Code.LOGIN_ERR, null, "incorrect username or password");
        }
        else if (result.getStatus() == 0)
        {
            return new Result(Code.STATUS_OFF, null, "the account is off");
        }
        // success
        else
        {
            // save into session
            // admin: admin.id
            request.getSession().setAttribute("admin", result.getId());
            System.out.println("admin id: " + request.getSession().getAttribute("admin"));
            System.out.println("session id: " + request.getSession().getId());
            return new Result(Code.LOGIN_OK, result, "login ok");
        }
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request, @RequestBody Admin logout)
    {
        try
        {
            System.out.println("logging out...");
            System.out.println("admin id: " + request.getSession().getAttribute("admin"));
            System.out.println("session id: " + request.getSession().getId());
            request.getSession().removeAttribute("admin");
            System.out.println("log out succeeded");
            return new Result(Code.LOGOUT_OK, null, "log out succeeded");

        }
        catch (Exception e)
        {
            return new Result(Code.LOGOUT_ERR, null, "log out failed");
        }
    }
}
