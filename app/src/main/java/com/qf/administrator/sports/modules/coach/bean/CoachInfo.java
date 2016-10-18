package com.qf.administrator.sports.modules.coach.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public class CoachInfo {
    private int  id;//ID

    private String caoch_name;//教练名字

    private String coaching_specialty;//教的项目

    private String coaching_years;//教的年数

    private String comment;//评价人数

    private int rank;//星数

    private int  price;//价格

    private String icon;//头像

    private List<SecondCoachInfo> list;


    public List<SecondCoachInfo> getList() {
        return list;
    }

    public void setList(List<SecondCoachInfo> list) {
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaoch_name() {
        return caoch_name;
    }

    public void setCaoch_name(String caoch_name) {
        this.caoch_name = caoch_name;
    }

    public String getCoaching_specialty() {
        return coaching_specialty;
    }

    public void setCoaching_specialty(String coaching_specialty) {
        this.coaching_specialty = coaching_specialty;
    }

    public String getCoaching_years() {
        return coaching_years;
    }

    public void setCoaching_years(String coaching_years) {
        this.coaching_years = coaching_years;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }



}
