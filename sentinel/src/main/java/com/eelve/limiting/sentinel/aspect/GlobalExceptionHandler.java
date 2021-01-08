package com.eelve.limiting.sentinel.aspect;


import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.eelve.limiting.sentinel.exception.BaseException;
import com.eelve.limiting.sentinel.vo.JsonResult;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

/**
 * @author zhaozhilue
 */
@RestControllerAdvice
@Log
public class GlobalExceptionHandler {
    /**
     * 处理自定义异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UndeclaredThrowableException.class)
    public JsonResult<Void> handleException(UndeclaredThrowableException e) {
        JsonResult<Void> r = new JsonResult<Void>();
        log.info("我被熔断了 UndeclaredThrowableException");
        r.setCode(500);
        r.setMsg(e.getMessage());
        return r;
    }

    /**
     * 处理自定义异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BlockException.class)
    public JsonResult<Void> handleException(BlockException e) {
        JsonResult<Void> r = new JsonResult<Void>();
        log.info("我被熔断了 BlockException");
        r.setCode(500);
        r.setMsg(e.getMessage());
        return r;
    }
}
