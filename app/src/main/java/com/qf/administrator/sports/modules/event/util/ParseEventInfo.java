package com.qf.administrator.sports.modules.event.util;

import com.qf.administrator.sports.modules.event.bean.EventInfo;
import com.qf.administrator.sports.modules.event.bean.ItemListInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chigo on 10/09/2016.
 */
public class ParseEventInfo {

    public static List<EventInfo> parseEventObject(String s) {
        List<EventInfo>  data=new ArrayList<>();
        try {
            JSONObject obj=new JSONObject(s);
            JSONArray array=obj.getJSONArray("content");
            for (int i = 0; i <array.length() ; i++) {
                   try{
                       JSONObject obj_content=array.getJSONObject(i);
                       EventInfo info=new EventInfo();
                       //id
                       info.setId(obj_content.getString("id"));
                       //比赛名字
                       info.setMatch_name(obj_content.getString("match_name"));
                       //比赛地址
                       info.setAddress(obj_content.getString("address"));

                       //解析itemListInfo
                       JSONArray array1= obj_content.getJSONArray("itemList");
                       ItemListInfo[] listInfo=new ItemListInfo[array1.length()];
                       for (int j = 0; j < array1.length(); j++) {
                           ItemListInfo itemListInfo=new ItemListInfo();
                           itemListInfo.setId(array1.getJSONObject(j).getString("id"));
                           itemListInfo.setMatch_id(array1.getJSONObject(j).getString("match_id"));
                           itemListInfo.setItem_name(array1.getJSONObject(j).getString("item_name"));
                           itemListInfo.setEnroll_fee(array1.getJSONObject(j).getString("enroll_fee"));
                           listInfo[j]=itemListInfo;
                       }
                       info.setItemlist(listInfo);

                       //团队类型
                       String team_type=obj_content.getString("match_team_type");
                       if ("1".equals(team_type)){
                           info.setMatch_team_type("人");
                       }else {
                           info.setMatch_team_type("队");
                       }
                       //图片
                       info.setPhoto_url(obj_content.getString("photo_url"));

                       //时间
                /*match_begintime: "2016-11-26T09:00:00",
                        match_endtime: "2016-11-26T21:00:00",
                        enroll_begintime: "2016-10-01T00:00:00",
                        enroll_endtime: "2016-11-13T23:59:00",*/

                       info.setEnroll_begintime(resolveTime(obj_content.getString("enroll_begintime")));
                       info.setEnroll_endtime(resolveTime(obj_content.getString("enroll_endtime")));
                       info.setMatch_begintime(resolveTime(obj_content.getString("match_begintime")));
                       info.setMatch_endtime(resolveTime(obj_content.getString("match_endtime")));

                       //sport类型
                       String sport_type=obj_content.getString("match_sport");
                       info.setMatch_sport(sportType(sport_type));


              /*  String OVER="已结束";//4
                String ING="活动中";//3
                String ENROLLING="报名中";//2*/


                       //报名的人数,总人数,活动状态
                /*max_enroll_num: 10000
                        match_status: 2,
                        joined_enroll_num: 7*/
                       info.setMax_enroll_num(obj_content.getString("max_enroll_num"));
                       info.setJoined_enroll_num(obj_content.getString("joined_enroll_num"));
                       String match_status=obj_content.getString("match_status");
                       if ("2".equals(match_status)){
                           info.setMatch_status(EventInfo.MatchStatus.ENROLLING);
                       }else if("3".equals(match_status)){
                           info.setMatch_status(EventInfo.MatchStatus.ING);
                       }else if("4".equals(match_status)){
                           info.setMatch_status(EventInfo.MatchStatus.OVER);
                       }

                       //添加数据到list集合
                       data.add(info);

                           }catch (Exception e){
                               e.printStackTrace();
                           }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    //处理时间的方法//
    public static String resolveTime(String str){
       str=str.replace("T"," ");
        str=str.substring(2,16);
        return str;
    }

    //判断运动类型
          /*String OTHER="其他";//7
                String RUN="跑步";//1
                String FOOTBA="足球";//3
                String BASKETBALL="篮球";//4
                String  BADBADMINTON="羽毛球";//2
                String  FUN="趣味运动";//5
                String FIVE="健身五项";//6*/
    public static String sportType(String num){
        switch (num){
            case "1":
                return EventInfo.SportType.RUN;
            case "2":
                return EventInfo.SportType.BADBADMINTON;
            case "3":
                return EventInfo.SportType.FOOTBA;
            case "4":
                return EventInfo.SportType.BASKETBALL;
            case "5":
                return EventInfo.SportType.FUN;
            case "6":
                return EventInfo.SportType.FIVE;
            case "7":
                return EventInfo.SportType.OTHER;
        }
        return "";
    }



}
