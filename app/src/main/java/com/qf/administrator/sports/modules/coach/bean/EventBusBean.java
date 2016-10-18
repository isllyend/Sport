package com.qf.administrator.sports.modules.coach.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/12.
 */
public class EventBusBean implements Serializable{
    private int courseID;//课程id

    private String icon;//头像

    private String year;//年数

    private String name;//名字


    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
