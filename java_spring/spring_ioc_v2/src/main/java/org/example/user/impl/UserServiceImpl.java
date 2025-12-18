package org.example.user.impl;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/18 10:17
 */

import org.example.user.UserService;
import org.example.user.dao.UserDao;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    @Override
    public void add() {
        userDao.add();
    }

    @Override
    public void init() {
        System.out.println("初始化调用！！！");
    }
}
