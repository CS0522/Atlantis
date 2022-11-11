package com.atlantis.controller;

import com.atlantis.common.Code;
import com.atlantis.common.Result;
import com.atlantis.exception.SystemException;
import com.atlantis.util.VerifyCode;
import com.wf.captcha.SpecCaptcha;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/verify")
public class VerifyController {

    @GetMapping
    public Result getVerifyCode(HttpServletRequest request)
    {
        try
        {
            VerifyCode verifyCode = new VerifyCode();
            // 写入session中
            request.getSession().setAttribute("verifyCode", verifyCode.getCode());
            // return Base64
            return new Result(Code.GET_OK, verifyCode.getImg(), "get verify code succeeded");
        }
        catch (Exception e)
        {
            throw new SystemException("unknown error occurred", e, Code.SYS_ERR);
        }
    }

    @PostMapping("/{verifyCode}")
    public Result checkVerifyCode(HttpServletRequest request, @PathVariable String verifyCode)
    {
        try
        {
            // 取出session中的验证码
            String code = (String) request.getSession().getAttribute("verifyCode");
            // 比较是否正确
            if (code.equals(verifyCode.toLowerCase()))
            {
                // 删除验证码
//                request.getSession().removeAttribute("verifyCode");
                return new Result(Code.VERIFY_OK, null, "verify succeeded");
            }
            // 不正确
            else
            {
                return new Result(Code.VERIFY_ERR, null, "verify failed");
            }
        }
        catch (Exception e)
        {
            throw new SystemException("unknown error occurred", e, Code.SYS_ERR);
        }
    }
}
