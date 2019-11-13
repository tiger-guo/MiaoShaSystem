package com.xglab.miaosha.service;

import com.xglab.miaosha.dao.UserDAO;
import com.xglab.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author: LiuGuohu
 * @company: XGLAB
 * @description:
 * @date: 2019/11/10
 */
@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public User getById(int id){
        return userDAO.getById(id);
    }

    @Transactional
    public boolean tx(){
        User u1 = new User();
        u1.setId(2);
        u1.setName("222");
        userDAO.insert(u1);

        User u2 = new User();
        u2.setId(1);
        u2.setName("222");
        userDAO.insert(u1);
        return true;
    }
}
