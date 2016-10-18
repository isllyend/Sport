package com.qf.administrator.sports.modules.event.bean;

import java.io.Serializable;

/**
 * Created by Chigo on 10/09/2016.
 */

public class ItemListInfo implements Serializable{
    private String id;
    private String match_id;
    private String item_name;
    private String enroll_fee;

    public String getEnroll_fee() {
        return enroll_fee;
    }

    public void setEnroll_fee(String enroll_fee) {
        this.enroll_fee = enroll_fee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }
}
