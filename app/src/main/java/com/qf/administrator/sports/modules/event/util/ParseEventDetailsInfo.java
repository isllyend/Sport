package com.qf.administrator.sports.modules.event.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Chigo on 10/10/2016.
 */
public class ParseEventDetailsInfo {
    public static String parseEventDetails(String s) {
        String result="";
        try {
            JSONObject obj=new JSONObject(s);
            result=obj.getString("remark");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
