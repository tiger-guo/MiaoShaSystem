package com.xglab.miaosha.service;

import com.xglab.miaosha.dao.GoodsDAO;
import com.xglab.miaosha.domain.Goods;
import com.xglab.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description:
 * @date: 2019/11/14
 */
@Service
public class GoodsService {

    @Autowired
    GoodsDAO goodsDAO;

    public List<GoodsVo> listGoodsVo(){
        return goodsDAO.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {

        return goodsDAO.getGoodsVoByGoodsId(goodsId);
    }

    public void reduceStock(GoodsVo goods) {
        Goods good = new Goods();
        good.setGoodsStock(goods.getStockCount() - 1);
        goodsDAO.reduceStock(good);
    }
}
