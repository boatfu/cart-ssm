package com.boat.tools;

public  class RandomCode {

    public  String getCode(){

        String sum = "";
        for(int i = 0 ; i < 6; i ++){
            String num = (int)(Math.random() * 9.9) + "";
            sum += num;
        }
        return sum ;
    }
}
