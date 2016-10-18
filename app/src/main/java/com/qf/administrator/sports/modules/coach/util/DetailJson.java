package com.qf.administrator.sports.modules.coach.util;

import com.qf.administrator.sports.modules.coach.bean.DetailInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/10/13.
 */
public class DetailJson {
    public static DetailInfo getDetailJson(String result) {
        DetailInfo detailInfo = new DetailInfo();
        try {
            JSONObject jsonObject = new JSONObject(result);
            detailInfo.setName(jsonObject.getString("name"));
            detailInfo.setPrice(jsonObject.getInt("price"));
            detailInfo.setSummary(jsonObject.getString("summary"));
            detailInfo.setCoach_id(jsonObject.getInt("coach_id"));
            String course_type = jsonObject.getString("course_type");
            if ("Private_Lesson".equals(course_type)) {
                detailInfo.setCourse_type("私教");
            }
            if ("Group_Lesson".equals(course_type)||"OneToMany".equals(course_type)) {
                detailInfo.setCourse_type("团课");
            }
            JSONObject jalocation = jsonObject.getJSONObject("location");
            detailInfo.setDetailed_address(jalocation.getString("detailed_address"));
            detailInfo.setMap_address(jalocation.getString("map_address"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  detailInfo;
    }
}
