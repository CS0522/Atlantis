package com.atlantis.util;

import com.wf.captcha.SpecCaptcha;

/**
 * 生成验证码工具类
 */
public class VerifyCode {
    private SpecCaptcha specCaptcha = new SpecCaptcha(130,48,4);

    // return code
    public String getCode()
    {
        System.out.println("code: " + specCaptcha.text().toLowerCase());
        return specCaptcha.text().toLowerCase();
    }

    // return img Base64
    public String getImg()
    {
        return specCaptcha.toBase64();
    }
}
