package com.xglab.miaosha.controller;

import com.xglab.miaosha.domain.MiaoshaUser;
import com.xglab.miaosha.service.GoodsService;
import com.xglab.miaosha.service.MiaoshaUserService;
import com.xglab.miaosha.service.UserService;
import com.xglab.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.soap.Addressing;
import java.util.List;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description:
 * @date: 2019/11/14
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/to_list")
    public String toGoodList(Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user", user);

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        // 秒杀时间判断
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;

        // 秒杀未开始
        if (now < startAt) {
            miaoshaStatus = 0;
            remainSeconds = (int) (startAt - now) / 1000;
        } else if (now > endAt) { // 秒杀结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {   // 秒杀正在进行
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }
}
