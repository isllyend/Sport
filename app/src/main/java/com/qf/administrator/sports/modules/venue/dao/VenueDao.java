package com.qf.administrator.sports.modules.venue.dao;

import android.util.Log;

import com.qf.administrator.sports.i.BaseCallBack;
import com.qf.administrator.sports.modules.venue.bean.VenuesInfo;
import com.qf.administrator.sports.modules.venue.util.ParseVenueInfo;
import com.qf.administrator.sports.net.HttpUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public class VenueDao {
    /**
     * 获取游戏列表的数据
     *
     * @return
     */
    public static void getVenueInfoList(int page,String city_id,String district_id,String activity_id,String facilityes_id,String sort,final BaseCallBack callBack) {
        final String url="http://platform-api.1yd.me/api/open/stadium?province_id=&city_id="+city_id+
                "&district_id="+district_id+"&activity_id="+activity_id+"&facility_id="+facilityes_id+
                "&page="+page+"&location_lat=&location_lon=&sort="+sort+"&size=10";
        //请求网络，并返回数据
        HttpUtil.doHttpReqeust("GET", url, null, new BaseCallBack() {
            @Override
            public void success(Object data) {
               //先解析data，获取list
                Log.i("info",url);
                List<VenuesInfo> tempList= ParseVenueInfo.parseGameList(data.toString());
                //Log.i("info",tempList.toString());
                if(tempList==null){
                    callBack.failed(0,"");
                    return;
                }
                callBack.success(tempList);
            }

            @Override
            public void failed(int errorCode, Object data) {
                callBack.failed(errorCode,data);
            }
        });
    }
}
