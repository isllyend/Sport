package com.qf.administrator.sports.modules.my.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qf.administrator.sports.R;
import com.qf.administrator.sports.activity.BaseActivity;
import com.qf.administrator.sports.activity.FragmentMy;
import com.qf.administrator.sports.modules.my.bean.ChangeFaPiaoEvent;
import com.qf.administrator.sports.modules.my.bean.ChangeIconEvent;
import com.qf.administrator.sports.modules.my.bean.userinfo;
import com.qf.administrator.sports.modules.my.widget.MyPrimaryUtil;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
public class Sqfp extends BaseActivity implements View.OnClickListener {
    LinearLayout ll_weixin, ll_zhifubao, ll_edu, ll_address;
    ImageView iv_weixin, iv_zhifubao,iv_back;
    Button btnok;
    TextView tv_edu, tv_taitou, tv_getperson, tv_phone, tv_address, tv_out, tv_outhigh;

    @Override
    protected void findViews() {
        iv_back= (ImageView) findViewById(R.id.iv_left);
        ll_edu = (LinearLayout) findViewById(R.id.fp_line1);
        ll_address = (LinearLayout) findViewById(R.id.fp_line5);
        ll_weixin = (LinearLayout) findViewById(R.id.fp_line6);
        ll_zhifubao = (LinearLayout) findViewById(R.id.fp_line7);
        btnok = (Button) findViewById(R.id.fp_btnok);
        iv_weixin = (ImageView) findViewById(R.id.fp_weixin_state);
        iv_zhifubao = (ImageView) findViewById(R.id.fp_zhifubao_state);
        tv_edu = (TextView) findViewById(R.id.fp_count);
        tv_taitou = (TextView) findViewById(R.id.fp_nickname);
        tv_getperson = (TextView) findViewById(R.id.fp_getperson);
        tv_phone = (TextView) findViewById(R.id.fp_phone);
        tv_address = (TextView) findViewById(R.id.fp_address);
        tv_out = (TextView) findViewById(R.id.ykmoney);
        tv_outhigh = (TextView) findViewById(R.id.highmoney);
    }

    @Override
    protected void initEvent() {
        iv_back.setOnClickListener(this);
        ll_weixin.setOnClickListener(this);
        ll_zhifubao.setOnClickListener(this);
        btnok.setOnClickListener(this);
        ll_edu.setOnClickListener(this);
        ll_address.setOnClickListener(this);
    }

    @Override
    protected void init() {
        if (userinfo.getCurrentUser(userinfo.class).getAddress() != null) {
            tv_address.setText(userinfo.getCurrentUser(userinfo.class).getAddress());
        }
        if (userinfo.getCurrentUser(userinfo.class).getNickname() != null) {
            tv_taitou.setText(userinfo.getCurrentUser(userinfo.class).getNickname());
            tv_getperson.setText(userinfo.getCurrentUser(userinfo.class).getNickname());
        } else {
            tv_taitou.setText(userinfo.getCurrentUser(userinfo.class).getUsername());
            tv_getperson.setText(userinfo.getCurrentUser(userinfo.class).getUsername());
        }
        tv_phone.setText(userinfo.getCurrentUser(userinfo.class).getUsername());
        tv_outhigh.setText("最高可开金额" +userinfo.getCurrentUser(userinfo.class).getFapiao() + "元");
        tv_out.setText("已开金额" + userinfo.getCurrentUser(userinfo.class).getFapiaoout() + "元");
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int setViewId() {
        return R.layout.sqfp;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.fp_line6:
                iv_weixin.setImageResource(R.mipmap.balence_gou);
                iv_zhifubao.setImageResource(0);
                break;
            case R.id.fp_line7:
                iv_zhifubao.setImageResource(R.mipmap.balence_gou);
                iv_weixin.setImageResource(0);
                break;
            case R.id.fp_btnok:
                if (tv_edu.getText().toString() != null && !tv_edu.getText().toString().equals("")) {
                    userinfo user =userinfo.getCurrentUser(userinfo.class);
                    user.setAddress(tv_address.getText().toString());
                    final Double lostmoney=user.getFapiao()-Double.valueOf(tv_edu.getText().toString());
                    Double out=user.getFapiaoout()+Double.valueOf(tv_edu.getText().toString());
                    user.setFapiao(lostmoney);
                    user.setFapiaoout(out);
                    user.update(user.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(Sqfp.this, "您的申请已发送，请耐心等候处理结果", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new ChangeFaPiaoEvent(lostmoney));
                                finish();
                            } else {
                                Toast.makeText(Sqfp.this, "您的申请发送失败，请尝试重新提交", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "发票金额还未填写", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fp_line1:
                editfapiaomoney();
                break;
            case R.id.fp_line5:
                editaddress();
                break;
        }
    }

    public void editfapiaomoney() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("发票额度");    //设置对话框标题
        builder.setIcon(android.R.drawable.ic_menu_edit);    //设置对话框标题前的图标
        final EditText edit = new EditText(this);
        edit.setText(userinfo.getCurrentUser(userinfo.class).getFapiao() + "");
        builder.setView(edit);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputmoney = edit.getText().toString();
                if (!MyPrimaryUtil.isNums(inputmoney)) {
                    Toast.makeText(Sqfp.this, "请输入大于0的整数", Toast.LENGTH_SHORT).show();
                    return;
                }
                Double money = Double.valueOf(edit.getText().toString());
                if (money > userinfo.getCurrentUser(userinfo.class).getFapiao()) {
                    Toast.makeText(Sqfp.this, "发票金额只能小于或等于最高金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                tv_edu.setText(edit.getText().toString());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(true);    //设置按钮是否可以按返回键取消,false则不可以取消
        AlertDialog dialog = builder.create();    //创建对话框
        dialog.setCanceledOnTouchOutside(true);    //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
        dialog.show();
    }

    public void editaddress() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("收件地址");    //设置对话框标题
        builder.setIcon(android.R.drawable.ic_menu_edit);    //设置对话框标题前的图标
        final EditText edit = new EditText(this);
        edit.setText(userinfo.getCurrentUser(userinfo.class).getAddress());
        builder.setView(edit);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_address.setText(edit.getText().toString());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(true);    //设置按钮是否可以按返回键取消,false则不可以取消
        AlertDialog dialog = builder.create();    //创建对话框
        dialog.setCanceledOnTouchOutside(true);    //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
        dialog.show();
    }
}
