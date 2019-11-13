package com.xglab.miaosha.controller;

import com.xglab.miaosha.domain.User;
import com.xglab.miaosha.redis.RedisService;
import com.xglab.miaosha.redis.UserKey;
import com.xglab.miaosha.result.Result;
import com.xglab.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description:
 * @date: 2019/11/9
 */
@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "joshua");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public User dbGet(){
        System.out.println(userService.tx());
        return userService.getById(2);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<String> redisGet(){
        redisService.set(UserKey.getById, "1", "admin");
        String str = redisService.get(UserKey.getById, "1", String.class);
        return Result.success(str);
    }
}
