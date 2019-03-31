package com.boat.dao;

import com.boat.entity.User;

import java.util.List;

public interface UserDao {
   void addUser( User user);
     void deleteUser(int id);
     void updateUser(User user);
     User getUser (int id ) ;
     List<User> list ();
     User checkUser(String name);
     User checkUserByEmail(String email);
}
