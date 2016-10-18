package com.qf.administrator.sports.modules.my.bean;

public class ChangeCreditsEvent {
    private int credits;
    //登录
    public ChangeCreditsEvent( int credits) {
        this.credits=credits;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
