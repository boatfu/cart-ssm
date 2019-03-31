package com.boat.tools;

import com.boat.entity.User;

import javax.servlet.http.HttpServletRequest;

public class BackStageTools {
    public String checkRoot(HttpServletRequest req){
        //判断是否为ROOT

        User root = null;

        try {
            root = (User) req.getSession().getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (root == null) {
            return "redirect:/loginHtml";
        } else if (!root.getName().equals("root")) {
            return "redirect:/loginHtml";
        }
        return null;
    }

    public String checkUser(HttpServletRequest req){
        //判断是否为User

        User root = null;

        try {
            root = (User) req.getSession().getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (root == null) {
            return "redirect:/loginHtml";
        } else {
        return null;}
    }


}
