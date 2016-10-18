package com.qf.administrator.sports.modules.coach.util;

import android.util.Log;

import com.qf.administrator.sports.modules.coach.bean.CoachDetailFirst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */
public class CoachDetailFirstJson {

    public static List<CoachDetailFirst> getCoachDetaiFirst(String result){
        List<CoachDetailFirst>lists=new ArrayList<>();
        CoachDetailFirst coachDetailFirst=null;
        try {

            JSONArray jsonArray=new JSONArray(result);
            for (int i=0;i<jsonArray.length();i++){
                coachDetailFirst=new CoachDetailFirst();
                 JSONObject jsonObject=jsonArray.getJSONObject(i);
                coachDetailFirst.setCoachingSpecialty(jsonObject.getString("coachingSpecialty"));
                coachDetailFirst.setCoachingYears(jsonObject.getString("coachingYears"));
                lists.add(coachDetailFirst);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("ii",lists.size()+"");
            return lists;
    }
}
