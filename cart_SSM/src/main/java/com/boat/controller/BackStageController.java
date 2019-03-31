package com.boat.controller;

import com.boat.entity.Order;
import com.boat.entity.Product;
import com.boat.entity.User;
import com.boat.service.OrderService;
import com.boat.service.ProductService;
import com.boat.service.UserService;
import com.boat.tools.BackStageTools;
import com.boat.tools.UserTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BackStageController {
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    @Autowired
    UserTools userTools;
    BackStageTools backStageTools = new BackStageTools();
    //后台管理
    @RequestMapping("/backStage")
    public String backStage(HttpServletRequest req, HttpServletResponse resp){
        //判断是否为ROOT
        String root = backStageTools.checkRoot(req);
        if( root !=null){
            return root;
        }

        ArrayList<User> users = (ArrayList<User>) userService.list();
        String msg = null;
        try {
            msg = (String) req.getAttribute("msg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(msg != null){
            req.setAttribute("msg",msg);
        }
        req.setAttribute("users",users);
        req.setAttribute("total",users.size());

        return "backStage.jsp";
    }

    //后台添加用户
    @RequestMapping("/addUserHtml")
    public String addUserHtml(HttpServletRequest req, HttpServletResponse resp){
        //判断是否为ROOT
        String root = backStageTools.checkRoot(req);
        if( root !=null){
            return root;
        }
        return "addUser.jsp";
    }

    @RequestMapping("/addUser")
    public String addUser(HttpServletRequest req, HttpServletResponse resp){
        //判断是否为ROOT
        String root = backStageTools.checkRoot(req);
        if( root !=null){
            return root;
        }
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
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
            return "redirect:/backStage";
        } else {
            userService.addUser(user);
            req.setAttribute("msg","add success!");
            return "redirect:/backStage";
        }

    }
    //删除后台用户deleteUser
    @RequestMapping("/deleteUser")
    public String deleteUser(HttpServletRequest req, HttpServletResponse resp){
        //判断是否为ROOT
        String root = backStageTools.checkRoot(req);
        if( root !=null){
            return root;
        }
        int id = Integer.parseInt(req.getParameter("id")) ;
        userService.deleteUser(id);
        req.setAttribute("msg","delete success!");
        return "redirect:/backStage";
    }
    @RequestMapping("/editUserHtml")
    public String editUserHtml(HttpServletRequest req, HttpServletResponse resp){
        //判断是否为ROOT
        String root = backStageTools.checkRoot(req);
        if( root !=null){
            return root;
        }
        String idd = req.getParameter("id");
        int  id = Integer.parseInt(req.getParameter("id"));

        User user = userService.getUser(id);
        req.setAttribute("id",idd);
        req.setAttribute("name",user.getName());
        req.setAttribute("password",user.getPassword());
        req.setAttribute("email",user.getEmail());
        return "editUser.jsp";
    }

    @RequestMapping("/editUser")
    public String editUser(HttpServletRequest req, HttpServletResponse resp) {
        //判断是否为ROOT
        String root = backStageTools.checkRoot(req);
        if( root !=null){
            return root;
        }
        String name = req.getParameter("name");

        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        User user2 = userService.getUser(id);

        //如果名字改变了检查是否重复NAME
        if (!user2.getName().equals(user.getName())) {
            int x = 1;
            try {
                x = userTools.checkUserByName(user.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (x > 0) {
                req.setAttribute("msg", "name has already exist!");
                return "redirect:/editUser";
            } else {
                userService.updateUser(user);
                req.setAttribute("msg", "edit success!");
                return "redirect:/backStage";
            }
        }   else {
            userService.updateUser(user);
            req.setAttribute("msg", "edit success!");
            return "redirect:/backStage";
        }


    }
    //backStege.jsp中的点击我查看商品
    @RequestMapping("/orderList")
    public String orderList(HttpServletRequest req){
        //判断是否为ROOT
        String root = backStageTools.checkRoot(req);
        if( root !=null){
            return root;
        }
        ArrayList<Order> orderList = (ArrayList<Order>) orderService.list();
        req.setAttribute("orderList",orderList);
        req.setAttribute("total",orderList.size());
        return "orderList.jsp";

    }
    @RequestMapping("/productList")
    public String productList(HttpServletRequest req){
        //判断是否为ROOT
        String root = backStageTools.checkRoot(req);
        if( root !=null){
            return root;
        }
        ArrayList<Product> productList = (ArrayList<Product>) productService.list();
        req.setAttribute("productList",productList);
        req.setAttribute("total",productList.size());
        return "productList.jsp";

    }
    @RequestMapping("/editProductHtml")
    public String editProductHtml(HttpServletRequest req){
        //判断是否为ROOT
        String root = backStageTools.checkRoot(req);
        if( root !=null){
            return root;
        }
        String idd = req.getParameter("id");
        int id = Integer.parseInt(idd);
        Product product = productService.getProduct(id);
        req.setAttribute("product",product);
        return "editProductHtml.jsp";
    }
    @RequestMapping("/editProduct")
    public String editProduct(HttpServletRequest req){
        //判断是否为ROOT
        String root = backStageTools.checkRoot(req);
        if( root !=null){
            return root;
        }
        String name = req.getParameter("name");
        String pricec = req.getParameter("price");
        String idd = req.getParameter("id");
        int id = Integer.parseInt(idd);
        float price = Float.parseFloat(pricec);
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        productService.updateProduct(product);
        return "redirect:/productList";
    }

    @RequestMapping("/addProductHtml")
    public String addProductHtml(HttpServletRequest req){
        //判断是否为ROOT
        String root = backStageTools.checkRoot(req);
        if( root !=null){
            return root;
        }
        return "addProduct.jsp";
    }
    @RequestMapping("/addProduct")
    public String addProduct(HttpServletRequest req){
        //判断是否为ROOT
        String root = backStageTools.checkRoot(req);
        if( root !=null){
            return root;
        }
        String name = req.getParameter("name");
        String pricec = req.getParameter("price");


        float price  = Float.parseFloat(pricec);
        Product product = new Product();

        product.setName(name);
        product.setPrice(price);
        productService.addProduct(product);
        return "redirect:/productList";
    }
    @RequestMapping("/deleteProduct")
    public String deleteProduct(HttpServletRequest req){
        //判断是否为ROOT
        String root = backStageTools.checkRoot(req);
        if( root !=null){
            return root;
        }
        String idd = req.getParameter("id");
        int id = Integer.parseInt(idd);
        productService.deleteProduct(id);
        return "redirect:/productList";
    }
    @RequestMapping("deleteOrder")
    public String deleteOrder(HttpServletRequest req){
        //判断是否为ROOT
        String root = backStageTools.checkRoot(req);
        if( root !=null){
            return root;
        }
        String idd = req.getParameter("id");
        int id = Integer.parseInt(idd);
        orderService.deleteOrder(id);
        return "redirect:/orderList";
    }

    @RequestMapping("/editOrderHtml")
    public String editOrderHtml(HttpServletRequest req){
        //判断是否为ROOT
        String root = backStageTools.checkRoot(req);
        if( root !=null){
            return root;
        }
        String idd = req.getParameter("id");
        int id = Integer.parseInt(idd);
        Order order = orderService.getOrder(id);
        req.setAttribute("order",order);
        return "editOrderHtml.jsp";
    }
    @RequestMapping("/editOrder")
    public String editOrder(HttpServletRequest req){
        //判断是否为ROOT
        String root = backStageTools.checkRoot(req);
        if( root !=null){
            return root;
        }
        Order order = new Order();
        order.setId(Integer.parseInt(req.getParameter("id")) );
        order.setInfo(req.getParameter("info"));
        order.setName(req.getParameter("name"));
        order.setBuy_counts(Integer.parseInt(req.getParameter("buy_counts")) );
        order.setOrder_amount(Float.parseFloat(req.getParameter("order_amount")));
        order.setCreate_time(req.getParameter("create_time"));
        order.setOrder_status(req.getParameter("order_status"));
        orderService.updateOrder(order);
        return "redirect:/orderList";
    }



}
