package com.qf.administrator.sports.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/9/12.
 */
public abstract class BaseFragment extends Fragment
{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(setViewId(), container, false);
        findViews(view);// 初始化控件
        initEvent();// 初始化点击事件
        init();// 初始化界面
        loadData();// 请求数据
        return view;
    }

    protected abstract void findViews(View view);

    protected abstract void initEvent();

    protected abstract void init();

    protected abstract void loadData();

    /**
     * 设置布局
     * 
     * @return
     */
    protected abstract int setViewId();
}
