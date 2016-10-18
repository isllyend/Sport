package com.qf.administrator.sports.modules.coach.bean;

/**
 * Created by Administrator on 2016/10/11.
 */
public class SecondCoachInfo {
    private int id;

    private String name;

    private int second_price;

    private String course_type;

    private int courseID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSecond_price() {
        return second_price;
    }

    public void setSecond_price(int second_price) {
        this.second_price = second_price;
    }

    public String getCourse_type() {
        return course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
