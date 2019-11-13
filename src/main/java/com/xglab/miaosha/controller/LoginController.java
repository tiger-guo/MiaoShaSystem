package com.xglab.miaosha.controller;

import com.sun.org.apache.bcel.internal.classfile.Code;
import com.xglab.miaosha.result.CodeMsg;
import com.xglab.miaosha.result.Result;
import com.xglab.miaosha.service.MiaoshaUserService;
import com.xglab.miaosha.util.ValidatorUtil;
import com.xglab.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description:
 * @date: 2019/11/13
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MiaoshaUserService userService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(LoginVo loginVo){
        log.info(loginVo.toString());

        CodeMsg codeMsg = userService.login(loginVo);
        if(codeMsg.getCode() == 0){
            System.out.println("siccess");
            return Result.success(true);
        }else {
            return Result.error(codeMsg);
        }
    }
}
