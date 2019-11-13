package com.xglab.miaosha.redis;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description:
 * @date: 2019/11/13
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();
}
