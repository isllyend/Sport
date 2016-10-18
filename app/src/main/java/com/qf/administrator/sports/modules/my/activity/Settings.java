package com.qf.administrator.sports.modules.my.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qf.administrator.sports.R;
import com.qf.administrator.sports.activity.BaseActivity;
import com.qf.administrator.sports.activity.FragmentMy;
import com.qf.administrator.sports.modules.my.bean.ExitEvent;
import com.qf.administrator.sports.modules.my.bean.LoginEvent;
import com.qf.administrator.sports.modules.my.bean.userinfo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
public class Settings extends BaseActivity {
    TextView aboutus;
    Button btnlogin;
    ImageView iv_back;
    @Override
    protected void findViews() {
        aboutus= (TextView) findViewById(R.id.line3);
        btnlogin= (Button) findViewById(R.id.btnlogin);
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
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(Settings.this,Aboutsports.class));
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!FragmentMy.islogin){
                    finish();
                    startActivity(new Intent(Settings.this,Login.class));
                }else{
                    EventBus.getDefault().post(new ExitEvent(R.mipmap.defaulthead,"未登录"));
                    FragmentMy.islogin=false;
                    finish();
                }
            }
        });
    }

    @Override
    protected void init() {
        if(!FragmentMy.islogin){
            btnlogin.setText("登录");
        }else{
            btnlogin.setText("退出登录");
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int setViewId() {
        return R.layout.settings;
    }
}
