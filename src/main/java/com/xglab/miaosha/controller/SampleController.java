package com.xglab.miaosha.controller;

import com.xglab.miaosha.domain.User;
import com.xglab.miaosha.rabbitmq.MQSender;
import com.xglab.miaosha.redis.RedisService;
import com.xglab.miaosha.redis.UserKey;
import com.xglab.miaosha.result.Result;
import com.xglab.miaosha.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

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

    @Autowired
    MQSender sender;

    /*
    @RequestMapping("/mq")
    @ResponseBody
    public Result<String> mq(Model model) {
        sender.send("hello, mq");
        return Result.success("hello, mq");
    }

    @RequestMapping("/mq/topic")
    @ResponseBody
    public Result<String> mqTopic(Model model) {
        sender.sendTopic("hello, Topicmq");
        return Result.success("hello, Topicmq");
    }

    @RequestMapping("/mq/fanout")
    @ResponseBody
    public Result<String> mqFanout(Model model) {
        sender.sendFanout("hello, Fanoutmq");
        return Result.success("hello, Fanoutmq");
    }

    @RequestMapping("/mq/header")
    @ResponseBody
    public Result<String> mqHeader(Model model) {
        sender.sendHeader("hello, Headermq");
        return Result.success("hello, Headermq");
    }
     */

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "joshua");
        return "hello";
    }

    @RequestMapping("/curl")
    @ResponseBody
    public String curlGet(){
        return "hello curl";
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
