package com.xglab.miaosha.redis;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description:
 * @date: 2019/11/13
 */
public class UserKey extends BasePrefix {

    private UserKey(String prefix) {
        super(0, prefix);
    }

    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey getById = new UserKey("id-");
    public static UserKey getByName = new UserKey("name-");
}
