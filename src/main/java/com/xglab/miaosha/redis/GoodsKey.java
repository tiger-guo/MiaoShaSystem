package com.xglab.miaosha.redis;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description:
 * @date: 2019/11/13
 */
public class GoodsKey extends BasePrefix {

    private GoodsKey(String prefix) {
        super(0, prefix);
    }

    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey getGoodList = new GoodsKey(60,"gl");
    public static GoodsKey getGoodDetail= new GoodsKey(60,"gd-");
    public static GoodsKey getMiaoshaGoodsStock= new GoodsKey(0,"gs-");

}
