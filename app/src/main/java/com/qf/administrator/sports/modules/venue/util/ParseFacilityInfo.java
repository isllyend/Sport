package com.qf.administrator.sports.modules.venue.util;

import com.qf.administrator.sports.modules.venue.bean.DistrictInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/11.
 */
public class ParseFacilityInfo {
    public static List<DistrictInfo> parseFacilityInfo(String result){
        List<DistrictInfo> tempList=new ArrayList<>();
        DistrictInfo allInfo=new DistrictInfo();
        allInfo.setDistrictId("");
        allInfo.setDistrictText("全部");
        tempList.add(allInfo);
        DistrictInfo info=null;
        try {
            JSONArray array=new JSONArray(result);

            for (int i = 1; i < array.length()+1; i++) {
                try{
                    JSONObject subJson=array.getJSONObject(i-1);
                    info=new DistrictInfo();

                   info.setDistrictId(subJson.getString("id"));
                    info.setDistrictText(subJson.getString("facility_name"));
                    tempList.add(info);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tempList;
    }
}
