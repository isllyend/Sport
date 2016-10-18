package com.qf.administrator.sports.modules.venue.util;

import com.qf.administrator.sports.modules.venue.bean.ResourseInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 */
public class ParseResourseInfo {
    public static List<ResourseInfo> parseSubgymsInfo(String result){

        List<ResourseInfo> tempList=new ArrayList<>();

        ResourseInfo info=null;
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray array=jsonObject.getJSONArray("list");
            for (int i=0;i<array.length();i++){
                info=new ResourseInfo();
                info.setTime(array.getJSONObject(i).getString("date"));
                info.setPrice(array.getJSONObject(i).getString("min_price"));
                info.setRemain(array.getJSONObject(i).getString("can_order_num"));
                tempList.add(info);
            }





        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tempList;
    }
}
