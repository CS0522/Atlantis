package com.atlantis.controller;

import com.atlantis.common.Code;
import com.atlantis.common.ProjectPath;
import com.atlantis.controller.base.BaseController;
import com.atlantis.exception.SystemException;
import com.atlantis.pojo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.atlantis.common.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

@RestController
// REST风格
@RequestMapping("/users")
public class UserController extends BaseController<User> {

    private static final String basePath = ProjectPath.getPhotosPath();

    @PutMapping("/info")
    public Result updateInfo(@RequestBody User user)
    {
        return updateByType(user, "info");
    }

    @PutMapping("/password")
    public Result updatePwd(@RequestBody User user)
    {
        return updateByType(user, "pwd");
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
    public Result login(HttpServletRequest request, @RequestBody User login)
    {
        // TODO login function
        System.out.println("logging in...");
        // 在业务层中进行以上处理，返回结果代码
        User result = baseService.login(login);

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
            request.getSession().setAttribute("user", result.getId());

            return new Result(Code.LOGIN_OK, result, "login ok");
        }
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request, @RequestBody User logout)
    {
        try
        {
            System.out.println("logging out...");
            request.getSession().removeAttribute("user");

            return new Result(Code.LOGOUT_OK, null, "log out succeeded");
        }
        catch (Exception e)
        {
            return new Result(Code.LOGOUT_ERR, null, "log out failed");
        }
    }

    // 用户的头像上传
    @PostMapping("/upload/{id}")
    public Result upload(MultipartFile file, @PathVariable Integer id)
    {
        System.out.println("uploading...");
        // 获取的file是临时文件，需要转存

        File filePath = new File(basePath);
        // 若目录不存在，创建
        if (!filePath.exists())
        {
            filePath.mkdirs();
        }

//        // 获取原始文件名后缀
//        String postfix = Objects.requireNonNull(
//                            file.getOriginalFilename()).substring(
//                                    (file.getOriginalFilename().lastIndexOf(".")));

        // 根据id重新生成用户名
        // 文件上传加入后缀 '.png'
        String fileName = "userId_" + id.toString() + ".png";

        // 转存文件
        try {
            file.transferTo(new File(basePath + fileName));
        }
        catch (IOException e) {
            throw new SystemException("upload failed", e, Code.SYS_ERR);
        }

        return new Result(Code.UPLOAD_OK, (String)fileName, "upload succeeded");
    }

    // 用户头像下载
    @GetMapping("/download/{id}")
    public Result download(HttpServletResponse response, @PathVariable Integer id)
    {
        try
        {
            // 输入流读取文件内容
            FileInputStream fis = new FileInputStream(basePath + "userId_" + id.toString() + ".png");
            // 输出流写回浏览器
            ServletOutputStream sos = response.getOutputStream();
            response.setContentType("image/png");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fis.read(bytes)) != -1)
            {
                sos.write(bytes, 0, len);
                sos.flush();
            }

            //close
            fis.close();
            sos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new Result(Code.DOWNLOAD_ERR, null, "no user photo");
        }

        return new Result(Code.DOWNLOAD_OK,
                        basePath + "userId_" + id.toString() + ".png",
                        "download succeeded");
    }

}