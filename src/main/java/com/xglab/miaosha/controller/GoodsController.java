package com.xglab.miaosha.controller;

import com.xglab.miaosha.domain.MiaoshaUser;
import com.xglab.miaosha.redis.GoodsKey;
import com.xglab.miaosha.redis.RedisService;
import com.xglab.miaosha.result.Result;
import com.xglab.miaosha.service.GoodsService;
import com.xglab.miaosha.service.MiaoshaUserService;
import com.xglab.miaosha.vo.GoodsDetailVo;
import com.xglab.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 1000 * 10
     * windons部署 页面缓存 QPS:63
     * Linux部署   页面缓存 QPS:48
     */
    @RequestMapping(value = "/to_list", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String toGoodList(HttpServletRequest request, HttpServletResponse response,
                             Model model, MiaoshaUser user) {
        String html = redisService.get(GoodsKey.getGoodList, "", String.class);
        // html缓存返回
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        model.addAttribute("user", user);
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);

        WebContext context = new WebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap());

        // 手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", context);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodList, "", html);
        }
        return html;
    }

    @RequestMapping(value = "/to_detail_2/{goodsId}", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String detail2(HttpServletRequest request, HttpServletResponse response,
                         Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId) {
        String html = redisService.get(GoodsKey.getGoodDetail, "" + goodsId, String.class);
        // html缓存返回
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

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

        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());

        // 手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", context);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodDetail, "" +goodsId, html);
        }
        return html;
    }

    @RequestMapping(value="/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user,
                                        @PathVariable("goodsId")long goodsId) {
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if(now < startAt ) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setMiaoshaStatus(miaoshaStatus);
        return Result.success(vo);
    }
}
