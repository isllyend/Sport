package com.qf.administrator.sports.modules.my.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.qf.administrator.sports.modules.event.bean.EventInfo;

import java.io.Serializable;
import java.util.ArrayList;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class userinfo extends BmobUser{
    private String nickname;
    private String gender;
    private BmobFile image;
    private double fapiao;
    private double fapiaoout;
    private ArrayList<EventInfo> sports;
    private String address;
    private double money;
    private int credits;
    private int bean;

    public int getBean() {
        return bean;
    }

    public void setBean(int bean) {
        this.bean = bean;
    }

    public double getFapiaoout() {
        return fapiaoout;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setFapiaoout(double fapiaoout) {
        this.fapiaoout = fapiaoout;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getFapiao() {
        return fapiao;
    }

    public ArrayList<EventInfo> getSports() {
        return sports;
    }

    public void setSports(ArrayList<EventInfo> sports) {
        this.sports = sports;
    }

    public void setFapiao(double fapiao) {
        this.fapiao = fapiao;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}