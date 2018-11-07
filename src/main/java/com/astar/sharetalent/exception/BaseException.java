package com.astar.sharetalent.exception;

/**
 * @author zhangyu
 * @description 异常处理基类
 * @date 2018/10/12 19:02
 */
public abstract class BaseException extends RuntimeException{

    public BaseException(){}

    public BaseException(Throwable ex) {
        super(ex);
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable ex) {
        super(message, ex);
    }
}
