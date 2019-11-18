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

    public MiaoshaKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey("go-");
    public static MiaoshaKey getMiaoshaPath = new MiaoshaKey(60,"mp-");
    public static MiaoshaKey getMiaoshaVerifyCode = new MiaoshaKey(300, "vc");
}
