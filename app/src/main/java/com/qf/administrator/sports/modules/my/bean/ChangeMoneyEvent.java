package com.qf.administrator.sports.modules.my.bean;

public class ChangeMoneyEvent {
    private double money;
    //登录
    public ChangeMoneyEvent(double money) {
        this.money=money;
    }
    public double getMoney() {
        return money;
    }
}
