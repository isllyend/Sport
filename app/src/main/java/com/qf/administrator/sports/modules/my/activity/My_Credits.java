package com.qf.administrator.sports.modules.my.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qf.administrator.sports.R;
import com.qf.administrator.sports.activity.BaseActivity;
import com.qf.administrator.sports.activity.FragmentMy;
import com.qf.administrator.sports.modules.my.bean.ChangeCreditsEvent;
import com.qf.administrator.sports.modules.my.bean.userinfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2016/10/14 0014.
 */
public class My_Credits extends BaseActivity{
    TextView tv_credits;
    Button btn_duihuan;
    ImageView iv_back;
    @Override
    protected void findViews() {
        tv_credits= (TextView) findViewById(R.id.jifen);
        btn_duihuan= (Button) findViewById(R.id.btn_duihuan);
        iv_back= (ImageView) findViewById(R.id.iv_left);
    }

    @Override
    protected void initEvent() {
        btn_duihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(My_Credits.this,BeanShop.class));
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void init() {
        tv_credits.setText(userinfo.getCurrentUser(userinfo.class).getCredits()+"");
        EventBus.getDefault().register(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int setViewId() {
        return R.layout.my_credits;
    }
    @Subscribe
    public void onEventMainThread(ChangeCreditsEvent event) {
        tv_credits.setText(event.getCredits()+"");
    }
}
