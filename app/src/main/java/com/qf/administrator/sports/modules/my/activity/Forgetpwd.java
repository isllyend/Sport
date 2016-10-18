package com.qf.administrator.sports.modules.my.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.qf.administrator.sports.R;
import com.qf.administrator.sports.activity.BaseActivity;
import com.qf.administrator.sports.activity.FragmentMy;
import com.qf.administrator.sports.modules.my.bean.LoginEvent;
import com.qf.administrator.sports.modules.my.bean.userinfo;
import com.qf.administrator.sports.modules.my.widget.MyPrimaryUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.qf.administrator.sports.R.id.forget_phonenum;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class Forgetpwd extends BaseActivity implements View.OnClickListener {
    EditText et_phone, et_check, et_pwd;
    Button btnsend, btnmodify;
    String checkcode;
    ImageView iv_back;
    @Override
    protected void findViews() {
        et_phone = (EditText) findViewById(R.id.forget_phonenum);
        et_check = (EditText) findViewById(R.id.forget_check);
        et_pwd = (EditText) findViewById(R.id.forget_pwd);
        btnsend = (Button) findViewById(R.id.forget_send);
        btnmodify = (Button) findViewById(R.id.forget_btnmodify);
        iv_back= (ImageView) findViewById(R.id.iv_left);
    }

    @Override
    protected void initEvent() {
        btnsend.setOnClickListener(this);
        btnmodify.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.forget_send:
                if(!MyPrimaryUtil.isMobile(et_phone.getText().toString())){
                    Toast.makeText(this, "手机号不合法，请重新输入!", Toast.LENGTH_SHORT).show();
                    return;
                }
                BmobSMS.requestSMSCode(et_phone.getText().toString(),"我要运动", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer smsId,BmobException ex) {
                        if(ex==null){//验证码发送成功
                            Toast.makeText(Forgetpwd.this, "验证码已发送，请注意查收!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.forget_btnmodify:

                if(et_check.getText().toString()==null||"".equals(et_check.getText().toString())){
                    Toast.makeText(this, "验证码未填写!", Toast.LENGTH_SHORT).show();
                    return;
                }


//                if(checkcode==null){
//                    Toast.makeText(this, "请先获取验证码!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (!checkcode.equals(et_check.getText().toString())) {
//                    Toast.makeText(this, "验证码不正确，请重新输入!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if(!MyPrimaryUtil.isPassword(et_pwd.getText().toString())){
                    Toast.makeText(this, "密码不合法，请重新输入!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!MyPrimaryUtil.isMobile(et_phone.getText().toString())){
                    Toast.makeText(this, "手机号不合法，请重新输入!", Toast.LENGTH_SHORT).show();
                    return;
                }//用户登录状态才能更新user表，故先登录一个admin账号
//                adminlogin();
//                BmobQuery<userinfo> users = new BmobQuery<userinfo>();
//                users.addWhereEqualTo("username", et_phone.getText().toString());
//                users.findObjects(new FindListener<userinfo>() {
//                    @Override
//                    public void done(List<userinfo> list, BmobException e) {
//                        if (list == null) {
//                            Toast.makeText(Forgetpwd.this, "请输入正确的手机号码!", Toast.LENGTH_SHORT).show();
//                        }else{
//                            userinfo user=list.get(0);
//                            user.setSessionToken(user.getSessionToken());
//                            user.setPassword(et_pwd.getText().toString());
//                            user.update(user.getObjectId(), new UpdateListener() {
//                                @Override
//                                public void done(BmobException e) {
//                                    if (e == null) {
//                                        Toast.makeText(Forgetpwd.this, "重置密码成功", Toast.LENGTH_SHORT).show();
//                                        finish();
//                                    } else {
//                                        Toast.makeText(Forgetpwd.this, "重置密码失败:Msg"+e.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                        }
//                    }
//                });
                BmobUser.resetPasswordBySMSCode(et_check.getText().toString(),et_pwd.getText().toString(), new UpdateListener() {
                    @Override
                    public void done(BmobException ex) {
                        if(ex==null){
                            Log.i("smile", "密码重置成功");
                        }else{
                            Log.i("smile", "重置失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
                        }
                    }
                });
                break;
        }
    }
    @Override
    protected int setViewId() {
        return R.layout.forgetpw;
    }

    private void openNormalNotification1() {
        //通知是由构建者模式来创建的
        Notification.Builder builder = new Notification.Builder(this);
        //将通知设置自动取消，也就是点击后会消失
        builder.setAutoCancel(true);
        //设置通知在状态栏上的图标
        //一般用于表示消息发送者的头像或是消息由应用中哪个模块发送的
        builder.setSmallIcon(R.mipmap.logo_email);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.mailbg));
        //设置通知的标题
        builder.setContentTitle("Insist运动");
        //设置通知的正文
        checkcode = MyPrimaryUtil.generateNum(6);
        builder.setContentText("您的验证码为:" + checkcode);
        //设置通知的信息
        builder.setContentInfo("60s有效,请尽快使用!");
        /**
         * 设置通知提醒时的表现形式
         *1.DEFAULT_ALL
         * 2.DEFAULT_SOUND 声音
         * 2.DEFAULT_VIBRATE 振动
         * 2.DEFAULT_LIGHTS 闪光
         */
//        builder.setDefaults(Notification.DEFAULT_ALL);
        //设置自定义的声音资源
//        builder.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification));
        //设置振动(参数中数组的含义：停顿两秒//振动两秒//停顿两秒//振动两秒)
//        builder.setVibrate(new long[]{2000, 2000, 2000, 2000});

//        Intent intent = new Intent(this, ShowNotificationActivity.class);
//        intent.putExtra("notify", "来自通知一的消息");
//        //等待意图，预加载的意图，用于等待用户点击通知时的操作
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
//
        Notification notification = builder.build();
//        notification.flags=Notification.FLAG_NO_CLEAR;//用户无法自行取消,只能通过NotificationManager的cancel来取消
//
//        //builder.setWhen();定时发送通知，可以设置未来时间
//        //通过系统服务来获取通知管理器
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        //显示通知
        manager.notify(0, notification);
    }
}
