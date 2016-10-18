package com.qf.administrator.sports.modules.venue.util;

import com.qf.administrator.sports.modules.venue.bean.SubgymsInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/10/14.
 */
public class ParseSubgymInfo {
    public static SubgymsInfo parseSubgymsInfo(String result){



        SubgymsInfo info=null;
        try {
            JSONObject jsonObject=new JSONObject(result);
            info=new SubgymsInfo();
            info.setSubGym_name(jsonObject.getString("subgym_name"));
            info.setPicture_url(jsonObject.getString("picture_url"));
            info.setAddress(jsonObject.getString("address"));
            info.setTraffic_info(jsonObject.getString("traffic_info"));
            info.setMobile(jsonObject.getString("mobile"));
            info.setField_count(jsonObject.getString("field_count"));
            info.setEnvironment(jsonObject.getString("environment"));
            info.setGround_material(jsonObject.getString("ground_material"));
            info.setFloor_height(jsonObject.getString("floor_height"));



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return info;
    }
}
