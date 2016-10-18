package com.qf.administrator.sports.modules.coach.util;

import com.qf.administrator.sports.modules.coach.bean.CoachInfo;
import com.qf.administrator.sports.modules.coach.bean.SecondCoachInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public class CoachJson {
    public static List<CoachInfo> getCoachJson(String result){
        List<CoachInfo>list=new ArrayList<>();
        CoachInfo coachInfo=null;
        SecondCoachInfo secondCoachInfo=null;
        try {
            JSONObject jsonObject=new JSONObject(result);

            JSONArray jsonArray=jsonObject.getJSONArray("content");
            for (int i=0;i<jsonArray.length();i++){
                coachInfo=new CoachInfo();
                JSONObject coachObject=jsonArray.getJSONObject(i);
                coachInfo.setCaoch_name(coachObject.getString("caoch_name"));//教练名

                    if (coachObject.getString("coaching_years").equals("null")){
                        coachInfo.setCoaching_years(1+"");//教的年数
                    }else{
                        coachInfo.setCoaching_years(coachObject.getString("coaching_years"));//教的年数

                    }

                List<SecondCoachInfo> secondList=new ArrayList<>();
                JSONArray secondArray = coachObject.getJSONArray("coach_course_list");
                for (int j = 0; j < secondArray.length(); j++) {
                    secondCoachInfo=new SecondCoachInfo();
                    JSONObject secondObject = secondArray.getJSONObject(j);
                    secondCoachInfo.setName(secondObject.getString("name"));
                    secondCoachInfo.setSecond_price(secondObject.getInt("price"));
                    String course_type = secondObject.getString("course_type");
                    if ("Private_Lesson".equals(course_type)) {
                        secondCoachInfo.setCourse_type("私教");
                    }
                    if ("Group_Lesson".equals(course_type) || "OneToMany".equals(course_type)) {
                        secondCoachInfo.setCourse_type("团课");
                    }
                    secondCoachInfo.setCourseID(secondObject.getInt("courseID"));
                    secondList.add(secondCoachInfo);
                }
                coachInfo.setList(secondList);
                coachInfo.setComment(coachObject.getString("comment"));//评价人数
                coachInfo.setId(coachObject.getInt("id"));//id值
                coachInfo.setPrice(coachObject.getInt("price"));//价格
                coachInfo.setRank(coachObject.getInt("rank"));//星数
                coachInfo.setIcon(coachObject.getString("head_portrait"));//图片

                list.add(coachInfo);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
