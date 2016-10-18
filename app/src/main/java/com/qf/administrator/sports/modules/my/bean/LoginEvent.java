package com.qf.administrator.sports.modules.my.bean;

public class LoginEvent {
    private String url;
    private String nickname;
    private double money,fapiao;
    private int credits;
    //登录
    public LoginEvent(String url, String nickname,double money,double fapiao,int credits) {
        this.money=money;
        this.fapiao=fapiao;
        this.credits=credits;
        this.nickname = nickname;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public double getFapiao() {
        return fapiao;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setFapiao(double fapiao) {
        this.fapiao = fapiao;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
