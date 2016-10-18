package com.qf.administrator.sports.modules.my.bean;

public class ChangeNicknameEvent {
    private String nickname;
    //登录
    public ChangeNicknameEvent(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
