package com.qf.administrator.sports.modules.my.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qf.administrator.sports.R;
import com.qf.administrator.sports.activity.BaseActivity;
import com.qf.administrator.sports.activity.FragmentMy;
import com.qf.administrator.sports.modules.my.bean.ChangeBeanEvent;
import com.qf.administrator.sports.modules.my.bean.ChangeCreditsEvent;
import com.qf.administrator.sports.modules.my.bean.ChangeNicknameEvent;
import com.qf.administrator.sports.modules.my.bean.userinfo;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.qf.administrator.sports.R.id.bean_detail;

/**
 * Created by Administrator on 2016/10/14 0014.
 */
public class BeanDetail extends BaseActivity{

    TextView tv_title,tv_count;
    Button btn_duihuan;
    int count;
    int bean;
    ImageView iv_back;
    @Override
    protected void findViews() {
        tv_title= (TextView) findViewById(bean_detail);
        tv_count= (TextView) findViewById(R.id.bean_count);
        btn_duihuan= (Button) findViewById(R.id.detail_duihuan);
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
        btn_duihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userinfo.getCurrentUser(userinfo.class).getCredits()<count){
                    Toast.makeText(BeanDetail.this,"积分不够了",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(BeanDetail.this,"兑换运动豆成功",Toast.LENGTH_SHORT).show();
                    userinfo user = userinfo.getCurrentUser(userinfo.class);
                    final int afterBean=user.getBean()+bean;
                    final int aftercredits=user.getCredits()-count;
                    user.setCredits(aftercredits);
                    user.setBean(afterBean);
                    user.update(user.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(BeanDetail.this, "兑换成功", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new ChangeCreditsEvent(aftercredits));
                                EventBus.getDefault().post(new ChangeBeanEvent(afterBean));
                                finish();
                            } else {
                                Toast.makeText(BeanDetail.this, "兑换失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void init() {
        Intent intent=getIntent();
        bean=intent.getIntExtra("bean",0);
        switch (bean){
            case 30:
                count=300;
                tv_title.setText("我要运动"+bean+"运动豆");
                tv_count.setText(count+"");
                break;
            case 50:
                count=490;
                tv_title.setText("我要运动"+bean+"运动豆");
                tv_count.setText(count+"");
                break;
            case 100:
                count=970;
                tv_title.setText("我要运动"+bean+"运动豆");
                tv_count.setText(count+"");
                break;
            case 200:
                count=1900;
                tv_title.setText("我要运动"+bean+"运动豆");
                tv_count.setText(count+"");
                break;
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int setViewId() {
        return R.layout.bean__detail;
    }
}
