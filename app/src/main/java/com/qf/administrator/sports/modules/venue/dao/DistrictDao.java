package com.qf.administrator.sports.modules.venue.dao;

import com.qf.administrator.sports.i.BaseCallBack;
import com.qf.administrator.sports.modules.venue.bean.DistrictInfo;
import com.qf.administrator.sports.modules.venue.util.ParseDistrivtInfo;
import com.qf.administrator.sports.modules.venue.util.ParseFacilityInfo;
import com.qf.administrator.sports.net.HttpUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/10/11.
 */
public class DistrictDao {
    public static  void getDistrictInfoList(String city_id,final BaseCallBack callBack){
        final  String url="http://platform-api.1yd.me/api/open/cities/"+city_id;
        HttpUtil.doHttpReqeust("GET", url, null, new BaseCallBack() {
            @Override
            public void success(Object data) {
                List<DistrictInfo> tempList= ParseDistrivtInfo.parseDistrictInfo(data.toString());
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
    public static void getFacilityInfoList(final BaseCallBack callBack){
        final String url="http://platform-api.1yd.me/api/open/facilitys";
        HttpUtil.doHttpReqeust("GET", url, null, new BaseCallBack() {
            @Override
            public void success(Object data) {
                List<DistrictInfo> tempList= ParseFacilityInfo.parseFacilityInfo(data.toString());
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
