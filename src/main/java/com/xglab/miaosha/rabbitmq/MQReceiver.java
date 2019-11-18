package com.xglab.miaosha.rabbitmq;

import com.rabbitmq.client.impl.nio.BlockingQueueNioQueue;
import com.xglab.miaosha.domain.MiaoshaOrder;
import com.xglab.miaosha.domain.MiaoshaUser;
import com.xglab.miaosha.domain.OrderInfo;
import com.xglab.miaosha.redis.RedisService;
import com.xglab.miaosha.result.CodeMsg;
import com.xglab.miaosha.result.Result;
import com.xglab.miaosha.service.GoodsService;
import com.xglab.miaosha.service.MiaoshaService;
import com.xglab.miaosha.service.OrderService;
import com.xglab.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description:
 * @date: 2019/11/18
 */
@Service
public class MQReceiver {

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RabbitListener(queues = MQConfig.DOMIAOSHA_QUEUE)
    public void receive(String message){
//        log.info("reveive:"+message);
        MiaoshaMessage mm = RedisService.stringToBean(message, MiaoshaMessage.class);
        MiaoshaUser user = mm.getUser();
        long goodsId = mm.getGoodsId();

        // 判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        Integer stock = goods.getStockCount();
        if (stock <= 0) {
            return;
        }
        // 判断是否秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return;
        }
        // 减库存 下订单 写入秒杀订单
        miaoshaService.miaosha(user, goods);
    }

    /*
    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message){
        log.info("reveive:"+message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message){
        log.info("Topic queue1 reveive:"+message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message){
        log.info("Topic queue2 reveive:"+message);
    }

    @RabbitListener(queues = MQConfig.HEADERS_QUEUE)
    public void receiveHeader(byte[] message){
        log.info("header queue reveive:"+new String(message));
    }
     */
}
