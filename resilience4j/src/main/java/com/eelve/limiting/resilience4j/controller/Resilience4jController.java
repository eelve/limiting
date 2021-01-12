package com.eelve.limiting.resilience4j.controller;

import com.eelve.limiting.resilience4j.exception.BaseException;
import com.eelve.limiting.resilience4j.exception.ProgramException;
import com.eelve.limiting.resilience4j.vo.JsonResult;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.BufferUnderflowException;

/**
 * @author zhaozhilue
 */
@RestController
@Log
public class Resilience4jController {

    @RequestMapping("/get")
    @ResponseBody
    //@TimeLimiter(name = "BulkheadA")
    @CircuitBreaker(name = "BulkheadA")
    @Bulkhead(name = "BulkheadA",fallbackMethod = "fallbackMethod")
    public JsonResult allInfos(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer num){
        log.info("param----->" + num);
        try {
            //Thread.sleep(num);

            if (num % 2 == 0) {
                log.info("num % 2 == 0");
                throw new BaseException("something bad with 2", 400);
            }

            if (num % 3 == 0) {
                log.info("num % 3 == 0");
                throw new BaseException("something bad whitch 3", 400);
            }

            if (num % 5 == 0) {
                log.info("num % 5 == 0");
                throw new ProgramException("something bad whitch 5", 400);
            }
            if (num % 7 == 0) {
                log.info("num % 7 == 0");
                int res = 1 / 0;
            }
            return JsonResult.ok();
        } catch (BufferUnderflowException e) {
            log.info("error");
            return JsonResult.error("error");
        }
    }


    public JsonResult fallbackMethod(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer num, BulkheadFullException exception) {
        return JsonResult.error("error 熔断" + num );
    }

}
