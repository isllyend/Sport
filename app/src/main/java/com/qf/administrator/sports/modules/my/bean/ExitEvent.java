package com.qf.administrator.sports.modules.my.bean;

public class ExitEvent {
    private int defaulticon;
    private String state;
    //登录
    public ExitEvent(int defaulticon,String state) {
        this.defaulticon=defaulticon;
        this.state=state;
    }

    public int getDefaulticon() {
        return defaulticon;
    }

    public void setDefaulticon(int defaulticon) {
        this.defaulticon = defaulticon;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
