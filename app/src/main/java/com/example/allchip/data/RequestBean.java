package com.example.allchip.data;

public class RequestBean<T> {
    public static int STATE_NORMAL = 0;

    private String message;
    private int state;
    private T content;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
