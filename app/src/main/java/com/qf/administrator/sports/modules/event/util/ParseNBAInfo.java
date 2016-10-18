package com.qf.administrator.sports.modules.event.util;

import android.util.Log;

import com.qf.administrator.sports.modules.event.bean.NBAInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chigo on 10/12/2016.
 */
public class ParseNBAInfo {
    public static Map<String,List<NBAInfo>> parseNBAInfo(String s) {
        Map<String,List<NBAInfo>> map=new HashMap<>();
        try {
            JSONObject obj = new JSONObject(s);
            JSONObject obj_result = obj.getJSONObject("result");
            Log.e("Chigo", "obj_result=" + obj_result.toString());
            JSONArray array_list = obj_result.getJSONArray("list");
            Log.e("Chigo", "array_list=" + array_list.toString());
            for (int i = 0; i < array_list.length(); i++) {
                try {
                    String title = array_list.getJSONObject(i).getString("title");
                    JSONArray array_tr = array_list.getJSONObject(i).getJSONArray("tr");
                    List<NBAInfo> list = new ArrayList<>();
                    for (int j = 0; j < array_tr.length(); j++) {
                        NBAInfo info = new NBAInfo();
                        JSONObject obj_tr = array_tr.getJSONObject(j);
                        info.setTime(obj_tr.getString("time"));
                        info.setPlayer1_name(obj_tr.getString("player1"));
                        info.setPlayer2_name(obj_tr.getString("player2"));
                        info.setPlayer1logo(obj_tr.getString("player1logo"));
                        info.setPlayer2logo(obj_tr.getString("player2logo"));
                        info.setLink1url(obj_tr.getString("link1url"));
                        info.setLink2url(obj_tr.getString("link2url"));
                        String score = obj_tr.getString("score");
                        //比分
                        if (score.contains("-")) {
                            String[] scores = score.trim().split("-");
                            info.setPlayer1_score(scores[0]);
                            info.setPlayer2_score(scores[1]);
                        } else {
                            info.setPlayer1_score("-");
                            info.setPlayer2_score("-");
                        }
                        //比赛状态
                        String status = obj_tr.getString("status");
                        if ("0".equals(status)) {
                            info.setStatus("未开赛");
                        } else if ("1".equals(status)) {
                            info.setStatus("直播中");
                        } else {
                            info.setStatus("已结束");
                        }
                        list.add(info);
                    }
                    map.put(title, list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }
}
