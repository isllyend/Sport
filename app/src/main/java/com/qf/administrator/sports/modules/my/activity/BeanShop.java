package com.qf.administrator.sports.modules.my.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qf.administrator.sports.R;
import com.qf.administrator.sports.activity.BaseActivity;
import com.qf.administrator.sports.activity.FragmentMy;
import com.qf.administrator.sports.modules.my.bean.ChangeBeanEvent;
import com.qf.administrator.sports.modules.my.bean.ChangeCreditsEvent;
import com.qf.administrator.sports.modules.my.bean.userinfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2016/10/14 0014.
 */
public class BeanShop extends BaseActivity implements View.OnClickListener {
    TextView tv_bean, tv_credits;
    RelativeLayout re_30, re_50, re_100, re_200;
    ImageView iv_back;
    @Override
    protected void findViews() {
        tv_bean = (TextView) findViewById(R.id.tv_shopbean);
        tv_credits = (TextView) findViewById(R.id.tv_shopcredits);
        re_30 = (RelativeLayout) findViewById(R.id.bean_30);
        re_50 = (RelativeLayout) findViewById(R.id.bean_50);
        re_100 = (RelativeLayout) findViewById(R.id.bean_100);
        re_200 = (RelativeLayout) findViewById(R.id.bean_200);
        iv_back= (ImageView) findViewById(R.id.iv_left);
    }

    @Override
    protected void initEvent() {
        re_30.setOnClickListener(this);
        re_50.setOnClickListener(this);
        re_100.setOnClickListener(this);
        re_200.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void init() {
        EventBus.getDefault().register(this);
        userinfo user = userinfo.getCurrentUser(userinfo.class);
        tv_bean.setText(user.getBean()+"");
        tv_credits.setText(user.getCredits()+"");
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int setViewId() {
        return R.layout.creditsshop;
    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.bean_30:
                intent = new Intent(BeanShop.this, BeanDetail.class);
                intent.putExtra("bean",30);
                break;
            case R.id.bean_50:
                intent = new Intent(BeanShop.this, BeanDetail.class);
                intent.putExtra("bean",50);
                break;
            case R.id.bean_100:
                intent = new Intent(BeanShop.this, BeanDetail.class);
                intent.putExtra("bean",100);
                break;
            case R.id.bean_200:
                intent = new Intent(BeanShop.this, BeanDetail.class);
                intent.putExtra("bean",200);
                break;
        }
        startActivity(intent);
    }
    @Subscribe
    public void onEventMainThread(ChangeCreditsEvent event) {
        tv_credits.setText(event.getCredits()+"");
    }
    @Subscribe
    public void onEventMainThread(ChangeBeanEvent event) {
        tv_bean.setText(event.getBean()+"");
    }
}
