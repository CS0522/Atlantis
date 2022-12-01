package com.atlantis.controller;

import com.atlantis.common.Code;
import com.atlantis.common.ProjectPath;
import com.atlantis.common.Result;
import com.atlantis.controller.base.BaseController;
import com.atlantis.exception.SystemException;
import com.atlantis.pojo.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
// REST风格
@RequestMapping("/members")
public class MemberController extends BaseController<Member> {

    private static final String basePath = ProjectPath.getPhotosPath();

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
        // 根据id重新生成用户名
        // 文件上传加入后缀 '.png'
        String fileName = "memberId_" + id.toString() + ".png";

        // 转存文件
        try {
            file.transferTo(new File(basePath + fileName));
        }
        catch (IOException e) {
            throw new SystemException("upload failed", e, Code.SYS_ERR);
        }

        // 多一步：通过status状态表示是否需要显示头像（前台）
        Member member = baseService.getById(id);
        member.setStatus(1);
        baseService.update(member);

        return new Result(Code.UPLOAD_OK, (String)fileName, "upload succeeded");
    }

    @GetMapping("/download/{id}")
    public Result download(HttpServletResponse response, @PathVariable Integer id)
    {
        try
        {
            // 输入流读取文件内容
            FileInputStream fis = new FileInputStream(basePath + "memberId_" + id.toString() + ".png");
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
            return new Result(Code.DOWNLOAD_ERR, null, "no member photo");
        }

        return new Result(Code.DOWNLOAD_OK,
                basePath + "memberId_" + id.toString() + ".png",
                "download succeeded");
    }
}
