package com.qf.administrator.sports.modules.coach.util;

import com.qf.administrator.sports.modules.coach.bean.SecondCoachInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/11.
 */
public class SecondCoachJson {
    public static List<SecondCoachInfo> getSecondCoachJson(String result,int id) {
        List<SecondCoachInfo>list=new ArrayList<>();
        SecondCoachInfo secondCoachInfo=null;
        try {

            JSONObject jsonObject=new JSONObject(result);
            JSONArray secondJsonarray=jsonObject.getJSONArray("content");
            for (int i=0;i<secondJsonarray.length();i++){
                JSONObject secondObjct=secondJsonarray.getJSONObject(i);
                int secondid=secondObjct.getInt("id");
                if (id==secondid){

                    JSONArray secondArray = secondObjct.getJSONArray("coach_course_list");
                    for (int j = 0; j < secondArray.length(); j++) {
                        secondCoachInfo=new SecondCoachInfo();
                        JSONObject secondObject = secondArray.getJSONObject(j);
                        secondCoachInfo.setName(secondObject.getString("name"));
                        secondCoachInfo.setSecond_price(secondObject.getInt("price"));
                        String course_type = secondObject.getString("course_type");
                        if ("Private_Lesson".equals(course_type)) {
                           secondCoachInfo.setCourse_type("私教");
                        }
                        if ("Group_Lesson".equals(course_type) ||"OneToMany".equals(course_type)) {
                            secondCoachInfo.setCourse_type("团课");
                        }
                        list.add(secondCoachInfo);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

       return list;

    }
}
