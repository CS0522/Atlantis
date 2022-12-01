package com.atlantis.exception;

import com.atlantis.common.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


// 声明这是处理异常的控制层的类，Advice是AOP思想
@RestControllerAdvice
public class ExceptionAdvice {

    // 声明处理异常的方法，和哪一种异常
    @ExceptionHandler(ServiceException.class)
    public Result doBusinessException(ServiceException e)
    {
        return new Result(e.getCode(), e, e.getMessage());
    }

    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException e)
    {
        // TODO
        // 记录日志
        // 处理错误等操作
        return new Result(e.getCode(), e, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result doException(Exception e)
    {
        // TODO
        // 记录日志
        // 处理错误等操作
        return new Result(Code.EXC_ERR, null, "unknown exception occurred");
    }
}
