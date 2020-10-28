package com.example.allchip.net;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lijing on 2017/7/19.
 * describe 返回结果基类
 */

public class TModel<T> {

    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_FAILED = 0;
    public static final int TOKEN_INVALID = -1;

    public static <T> TModel<T> success(T data) {
        final TModel<T> model = new TModel<>();
        model.setStatus(STATUS_SUCCESS);
        model.setData(data);
        return model;
    }

    public static <T> TModel<T> error(String msg) {
        final TModel<T> model = new TModel<>();
        model.setStatus(STATUS_FAILED);
        model.setMsg(msg);
        return model;
    }

    @SerializedName("status")
    private int status; //1成功, 0失败 ,-1登录失效

    @SerializedName("msg")
    private String msg;

    @SerializedName("data")
    private T data;

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 用户未登录或登录失效
     * @return
     */
    public boolean invalidLogin(){
        return status == TOKEN_INVALID || status == 403;
    }
}
