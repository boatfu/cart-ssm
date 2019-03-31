package com.boat.controller;

import com.boat.entity.Order;
import com.boat.entity.User;
import com.boat.service.OrderService;
import com.boat.service.ProductService;
import com.boat.service.UserService;
import com.boat.tools.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    @Autowired
    UserTools userTools;
    BackStageTools backStageTools = new BackStageTools();
    RandomCode randomCode = new RandomCode();
    //主页设置
    @RequestMapping("/")
    public String index(){
        return "redirect:/loginHtml";
    }
    //注册账号
    @RequestMapping("/regist")
    public String regest(HttpServletRequest req, HttpServletResponse resp){
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String passwordre = req.getParameter(("passwordre"));
        String email = req.getParameter("email");
        if(userTools.checkPasswordSafety(password) < 0){
            req.setAttribute("msg","密码格式错误");
            return "login.jsp";
        }
        //检查密码是否输入错误
        if( !password.equals(passwordre)){
            req.setAttribute("msg","check your password again");
            return "regist.jsp";
        }
        //检查邮箱的格式
        if(userTools.checkEmailForm(email) < 0){
            req.setAttribute("msg","email form is wrong");
            return "regist.jsp";
        }
        //检测邮箱是否存在(独一无二)
        if(userService.checkUserByEmail(email) != null){
            req.setAttribute("msg","email has already exist");
            return "regist.jsp";
        }

        User user = new User();
        user.setName(name);
        user.setPassword(password);

        user.setEmail(email);
        //检查是否重复NAME
        int x = 1;
        try {
             x = userTools.checkUserByName(user.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(x > 0){
                req.setAttribute("msg","name has already exist!");
                return "regist.jsp";
            } else {

                req.getSession().setAttribute("user",user);
                return "redirect:/emailConfirmHtml";
            }
    }
    @RequestMapping("/emailConfirmHtml")
        public String emailConfirmHtml(HttpServletRequest req){
        User user =(User)req.getSession().getAttribute("user");
        String email = user.getEmail();
        String code = randomCode.getCode() ;
        req.getSession().setAttribute("code",code);
        //生成邮件,储存在req中
        try {
            new Thread(new EmailTools(email, code)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "emailConfirm.jsp";

    }
    @RequestMapping("/emailConfirm")
    public String emailConfirm(HttpServletRequest req){
        String code = (String)req.getSession().getAttribute("code");
        if(code.equals(req.getParameter("code"))){
            User user = (User)req.getSession().getAttribute("user");
            userService.addUser(user);
            req.getSession().invalidate();
            req.getSession().setAttribute("user",user);
            return "redirect:/myAccount";
        } else {
            String msg = "验证码错误,请重新输入";
            req.setAttribute("msg",msg);
            return "emailConfirm.jsp";
        }


    }

    @RequestMapping("/findPasswordByEmailHtml")
    public String findPasswordByEmailHtml(HttpServletRequest req){
        return "findPasswordByEmail.jsp";
    }
    @RequestMapping("/findPasswordByEmail")
    public String findPasswordByEmail(HttpServletRequest req){
        String email = "";
        try{
            email = req.getParameter("email");
        }catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("msg", "未检测到邮箱");
            return "/findPasswordByEmailHtml";
        }
        //检查邮箱是否存在
        User user = userService.checkUserByEmail(email);
        if(user ==null){
            req.setAttribute("msg","该邮箱未注册");
            return "findPasswordByEmail.jsp";
        }


        String code = randomCode.getCode();
        req.getSession().setAttribute("email",email);
        req.getSession().setAttribute("code",code );
        try {
            new Thread(new EmailTools(email, code )).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.setAttribute("msg","邮件已发送,注意查收");
        return "inputCode.jsp";
    }

    @RequestMapping("/checkPasswordByEmail")
    public String checkPasswordByEmail(HttpServletRequest req){
        String code = (String)req.getSession().getAttribute("code");
        if(code.equals(req.getParameter("code"))){
            req.setAttribute("msg","验证成功" );
            return "resetPasswordByEmail.jsp";
        } else {
            req.setAttribute("msg","验证码错误,请重新输入");
            return "inputCode.jsp";
        }
    }
    @RequestMapping("/resetPasswordByEmail")
    public String resetPasswordByEmail(HttpServletRequest req){
        //首先验证密码是否配对
        String password = req.getParameter("password");
        String passwordre = req.getParameter("passwordre");
        if(password.equals(passwordre)){
            String email = (String) req.getSession().getAttribute("email");
            //从EMAIL找到User 是EMAIL独一无二
            User user = userService.checkUserByEmail(email);
            user.setPassword(password);
            userService.updateUser(user);

            req.getSession().invalidate();
            req.getSession().setAttribute("user",user);
            return "redirect:/myAccount";
        }else {
            req.setAttribute("msg","两次输入密码不匹配");
            return "resetPasswordByEmail.jsp";
        }



    }

    @RequestMapping("/login")
    public String checkUser(HttpServletRequest req, HttpServletResponse resp){
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        //检查密码格式
        if(userTools.checkPasswordSafety(password) < 0){
            req.setAttribute("msg","密码格式错误");
            return "login.jsp";
        }
        //判断是否为邮箱
        if(userTools.checkEmailForm(name) > 0){
            User userE = new User();
            userE.setEmail(name);
            userE.setPassword(password);
            //检查邮箱是否注册过
            if(userService.checkUserByEmail(name) != null){
                if(userTools.checkUserByEmailAndPassword(userE) > 0){
                    req.getSession().setAttribute("user",userService.checkUserByEmail(name));
                    return "redirect:/myAccount";
                }else {
                    req.setAttribute("msg","邮箱或密码错误");
                    return "login.jsp";}
            }else {
                req.setAttribute("msg","邮箱或密码错误");
                return "login.jsp";
            }
        }
        //通过密码登录

        User user = new User();
        user.setName(name);
        user.setPassword(password);

        //检查checkUserByNameAndPassword的返回值1
        int x = -1;
        try {
            x = userTools.checkUserByNameAndPassword(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(x < 0){
            req.setAttribute("msg","name or password is wrong!");
            return "login.jsp";
        } else {
            User user1 = userService.checkUser(user.getName());
            req.getSession().setAttribute("user",user1);
            //判断是否为管理员
            if(user1.getName().equals("root")){
                return "redirect: /backStage";
            }
            return "redirect:/myAccount";
        }
    }

    @RequestMapping("/myAccount")
    public String method (HttpServletRequest req, HttpServletResponse resp){
        //保护机制
        User user = null;
        try {
            user = (User)req.getSession().getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user == null){
            return "redirect:/loginHtml";
        } else if(user.getName().equals("root")){
            return "redirect:/backStage";
        }
        //订单列表
        List<Order> orderList = orderService.checkOrderByName(user.getName());
        req.setAttribute("orderList",orderList);
        req.setAttribute("total",orderList.size());
        return "myAccount.jsp";
    }

    @RequestMapping("/loginHtml")
    public String loginHtml(HttpServletRequest req, HttpServletResponse resp){
        return "login.jsp";
    }
    @RequestMapping("/registHtml")
    public String registHtml(HttpServletRequest req, HttpServletResponse resp){
        return "regist.jsp";
    }
    //注销功能
    @RequestMapping("/logout")
    public String logout(HttpServletRequest req){
        req.getSession().invalidate();
        return "redirect:/loginHtml";
    }


}
