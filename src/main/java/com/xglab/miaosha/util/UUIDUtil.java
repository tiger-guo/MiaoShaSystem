package com.xglab.miaosha.util;

import java.util.UUID;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description: UUID工具类
 * @date: 2019/11/14
 */
public class UUIDUtil {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
