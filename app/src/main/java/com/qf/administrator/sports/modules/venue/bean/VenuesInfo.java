package com.qf.administrator.sports.modules.venue.bean;

/**
 * Created by Administrator on 2016/10/10.
 */
public class VenuesInfo {
    private String id;
    private String pictrueUrl;
    private String name;
    private String adress;
    private String distance;
    private String price;
    private String faciliiesId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPictrueUrl() {
        return pictrueUrl;
    }

    public void setPictrueUrl(String pictrueUrl) {
        this.pictrueUrl = pictrueUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFaciliiesId() {
        return faciliiesId;
    }

    public void setFaciliiesId(String faciliiesId) {
        this.faciliiesId = faciliiesId;
    }
}
