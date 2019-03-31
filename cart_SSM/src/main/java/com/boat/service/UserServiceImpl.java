package com.boat.service;

import com.boat.dao.UserDao;
import com.boat.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    public void addUser(User user) {
        userDao.addUser(user);
    }

    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public User getUser(int id) {
        return userDao.getUser(id);
    }

    public List<User> list() {
        return userDao.list();
    }

    public User checkUser(String name) {

        return userDao.checkUser(name);


    }

    public User checkUserByEmail(String email) {
        return userDao.checkUserByEmail(email);
    }
}
