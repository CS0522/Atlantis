package com.atlantis.exception;

public class ServiceException extends RuntimeException{
    // 设置错误code，方便识别
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    // 重载构造方法

    public ServiceException(Integer code) {
        this.code = code;
    }

    public ServiceException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }
}
