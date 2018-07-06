package com.wizz.seckill.Exception;

import com.wizz.seckill.Model.reqRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author pjmike
 * @create 2018-05-24 20:52
 */
@RestControllerAdvice
@Slf4j
public class OwnExceptionHandler {
    @ExceptionHandler(ObjectException.class)
    public reqRes exceptionHandler(ObjectException ex) {
        return new reqRes(1, ex.getMessage());
    }
}