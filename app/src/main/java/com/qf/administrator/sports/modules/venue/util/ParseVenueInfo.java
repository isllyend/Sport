package com.qf.administrator.sports.modules.venue.util;

import com.qf.administrator.sports.modules.venue.bean.VenuesInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public class ParseVenueInfo {

    public static List<VenuesInfo> parseGameList(String result){
        List<VenuesInfo> tempList=new ArrayList<>();


        VenuesInfo info=null;
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray array=jsonObject.getJSONArray("content");

            for (int i = 0; i < array.length(); i++) {
                try{
                    JSONObject subJson=array.getJSONObject(i);
                    info=new VenuesInfo();
                    info.setId(subJson.getString("id"));
                    info.setPictrueUrl(subJson.getString("picture_url"));
                    info.setName(subJson.getString("name"));
                    info.setAdress(subJson.getString("address"));
                    info.setFaciliiesId( subJson.getJSONArray("facilities").getJSONObject(0).getString("id"));
                    info.setDistance(subJson.getString("distance"));
                    info.setPrice(subJson.getString("min_price"));
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
