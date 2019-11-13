package com.xglab.miaosha.service;

import com.xglab.miaosha.dao.MiaoshaUserDao;
import com.xglab.miaosha.domain.MiaoshaUser;
import com.xglab.miaosha.domain.User;
import com.xglab.miaosha.result.CodeMsg;
import com.xglab.miaosha.result.Result;
import com.xglab.miaosha.util.MD5Util;
import com.xglab.miaosha.util.ValidatorUtil;
import com.xglab.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description:
 * @date: 2019/11/13
 */
@Service
public class MiaoshaUserService {

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    public MiaoshaUser getById(Long id){
        return miaoshaUserDao.getById(id);
    }

    public CodeMsg login(LoginVo loginVo) {
        // 参数校验
        String mobile = loginVo.getMobile();
        String frompass = loginVo.getPassword();
        if(StringUtils.isEmpty(frompass)){
            return CodeMsg.PASSWORD_EMPTY;
        }
        if(StringUtils.isEmpty(mobile)){
            return CodeMsg.MOBILE_EMPTY;
        }
        if(!ValidatorUtil.isMobile(mobile)){
            return CodeMsg.MOBILE_ERROR;
        }

        MiaoshaUser user = getById(Long.parseLong(mobile));
        if(user == null){
            return CodeMsg.MOBILE_NOT_EXIST;
        }
        String password = MD5Util.formPassToDBPass(frompass, user.getSalt());
        if(!password.equals(user.getPassword())){
            return CodeMsg.PASSWORD_ERROR;
        }else {
            return CodeMsg.SUCCESS;
        }
    }
}
