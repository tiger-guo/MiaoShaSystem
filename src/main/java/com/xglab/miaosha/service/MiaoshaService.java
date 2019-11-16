package com.xglab.miaosha.service;

import com.xglab.miaosha.dao.GoodsDAO;
import com.xglab.miaosha.domain.Goods;
import com.xglab.miaosha.domain.MiaoshaUser;
import com.xglab.miaosha.domain.OrderInfo;
import com.xglab.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description:
 * @date: 2019/11/15
 */
@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        // 减库存 下订单 写入秒杀订单
        goodsService.reduceStock(goods);

        return orderService.createOrder(user, goods);

    }
}
