package com.qf.administrator.sports.modules.event.bean;

import java.io.Serializable;

/**
 * Created by Chigo on 10/09/2016.
 */

public class EventInfo implements Serializable{
    private String id;
    private String address;
    private String match_name;
    private String match_team_type;
    private String match_sport;
    private String photo_url;
    private String match_begintime;
    private String match_endtime;
    private String enroll_begintime;
    private String enroll_endtime;
    private String max_enroll_num;
    private String match_status;
    private String joined_enroll_num;
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private ItemListInfo[] itemlist;

    public ItemListInfo[] getItemlist() {
        return itemlist;
    }

    public void setItemlist(ItemListInfo[] itemlist) {
        this.itemlist = itemlist;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEnroll_begintime() {
        return enroll_begintime;
    }

    public void setEnroll_begintime(String enroll_begintime) {
        this.enroll_begintime = enroll_begintime;
    }

    public String getEnroll_endtime() {
        return enroll_endtime;
    }

    public void setEnroll_endtime(String enroll_endtime) {
        this.enroll_endtime = enroll_endtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJoined_enroll_num() {
        return joined_enroll_num;
    }

    public void setJoined_enroll_num(String joined_enroll_num) {
        this.joined_enroll_num = joined_enroll_num;
    }

    public String getMatch_begintime() {
        return match_begintime;
    }

    public void setMatch_begintime(String match_begintime) {
        this.match_begintime = match_begintime;
    }

    public String getMatch_endtime() {
        return match_endtime;
    }

    public void setMatch_endtime(String match_endtime) {
        this.match_endtime = match_endtime;
    }

    public String getMatch_name() {
        return match_name;
    }

    public void setMatch_name(String match_name) {
        this.match_name = match_name;
    }

    public String getMatch_sport() {
        return match_sport;
    }

    public void setMatch_sport(String match_sport) {
        this.match_sport = match_sport;
    }

    public String getMatch_status() {
        return match_status;
    }

    public void setMatch_status(String match_status) {
        this.match_status = match_status;
    }

    public String getMatch_team_type() {
        return match_team_type;
    }

    public void setMatch_team_type(String match_team_type) {
        this.match_team_type = match_team_type;
    }

    public String getMax_enroll_num() {
        return max_enroll_num;
    }

    public void setMax_enroll_num(String max_enroll_num) {
        this.max_enroll_num = max_enroll_num;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }


   public  interface SportType{
        String OTHER="其他";//7
        String RUN="跑步";//1
        String FOOTBA="足球";//3
        String BASKETBALL="篮球";//4
        String  BADBADMINTON="羽毛球";//2
        String  FUN="趣味运动";//5
        String FIVE="健身五项";//6
    }

    public interface MatchStatus{
        String OVER="已结束";//4
        String ING="活动中";//3
        String ENROLLING="报名中";//2
    }
}
