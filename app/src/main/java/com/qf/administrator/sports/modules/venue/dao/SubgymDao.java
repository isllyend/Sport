package com.qf.administrator.sports.modules.venue.dao;

import android.util.Log;

import com.qf.administrator.sports.i.BaseCallBack;
import com.qf.administrator.sports.modules.venue.bean.ResourseInfo;
import com.qf.administrator.sports.modules.venue.bean.SubgymsInfo;
import com.qf.administrator.sports.modules.venue.util.ParseResourseInfo;
import com.qf.administrator.sports.modules.venue.util.ParseSubgymInfo;
import com.qf.administrator.sports.net.HttpUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 */
public class SubgymDao {
    public static void getSubgymInfo(String item_id, final BaseCallBack callBack) {
        final String url = "http://platform-api.1yd.me/api/open/subgyms/" + item_id;

        HttpUtil.doHttpReqeust("GET", url, null, new BaseCallBack() {
            @Override
            public void success(Object data) {

                SubgymsInfo info = ParseSubgymInfo.parseSubgymsInfo(data.toString());

                if (info == null) {
                    callBack.failed(0, "");
                    return;
                }
                callBack.success(info);
            }

            @Override
            public void failed(int errorCode, Object data) {
                callBack.failed(errorCode, data);
            }
        });
    }

    public static void getResourceInfo(String item_id, final BaseCallBack callBack) {
        final String url = "http://platform-api.1yd.me/api/open/resources/day_resources?subgym_id=" + item_id;
        Log.i("info", url);
        HttpUtil.doHttpReqeust("GET", url, null, new BaseCallBack() {
            @Override
            public void success(Object data) {
                List<ResourseInfo> tempList = ParseResourseInfo.parseSubgymsInfo(data.toString());
                if (tempList == null) {
                    callBack.failed(0, "");
                    return;
                }
                callBack.success(tempList);
            }

            @Override
            public void failed(int errorCode, Object data) {

            }
        });
    }
}
