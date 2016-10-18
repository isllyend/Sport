package com.qf.administrator.sports.modules.my.bean;

public class ChangeFaPiaoEvent {
    private double fapiao;
    //登录
    public ChangeFaPiaoEvent(double fapiao) {
        this.fapiao=fapiao;
    }
    public double getFapiao() {
        return fapiao;
    }

    public void setFapiao(double fapiao) {
        this.fapiao = fapiao;
    }
}
