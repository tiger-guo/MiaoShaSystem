package com.xglab.miaosha.redis;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description:
 * @date: 2019/11/18
 */
public class MiaoshaKey extends BasePrefix{

    private MiaoshaKey(String prefix) {
        super(0, prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey("go-");
}
