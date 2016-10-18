package com.qf.administrator.sports.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/9/12. 所有Activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//透明导航栏
        }
        super.onCreate(savedInstanceState);//你好
        //去掉actionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(setViewId());
        findViews();// 初始化控件
        initEvent();// 初始化点击事件
        init();// 初始化界面
        loadData();// 请求数据
    }

    protected abstract void findViews();

    protected abstract void initEvent();

    protected abstract void init();

    protected abstract void loadData();

    /**
     * 设置布局
     * @return
     */
    protected abstract int setViewId();
}
