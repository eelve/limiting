package com.eelve.limiting.resilience4j.controller;

import com.eelve.limiting.resilience4j.exception.BaseException;
import com.eelve.limiting.resilience4j.exception.ProgramException;
import com.eelve.limiting.resilience4j.vo.JsonResult;
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
@Log
public class Resilience4jController {

    /**
     * 该方法是对接口调用超时的处理方法
     */
    public JsonResult timeOutError(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer num) {
        response.setStatus(500);
        log.info("发生了熔断！！");
        return JsonResult.error("熔断");
    }

    /**
     * blockHandler = "errorReturn" 熔断
     * fallback = "errorReturn" 限流
     */

    @RequestMapping("/get")
    @ResponseBody
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

//            if (num % 5 == 0) {
//                log.info("num % 5 == 0");
//                throw new ProgramException("something bad whitch 5", 400);
//            }
//            if (num % 7 == 0) {
//                log.info("num % 7 == 0");
//                int res = 1 / 0;
//            }
            return JsonResult.ok();
        } catch (ProgramException e) {
            log.info("error");
            return JsonResult.error("error");
        }
    }


}
