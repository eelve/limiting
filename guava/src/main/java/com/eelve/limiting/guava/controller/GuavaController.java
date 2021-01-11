package com.eelve.limiting.guava.controller;


import com.eelve.limiting.guava.annotation.MyRateLimiter;
import com.eelve.limiting.guava.exception.BaseException;
import com.eelve.limiting.guava.exception.ProgramException;
import com.eelve.limiting.guava.vo.JsonResult;
import com.google.common.util.concurrent.SimpleTimeLimiter;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhaozhilue
 */
@RestController
@Log
public class GuavaController {

    @RequestMapping("/get")
    @ResponseBody
    public JsonResult allInfos(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer num){


        log.info("param----->" + num);
        try {
            Thread.sleep(num*100);

//            if (num % 2 == 0) {
//                log.info("num % 2 == 0");
//                throw new BaseException("something bad with 2", 400);
//            }

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

    /**
     * 开启限流
     * @return
     */
    @MyRateLimiter(qps = 2.0, timeout = 1)
    @GetMapping("/rateLimiter")
    public JsonResult rateLimiter(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer num) {
        log.info("param----->" + num);
        try {
            Thread.sleep(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("【rateLimiter】被执行了。。。。。");
        return JsonResult.ok("你不能总是看到我，快速刷新我看一下！");
    }


    /**
     * 未开启限流
     * @return
     */
    @GetMapping("/noRateLimiter")
    public JsonResult noRateLimiter(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer num) {
        log.info("param----->" + num);
        try {
            Thread.sleep(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("【noRateLimiter】被执行了。。。。。");
        return JsonResult.ok("我没有被限流哦，一直刷新一直在.....");
    }

}
