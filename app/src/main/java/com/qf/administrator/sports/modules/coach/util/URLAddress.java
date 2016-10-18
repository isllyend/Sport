package com.qf.administrator.sports.modules.coach.util;

/**
 * Created by Administrator on 2016/10/10.
 */
public class URLAddress {
     public static String getUrlAddress(int i,int page){
            String [] strings={
                    //网球
                    "http://platform-api.1yd.me/api/coaches/search?category_id=1&is_has_field=&size=10&page="+page,
                    //羽毛球：
                    "http://platform-api.1yd.me/api/coaches/search?category_id=2&is_has_field=&size=10&page="+page,
                    //乒乓球：
                    "http://platform-api.1yd.me/api/coaches/search?category_id=21&is_has_field=&size=10&page="+page,
                    //足球：
                    "http://platform-api.1yd.me/api/coaches/search?category_id=23&is_has_field=&size=10&page="+page,
                    //篮球
                    "http://platform-api.1yd.me/api/coaches/search?category_id=61&is_has_field=&size=10&page="+page,
                    //企业健身
                    "http://platform-api.1yd.me/api/coaches/search?category_id=127&is_has_field=&size=10&page="+page,
                    //健身
                    "http://platform-api.1yd.me/api/coaches/search?category_id=149&is_has_field=&size=10&page="+page,
                    //瑜伽：
                    "http://platform-api.1yd.me/api/coaches/search?category_id=150&is_has_field=&size=10&page="+page,

                    //游泳
                    "http://platform-api.1yd.me/api/coaches/search?category_id=151&is_has_field=&size=10&page="+page,
                    //太极拳：
                    "http://platform-api.1yd.me/api/coaches/search?category_id=152&is_has_field=&size=10&page="+page,
                    //跆拳道：
                    "http://platform-api.1yd.me/api/coaches/search?category_id=153&is_has_field=&size=10&page="+page,
                    //武术：
                    "http://platform-api.1yd.me/api/coaches/search?category_id=161&is_has_field=&size=10&page="+page,
                    //舞蹈：
                    "http://platform-api.1yd.me/api/coaches/search?category_id=162&is_has_field=&size=10&page="+page,
                    //跑步：
                    "http://platform-api.1yd.me/api/coaches/search?category_id=163&is_has_field=&size=10&page="+page,
                    //其他：
                    "http://platform-api.1yd.me/api/coaches/search?category_id=165&is_has_field=&size=10&page="+page
            };

         return strings[i];

     }






}
