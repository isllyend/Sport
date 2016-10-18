package com.qf.administrator.sports.modules.coach.util;

import com.qf.administrator.sports.modules.coach.bean.CoachDetailSecond;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/10/13.
 */
public class CoachDetailSecondJson {
    public static CoachDetailSecond getCoachDetailSecond(String result){
        CoachDetailSecond coachDetailSecond=null;
        try {
            JSONObject jsonObject=new JSONObject(result);
            coachDetailSecond=new CoachDetailSecond();
            coachDetailSecond.setNamesecond(jsonObject.getString("name"));
            coachDetailSecond.setGender(jsonObject.getString("gender"));
            coachDetailSecond.setMyself_intro(jsonObject.getString("myself_intro"));
            coachDetailSecond.setTeach_testimonials(jsonObject.getString("teach_testimonials"));
            coachDetailSecond.setHead_picture(jsonObject.getString("head_picture"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return coachDetailSecond;
    }

}
