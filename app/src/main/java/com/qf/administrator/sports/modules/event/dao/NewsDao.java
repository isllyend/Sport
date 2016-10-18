package com.qf.administrator.sports.modules.event.dao;

import com.qf.administrator.sports.i.BaseCallBack;
import com.qf.administrator.sports.modules.event.bean.NewsInfo;
import com.qf.administrator.sports.modules.event.util.HttpUtil;
import com.qf.administrator.sports.modules.event.util.ParseNewsInfo;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Chigo on 10/09/2016.
 */

public class NewsDao {
    public static void getNewsInfo( int page,final BaseCallBack baseCallBack){
        HttpUtil.doHttpReqeustNews("GET","http://apis.baidu.com/txapi/tiyu/tiyu?num=10&&page="+page, null,new BaseCallBack() {
            @Override
            public void success(Object data) {
                List<NewsInfo> tempList= ParseNewsInfo.parseNewsInfo(data.toString());
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
    public static void getNewsInfo(int page,String word,final BaseCallBack baseCallBack){
        String w= URLEncoder.encode(word);
        HttpUtil.doHttpReqeustNews("GET","http://apis.baidu.com/txapi/tiyu/tiyu?num=20&&page="+page+"&&word="+w, null,new BaseCallBack() {
            @Override
            public void success(Object data) {
                List<NewsInfo> tempList= ParseNewsInfo.parseNewsInfo(data.toString());
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
