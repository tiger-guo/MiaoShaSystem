package com.xglab.miaosha.util;

import com.xglab.miaosha.redis.BasePrefix;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description:
 * @date: 2019/11/13
 */
public class ValidatorUtil {

    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String src){
        if(StringUtils.isEmpty(src))
            return false;
        Matcher m = mobile_pattern.matcher(src);
        return m.matches();
    }
}
