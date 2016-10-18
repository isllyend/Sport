package com.qf.administrator.sports.modules.coach.util;

import com.qf.administrator.sports.modules.coach.bean.CoachCourse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */
public class CoachCourseJson  {
    public static List<CoachCourse> getCoachCourse(String restruct){
        List<CoachCourse>lists=new ArrayList<>();
        CoachCourse coachCourse=null;
        try {

            JSONObject jsonObject=new JSONObject(restruct);
            JSONArray jsonArray=jsonObject.getJSONArray("content");
            for (int i=0;i<jsonArray.length();i++){
                coachCourse=new CoachCourse();
                JSONObject objects=jsonArray.getJSONObject(i);
                coachCourse.setNameCourse(objects.getString("name"));
                coachCourse.setPrice(objects.getInt("price"));
                String course_type = objects.getString("course_type");
                if ("Private_Lesson".equals(course_type)) {
                    coachCourse.setCourse_type("私教");
                }
                if ("Group_Lesson".equals(course_type) ||"OneToMany".equals(course_type)) {
                    coachCourse.setCourse_type("团课");
                }

                String location=objects.getString("location");
                if ("null".equals(location)){
                   coachCourse.setMap_address("多个地区");
                }else{
                    JSONObject jalocation=objects.getJSONObject("location");
                    coachCourse.setMap_address(jalocation.getString("map_address"));
                }


                lists.add(coachCourse);



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lists;

    }
}
