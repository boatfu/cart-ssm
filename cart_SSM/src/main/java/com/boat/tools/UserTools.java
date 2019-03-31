package com.boat.tools;
import com.boat.entity.User;


import com.boat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserTools  {
    @Autowired
    UserService userService;
    public int checkUserByName(String name)   {


          User  user = userService.checkUser(name);


        if(user != null){
            return 1;
        }else{
            return -1;
        }
    }
    public int checkUserByNameAndPassword(User user) {
        int x = checkUserByName(user.getName());


        if( x > 0){
            User user2  = userService.checkUser(user.getName());
            if(  !(user2.getName().equals(user.getName()) && user2.getPassword().equals(user.getPassword()))){
                return -1;
            } else {
                return 1;
            }
        } else {
        return -1;
        }

    }
    public int checkUserByEmailAndPassword(User user){
        String email = user.getEmail();
        String password = user.getPassword();
        User user2 = userService.checkUserByEmail(email);
        if(user2.getEmail().equals(email) && user2.getPassword().equals(password)){
            return 1;
        }else {
            return -1;
        }
    }
    //检验密码的安全性
    public int checkPasswordSafety(String password){

        String regex = "^[\\w_-]{6,16}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        if(m.find()){
            return 1;
        }else{
            return -1;
        }
    }

    public int checkRepeat(String password , String passwordre){
        if( password.equals(passwordre)){
            return 1;
        } else {
            return -1;
        }
    }
    public int checkEmailForm(String email){
        String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.find()){
            return 1;
        }else{
            return -1;
        }

    }
}
