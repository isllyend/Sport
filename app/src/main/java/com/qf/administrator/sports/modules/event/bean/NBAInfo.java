package com.qf.administrator.sports.modules.event.bean;

/**
 * Created by Chigo on 10/12/2016.
 */

public class NBAInfo {
    private String time;
    private String player1_name;


    public String getPlayer2_name() {
        return player2_name;
    }

    public void setPlayer2_name(String player2_name) {
        this.player2_name = player2_name;
    }

    public String getLink1url() {
        return link1url;
    }

    public void setLink1url(String link1url) {
        this.link1url = link1url;
    }

    public String getLink2url() {
        return link2url;
    }

    public void setLink2url(String link2url) {
        this.link2url = link2url;
    }

    public String getPlayer1_name() {
        return player1_name;
    }

    public void setPlayer1_name(String player1_name) {
        this.player1_name = player1_name;
    }

    public String getPlayer1_score() {
        return player1_score;
    }

    public void setPlayer1_score(String player1_score) {
        this.player1_score = player1_score;
    }

    public String getPlayer1logo() {
        return player1logo;
    }

    public void setPlayer1logo(String player1logo) {
        this.player1logo = player1logo;
    }

    public String getPlayer2_score() {
        return player2_score;
    }

    public void setPlayer2_score(String player2_score) {
        this.player2_score = player2_score;
    }

    public String getPlayer2logo() {
        return player2logo;
    }

    public void setPlayer2logo(String player2logo) {
        this.player2logo = player2logo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String player2_name;
    private String player1logo;
    private String player2logo;
    private String link1url;//视频链接
    private String link2url;//数据统计链接
    private String status;//比赛状态

    private String player1_score;//分数
    private String player2_score;//分数

}
