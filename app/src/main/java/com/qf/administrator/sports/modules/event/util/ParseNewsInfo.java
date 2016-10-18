package com.qf.administrator.sports.modules.event.util;

import android.util.Log;

import com.qf.administrator.sports.modules.event.bean.NewsInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chigo on 10/14/2016.
 */
public class ParseNewsInfo {
    public static List<NewsInfo> parseNewsInfo(String s) {
        List<NewsInfo> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(s);
            Log.e("Chigo","obj="+obj.toString());
            JSONArray array_newslist = obj.getJSONArray("newslist");
            for (int i = 0; i < array_newslist.length(); i++) {
                try {
                    JSONObject obj_news=array_newslist.getJSONObject(i);
                    NewsInfo info =new NewsInfo();
                    info.setCtime(obj_news.getString("ctime"));
                    info.setDescription(obj_news.getString("description"));
                    info.setTitle(obj_news.getString("title"));
                    info.setPicUrl(obj_news.getString("picUrl"));
                    info.setUrl(obj_news.getString("url"));
                    list.add(info);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
