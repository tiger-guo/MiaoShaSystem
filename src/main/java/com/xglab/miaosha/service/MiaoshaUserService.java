package com.xglab.miaosha.service;

import com.xglab.miaosha.dao.MiaoshaUserDao;
import com.xglab.miaosha.domain.MiaoshaUser;
import com.xglab.miaosha.exception.GlobalException;
import com.xglab.miaosha.redis.MiaoshaUserKey;
import com.xglab.miaosha.redis.RedisService;
import com.xglab.miaosha.result.CodeMsg;
import com.xglab.miaosha.util.MD5Util;
import com.xglab.miaosha.util.UUIDUtil;
import com.xglab.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description:
 * @date: 2019/11/13
 */
@Service
public class MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;

    public MiaoshaUser getById(Long id) {
        return miaoshaUserDao.getById(id);
    }

    public String login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        // 参数校验
        String mobile = loginVo.getMobile();
        String frompass = loginVo.getPassword();

        MiaoshaUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        String password = MD5Util.formPassToDBPass(frompass, user.getSalt());
        if (!password.equals(user.getPassword())) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        // 生成cookie
        String token = UUIDUtil.uuid();
        setCookie(response, token, user);
        return token;
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        // 延长有效期
        if (user != null)
            setCookie(response, token, user);
        return user;
    }


    /**
     * cookie设置
     * @param response
     * @param user
     */
    private void setCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }


}
