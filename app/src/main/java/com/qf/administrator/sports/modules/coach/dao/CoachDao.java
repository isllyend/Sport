package com.qf.administrator.sports.modules.coach.dao;

import com.qf.administrator.sports.i.BaseCallBack;
import com.qf.administrator.sports.modules.coach.bean.CoachInfo;
import com.qf.administrator.sports.modules.coach.util.CoachJson;
import com.qf.administrator.sports.modules.coach.util.URLAddress;
import com.qf.administrator.sports.net.HttpUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public class CoachDao {
    public static void getCoachInfoList(int array,int page,final BaseCallBack baseCallBack){

        HttpUtil.doHttpReqeust("GET", URLAddress.getUrlAddress(array,page), null, new BaseCallBack() {

            @Override
            public void success(Object data) {
                String result = data.toString();
                List<CoachInfo> tempList=CoachJson.getCoachJson(result);

                if (tempList==null){
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
