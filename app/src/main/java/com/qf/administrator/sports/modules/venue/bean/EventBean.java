package com.qf.administrator.sports.modules.venue.bean;

import android.support.v4.widget.DrawerLayout;
import android.widget.RelativeLayout;

import com.qf.administrator.sports.widget.MyDrawerLayout;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/12.
 */
public class EventBean implements Serializable{
    private MyDrawerLayout drawerLayout;
    private RelativeLayout drawerView;

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setDrawerLayout(MyDrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    public RelativeLayout getDrawerView() {
        return drawerView;
    }

    public void setDrawerView(RelativeLayout drawerView) {
        this.drawerView = drawerView;
    }
}
