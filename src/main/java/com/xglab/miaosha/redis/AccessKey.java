package com.xglab.miaosha.redis;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description:
 * @date: 2019/11/18
 */
public class AccessKey extends BasePrefix{


    public AccessKey(String prefix) {
        super(prefix);
    }

    public AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static AccessKey withExpire(int expireSeconds){
        return new AccessKey(expireSeconds, "access-");
    }
}
