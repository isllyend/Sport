package com.qf.administrator.sports.modules.my.activity;

import android.view.View;
import android.widget.ImageView;

import com.qf.administrator.sports.R;
import com.qf.administrator.sports.activity.BaseActivity;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
public class Wdkp extends BaseActivity {
    ImageView iv_back;
    @Override
    protected void findViews() {
        iv_back= (ImageView) findViewById(R.id.iv_left);
    }

    @Override
    protected void initEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int setViewId() {
        return R.layout.wdzh;
    }
}
