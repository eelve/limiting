package com.eelve.limiting.hystrix.controller;

import com.eelve.limiting.hystrix.exception.BaseException;
import com.eelve.limiting.hystrix.exception.ProgramException;
import com.eelve.limiting.hystrix.vo.JsonResult;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhaozhilue
 */
@RestController
@DefaultProperties(defaultFallback = "timeOutError")
@Log
public class HystrixController {

    /**
     * 该方法是对接口调用超时的处理方法
     */
    public JsonResult timeOutError(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer num) {
        log.info("发生了熔断！！");
        return JsonResult.error("熔断");
    }

    @RequestMapping("/get")
    @ResponseBody
    @HystrixCommand(fallbackMethod = "timeOutError")
    public JsonResult allInfos(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer num){
        log.info("param----->" + num);
        try {
            Thread.sleep(num);

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
        } catch (ProgramException | InterruptedException exception) {
            log.info("error");
            return JsonResult.error("error");
        }
    }

}
