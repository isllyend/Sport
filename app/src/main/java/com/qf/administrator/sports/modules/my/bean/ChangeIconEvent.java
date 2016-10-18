package com.qf.administrator.sports.modules.my.bean;

public class ChangeIconEvent {
    private String url;
    //登录
    public ChangeIconEvent(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
