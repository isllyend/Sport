package com.qf.administrator.sports.modules.my.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import static android.content.pm.PackageManager.GET_UNINSTALLED_PACKAGES;

/**
 * Created by kira on 2016/9/9 0009.
 */
public class MyPrimaryUtil {
    //判断联网
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    //dip to px
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    //px to dip
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //format long time
    public static String DateFormat(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }

    public static boolean isInstalledApp(Context context, String packageName) {
        Boolean flag = false;

        try {
            PackageManager pm = context.getPackageManager();
            List<PackageInfo> pkgs = pm.getInstalledPackages(GET_UNINSTALLED_PACKAGES);
            for (PackageInfo pkg : pkgs) {
                // 当找到了名字和该包名相同的时候，返回
                if ((pkg.packageName).equals(packageName)) {
                    return flag = true;
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return flag;
    }

    public static void install(Context context, String fileName) {
        if (TextUtils.isEmpty(fileName) || context == null) {
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getLoginTimes(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("LoginTimes", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //判断是不是首次登录，
        if (preferences.getInt("times", 1) == 1) {
            editor.putInt("times", 2);
            editor.commit();
            return 0;
        } else {
            int time = preferences.getInt("times", 1);
            editor.putInt("times", ++time);
            editor.commit();
            return time;
        }
    }

    //获取屏幕宽高px和密度
    public static float getScreenDensity(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        return dm.density;
    }

    public static float getScreenDensityDpi(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        // 屏幕密度（每寸像素：120/160/240/320）
        return dm.densityDpi;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    //随机生成中文
    public static String generateCN(int nums) {
        String resultstr = "";
        for (int i = 0; i < nums; i++) {
            String str = null;
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39)));// 获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93)));// 获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                str = new String(b, "GBk");
            } catch (UnsupportedEncodingException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }// 转成中文
            resultstr += str;
        }
        return resultstr;
    }

    //随机产生英文
    public static String generateEN(int nums) {
        String resultstr = "";
        for (int i = 0; i < nums; i++) {
            String str = null;
            str = (char) (Math.random() * 26 + 'A') + "";
            resultstr += str;
        }
        return resultstr;
    }
    //随机产生数字
    public static String generateNum(int nums) {
        String resultstr = "";
        for (int i = 0; i < nums; i++) {
            String str = null;
            str = (int)(Math.random()*10) + "";
            resultstr += str;
        }
        return resultstr;
    }
    //随机产生颜色RGB
    public static int generateRandomColorCode() {
        Random random = new Random();
        return Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    //解决内存溢出：将图片转换成bitmap
    public static Bitmap getBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
    public static Bitmap getBitMap(Context context, int resId, int sampleSize) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;//内存可被回收
        opt.inSampleSize = sampleSize;
        opt.inInputShareable = true;
        //获取资源图片
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }
    public static Bitmap getBitMap(Context context, String filepath, int sampleSize) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;//内存可被回收
        opt.inSampleSize = sampleSize;
        opt.inInputShareable = true;
        //获取资源图片
        return BitmapFactory.decodeFile(filepath);
    }
    public static void getBitMap(final String urlpath,final int sampleSize,final Bitmap_CallBack bitmap_callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inPreferredConfig = Bitmap.Config.RGB_565;
                opt.inPurgeable = true;//内存可被回收
                opt.inSampleSize = sampleSize;
                opt.inInputShareable = true;
                Bitmap map = null;
                try {
                    URL url = new URL(urlpath);
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    InputStream in;
                    in = conn.getInputStream();
                    map = BitmapFactory.decodeStream(in);
                    bitmap_callBack.getBitmap(map);
                    // TODO Auto-generated catch block
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    //根据地址获取json字符串,仅支持get
    public static String getJsonString(String str) {
        String res = "";
        HttpURLConnection huc = null;
        BufferedReader br = null;
        try {
            URL url = new URL(str);
            huc = (HttpURLConnection) url.openConnection();
            InputStream is = huc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String s;
            while ((s = br.readLine()) != null) {
                res += s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                huc.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }
    //    HashMap<String,String> params=new HashMap<>();
//    params.put("platform","2");
//    params.put("page",page+"");
    //get or post获取json字符串
    public static void doHttpReqeust(final String POST_GET, final String url,
                                     final HashMap<String, String> params,final Json_CallBack json_callBack) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL u = new URL(url);
                        // 打开连接
                        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                        // 设置输入可用
                        connection.setDoInput(true);
                        // 设置输出可用
                        connection.setDoOutput(true);
                        // 设置请求方式
                        connection.setRequestMethod(POST_GET);
                        // 设置连接超时
                        connection.setConnectTimeout(5000);
                        // 设置读取超时
                        connection.setReadTimeout(5000);
                        // 设置缓存不可用
                        connection.setUseCaches(false);
                        // 开始连接
                        connection.connect();
                        // 只有当POST请求时才会执行此代码段
                        if (params != null) {
                            // 获取输出流,connection.getOutputStream已经包含了connect方法的调用
                            OutputStream outputStream = connection
                                    .getOutputStream();
                            StringBuilder sb = new StringBuilder();
                            Set<Map.Entry<String, String>> sets = params.entrySet();
                            // 将Hashmap转换为string
                            for (Map.Entry<String, String> entry : sets) {
                                sb.append(entry.getKey()).append("=")
                                        .append(entry.getValue()).append("&");
                            }
                            String param = sb.substring(0, sb.length() - 1);
                            // 使用输出流将string类型的参数写到服务器
                            outputStream.write(param.getBytes());
                            outputStream.flush();
                        }
                        // 当返回码为200时才读取数据
                        if (connection.getResponseCode() == 200) {
                            InputStream is = connection.getInputStream();
                            InputStreamReader isr = new InputStreamReader(is);
                            BufferedReader br = new BufferedReader(isr);
                            String s;
                            String str = "";
                            while ((s = br.readLine()) != null) {
                                str += s;
                            }
                            json_callBack.getJson(str);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //回调接口
    private static Json_CallBack json_callBack;
    private static Bitmap_CallBack bitmap_callBack;
    public interface Json_CallBack {
        public void getJson(String str);
    }
    public interface Bitmap_CallBack {
        public void getBitmap(Bitmap bitmap);
    }
    //modify 16/9/28

    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式：验证手机号
     */
//    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    public static final String REGEX_MOBILE = "^1([3]|[5]|[8])\\d{9}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证大于0数字
     */
    public static final String REGEX_NUM = "^[1-9]\\d*$";
    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w\\-\\ ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    public static boolean isNums(String nums) {
        return Pattern.matches(REGEX_NUM, nums);
    }
    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }
}
