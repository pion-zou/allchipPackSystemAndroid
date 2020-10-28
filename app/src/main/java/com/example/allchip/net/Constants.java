package com.example.allchip.net;


/**
 * Created by lijing on 2017/7/18.
 */

public class Constants {

    // true正式环境/false测试环境
    public static final String MainUrl = "http://192.168.66.223:8080";


    //请求超时时间(单位s)
    public static final long CONNECT_TIME_OUT = 15;

    //支付回调等待时间
    public static final long PAYMENT_WAIT_TIME = 3000;

    //订单预留时间,单位分钟
    public static final int ORDER_REMAIN_TIME = 15;


}
