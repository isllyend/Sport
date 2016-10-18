package com.qf.administrator.sports.modules.my.bean;

public class ChangeBeanEvent {
    private int bean;
    //登录
    public ChangeBeanEvent(int bean) {
        this.bean=bean;
    }

    public int getBean() {
        return bean;
    }

    public void setBean(int bean) {
        this.bean = bean;
    }
}
