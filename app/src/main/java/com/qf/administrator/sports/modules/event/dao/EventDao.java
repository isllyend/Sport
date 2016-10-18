package com.qf.administrator.sports.modules.event.dao;

import com.qf.administrator.sports.i.BaseCallBack;
import com.qf.administrator.sports.modules.event.bean.EventInfo;
import com.qf.administrator.sports.modules.event.bean.NBAInfo;
import com.qf.administrator.sports.modules.event.util.HttpUtil;
import com.qf.administrator.sports.modules.event.util.ParseEventDetailsInfo;
import com.qf.administrator.sports.modules.event.util.ParseEventInfo;
import com.qf.administrator.sports.modules.event.util.ParseNBAInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Chigo on 10/09/2016.
 */

public class EventDao {
        public static void getEventInfoList(int page, final BaseCallBack baseCallBack){
            HttpUtil.doHttpReqeust("GET","http://platform-api.1yd.me/api/matches/available?page="+page, null,new BaseCallBack() {
                @Override
                public void success(Object data) {
                    //先解析data，获取list
                    List<EventInfo> tempList= ParseEventInfo.parseEventObject(data.toString());
                    if(tempList==null){
                        baseCallBack.failed(0,"");
                        return;
                    }
                    baseCallBack.success(tempList);
                }

                @Override
                public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode,data);
                }
            });
         }

    public static void getEventInfoDetails(int id, final BaseCallBack baseCallBack){
        HttpUtil.doHttpReqeust("GET","http://platform-api.1yd.me/api/matches/"+id, null,new BaseCallBack() {
            @Override
            public void success(Object data) {
                String tempList= ParseEventDetailsInfo.parseEventDetails(data.toString());
                if(tempList==null){
                    baseCallBack.failed(0,"");
                    return;
                }
                baseCallBack.success(tempList);
            }

            @Override
            public void failed(int errorCode, Object data) {
                baseCallBack.failed(errorCode,data);
            }
        });
    }

    public static void getNBAtInfo( final BaseCallBack baseCallBack){
        HttpUtil.doHttpReqeust("GET","http://op.juhe.cn/onebox/basketball/nba?dtype=json&=&key=3131b831628e5e7a615c3701e1e67deb", null,new BaseCallBack() {
            @Override
            public void success(Object data) {
                Map<String,List<NBAInfo>> tempList= ParseNBAInfo.parseNBAInfo(data.toString());
                if(tempList==null){
                    baseCallBack.failed(0,"");
                    return;
                }
                baseCallBack.success(tempList);
            }

            @Override
            public void failed(int errorCode, Object data) {
                baseCallBack.failed(errorCode,data);
            }
        });
    }
}
