package com.eelve.limiting.resilience4j.aspect;



import com.eelve.limiting.resilience4j.exception.BaseException;
import com.eelve.limiting.resilience4j.vo.JsonResult;
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
    @ExceptionHandler(BaseException.class)
    public JsonResult<Void> handleException(BaseException e) {
        JsonResult<Void> r = new JsonResult<Void>();
        r.setCode(e.getCode());
        r.setMsg(e.getMessage());
        return r;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public JsonResult<Void> handleRuntimeException(Exception e) {
        log.info(e.getMessage());
        return JsonResult.error();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public JsonResult<Void> handleException(Exception e) {
        log.info("Exception" + e.getMessage());
        return JsonResult.error();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) //设置状态码为 400
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public String paramExceptionHandler(MethodArgumentNotValidException e) {
        log.info("MethodArgumentNotValidException" + e.getMessage());
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return fieldError.getDefaultMessage();
            }
        }
        return "请求参数错误";
    }
}
