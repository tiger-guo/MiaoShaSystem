package com.xglab.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description: md5加密工具类
 * @date: 2019/11/13
 */
public class MD5Util {


    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    // 将用户输入密码转换成form表单密码
    public static String inputPassFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    // 将form表单密码转换成数据库存储密码
    public static String formPassToDBPass(String inputPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(3) + salt.charAt(4);
        return inputPassFormPass(str);
    }

    // 将用户输入的密码转换成数据库存储的密码
    public static String inputPassToDBPass(String inputPass, String salt) {
        String formPass = inputPassFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, salt);
        return dbPass;
    }

    public static void main(String[] args) {
        inputPassFormPass("15399086896");
        System.out.println(inputPassToDBPass("15399086896", "123456"));
        //8fdc0b69cadb6d7891274cb810784c91
    }
}
