package com.boat.controller;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.boat.entity.Order;
import com.boat.entity.Product;
import com.boat.entity.User;
import com.boat.service.OrderService;
import com.boat.service.ProductService;
import com.boat.service.UserService;
import com.boat.tools.AlipayConfig;
import com.boat.tools.BackStageTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PayController {
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    BackStageTools backStageTools = new BackStageTools();
    @RequestMapping("/products")
    public String products(HttpServletRequest req){
        List<Product> productList = productService.list();
        req.setAttribute("productList",productList);
        req.setAttribute("total",productList.size());
        return "products.jsp";
    }



    @RequestMapping("/purchase")
    public String purchase(HttpServletRequest req){
        //判断是否存在user
        String use = backStageTools.checkUser(req);
        if(use != null){
            return use;
        }
        ArrayList<Product> products = (ArrayList<Product>) productService.list();
        req.setAttribute("products",products);
        req.setAttribute("total",products.size());
        return "purchase.jsp";
    }
    @RequestMapping("/prepare")
    public String prepare(HttpServletRequest req){
        //判断是否存在user
        String use = backStageTools.checkUser(req);
        if(use != null){
            return use;
        }
        Product product = productService.getProduct(Integer.parseInt(req.getParameter("id")) );
        User user = (User)req.getSession().getAttribute("user");
        String name = user.getName();
        String info = product.getName();
        req.setAttribute("name",name);
        req.setAttribute("info",info);
        req.setAttribute("price",product.getPrice());
        return "prepare.jsp";
    }

    @RequestMapping("/formOrder")
    public String formOrder(HttpServletRequest req){
        //判断是否存在user
        String use = backStageTools.checkUser(req);
        if(use != null){
            return use;
        }
        String name = req.getParameter("name");
        String info = req.getParameter("info");
        float price = Float.parseFloat(req.getParameter("price"));
        int  buy_counts = Integer.parseInt(req.getParameter("buy_counts"));
        float order_amount = buy_counts * price;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String create_time = df.format(new Date());
        Order order = new Order();
        order.setName(name);
        order.setInfo(info);
        order.setBuy_counts(buy_counts);
        order.setOrder_amount(order_amount);
        order.setCreate_time(create_time);
        orderService.addOrder(order);
        List<Order> list = orderService.checkOrderByName(order.getName());
        Collections.reverse(list);
        Order order1 = list.get(0);
        req.getSession().setAttribute("order",order1);
        return "formOrder.jsp";
    }
    @RequestMapping("/alipay")
    public String alipay(HttpServletRequest req){
//判断是否存在user
        String use = backStageTools.checkUser(req);
        if(use != null){
            return use;
        }
        //通过id更新当前order
        try {
            String id = req.getParameter("id");
            Order order = orderService.getOrder(Integer.parseInt(id));
            req.getSession().setAttribute("order",order);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "redirect:/goAlipay";
    }

    @RequestMapping(value = "/goAlipay", produces = "text/html; charset=UTF-8")
    public String goAlipay(HttpServletRequest req) throws Exception {

        Order order = (Order)req.getSession().getAttribute("order");
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getId() + "";
        //付款金额，必填
        String total_amount = order.getOrder_amount() + "";
        //订单名称，必填
        String subject = order.getInfo();
        //商品描述，可空
        String body = "用户订购商品个数：" + order.getBuy_counts();

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "1c";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        req.getSession().setAttribute("result",result);
        return "redirect:/form";
    }
    //提交表单
    @RequestMapping("/form")
    public String form(HttpServletRequest req){

        return "form.jsp";
    }

    @RequestMapping("/alipayReturnNotice")
    public ModelAndView alipayReturnNotice(HttpServletRequest req) throws Exception{

        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = req.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        ModelAndView mv = null;
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
             mv = new ModelAndView("alipaySuccess.jsp");
            //商户订单号
            String out_trade_no = new String(req.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(req.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(req.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水

            Order order = orderService.getOrder(Integer.parseInt(req.getParameter("out_trade_no")));
            orderService.updateOrderByStatus(order);



           mv.addObject("order",order);
            //从数据库中抽出优惠券

        } else {
            mv = new ModelAndView("alipayFailed.jsp");
        }

        return mv;
    }

    //异步操作
    @RequestMapping("/alipayNotifyNotice")
    @ResponseBody
    public String alipayNotifyNotice(HttpServletRequest req)throws Exception {

        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = req.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
        if (signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(req.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(req.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //交易状态
            String trade_status = new String(req.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            //付款金额
            String total_amount = new String(req.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            if (trade_status.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知

                // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水


            }} else {//验证失败
                System.out.println("fail");
            }

            return "success";
        }
    }
