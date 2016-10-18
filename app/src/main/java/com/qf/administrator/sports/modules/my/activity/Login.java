package com.qf.administrator.sports.modules.my.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import com.qf.administrator.sports.R;
import com.qf.administrator.sports.activity.BaseActivity;
import com.qf.administrator.sports.activity.FragmentMy;
import com.qf.administrator.sports.modules.my.bean.LoginEvent;
import com.qf.administrator.sports.modules.my.bean.userinfo;

import org.greenrobot.eventbus.EventBus;

public class Login
        extends BaseActivity
        implements View.OnClickListener {
    Button btnlogin;
    EditText et_pwd;
    EditText et_username;
    TextView tv_forget;
    TextView tv_register;
    ImageView iv_back;

    protected void findViews() {
        tv_register = ((TextView) findViewById(R.id.tvregister));
        tv_forget = ((TextView) findViewById(R.id.tvforgetpw));
        et_username = ((EditText) findViewById(R.id.login_username));
        et_pwd = ((EditText) findViewById(R.id.login_password));
        btnlogin = ((Button) findViewById(R.id.btnlogin));
        iv_back= (ImageView) findViewById(R.id.iv_left);
    }

    protected void init() {
    }

    protected void initEvent() {
        tv_register.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        btnlogin.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    protected void loadData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tvregister:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.tvforgetpw:
                startActivity(new Intent(this, Forgetpwd.class));
                break;
            case R.id.btnlogin:
                final userinfo user = new userinfo();
                user.setUsername(et_username.getText().toString());
                user.setPassword(et_pwd.getText().toString());
                user.login(new SaveListener<userinfo>() {
                    @Override
                    public void done(userinfo userinfo, BmobException e) {
                        if (e == null) {
                            Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                            FragmentMy.islogin = true;
                            finish();
                            EventBus.getDefault().
                                    post(new LoginEvent(userinfo.getCurrentUser(userinfo.class).getImage() == null ? null : userinfo.getCurrentUser(userinfo.class).getImage().getUrl(),
                                            userinfo.getCurrentUser(userinfo.class).getNickname(),
                                            userinfo.getCurrentUser(userinfo.class).getMoney(),
                                            userinfo.getCurrentUser(userinfo.class).getFapiao(),
                                            userinfo.getCurrentUser(userinfo.class).getCredits()
                                    ));
                        } else {
                            Toast.makeText(Login.this, "请检查用户名或密码是否正确", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    protected int setViewId() {
        return R.layout.login;
    }
}
