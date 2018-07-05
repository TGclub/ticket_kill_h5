package com.wizz.seckill.Exception;

/**
 * 自定义异常
 *
 * @author pjmike
 * @create 2018-05-24 20:51
 */
public class ObjectException extends RuntimeException{

    public ObjectException() {
        super();
    }

    public ObjectException(String message) {
        super(message);
    }
}