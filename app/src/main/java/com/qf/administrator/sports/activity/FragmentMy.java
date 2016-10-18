package com.qf.administrator.sports.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qf.administrator.sports.R;
import com.qf.administrator.sports.modules.event.activity.MyEventActivity;
import com.qf.administrator.sports.modules.my.activity.Ddcx;
import com.qf.administrator.sports.modules.my.activity.Login;
import com.qf.administrator.sports.modules.my.activity.My_Credits;
import com.qf.administrator.sports.modules.my.activity.My_item;
import com.qf.administrator.sports.modules.my.activity.Settings;
import com.qf.administrator.sports.modules.my.activity.Sqfp;
import com.qf.administrator.sports.modules.my.activity.Tzbg;
import com.qf.administrator.sports.modules.my.activity.Wdkp;
import com.qf.administrator.sports.modules.my.activity.Wdzl;
import com.qf.administrator.sports.modules.my.activity.Ydgj;
import com.qf.administrator.sports.modules.my.bean.ChangeCreditsEvent;
import com.qf.administrator.sports.modules.my.bean.ChangeFaPiaoEvent;
import com.qf.administrator.sports.modules.my.bean.ChangeIconEvent;
import com.qf.administrator.sports.modules.my.bean.ChangeMoneyEvent;
import com.qf.administrator.sports.modules.my.bean.ChangeNicknameEvent;
import com.qf.administrator.sports.modules.my.bean.ExitEvent;
import com.qf.administrator.sports.modules.my.bean.LoginEvent;
import com.qf.administrator.sports.util.ImageLoaderUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class FragmentMy
        extends BaseFragment
        implements View.OnClickListener {
    public static boolean islogin = false;
    ImageView iv;
    My_item my_ddcx;
    My_item my_setting;
    My_item my_sqfp;
    My_item my_tzbg;
    My_item my_wdkp;
    My_item my_wdss;
    My_item my_wdzl;
    My_item my_ydgj;
    TextView nickname, tv_money, tv_credits, tv_fapiao;
    LinearLayout ll_mycredits;

    protected void findViews(View paramView) {
        iv = ((ImageView) paramView.findViewById(R.id.iv_icon));
        ll_mycredits= (LinearLayout) paramView.findViewById(R.id.llmy_credits);
        my_wdzl = ((My_item) paramView.findViewById(R.id.wdzl));
        my_ddcx = ((My_item) paramView.findViewById(R.id.ddcx));
        my_wdkp = ((My_item) paramView.findViewById(R.id.mykp));
        my_ydgj = ((My_item) paramView.findViewById(R.id.ydgj));
        my_sqfp = ((My_item) paramView.findViewById(R.id.sqfp));
        my_wdss = ((My_item) paramView.findViewById(R.id.myss));
        my_tzbg = ((My_item) paramView.findViewById(R.id.tzbg));
        my_setting = ((My_item) paramView.findViewById(R.id.settings));
        nickname = ((TextView) paramView.findViewById(R.id.nickname));
        tv_credits = (TextView) paramView.findViewById(R.id.my_credits);
        tv_money = (TextView) paramView.findViewById(R.id.my_money);
        tv_fapiao = (TextView) paramView.findViewById(R.id.my_fapiao);
    }

    protected void init() {
        EventBus.getDefault().register(this);
            nickname.setText("未登录");
    }

    protected void initEvent() {
        ll_mycredits.setOnClickListener(this);
        iv.setOnClickListener(this);
        my_wdzl.setOnClickListener(this);
        my_ddcx.setOnClickListener(this);
        my_wdkp.setOnClickListener(this);
        my_ydgj.setOnClickListener(this);
        my_sqfp.setOnClickListener(this);
        my_wdss.setOnClickListener(this);
        my_tzbg.setOnClickListener(this);
        my_setting.setOnClickListener(this);
    }

    protected void loadData() {
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_my;
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        if (!FragmentMy.islogin) {
            if (view.getId() == R.id.settings) {
                intent = new Intent(getActivity(), Settings.class);
            } else {
                intent = new Intent(getActivity(), Login.class);
            }
        } else {
            switch (view.getId()) {
                case R.id.wdzl:
                    intent = new Intent(getActivity(), Wdzl.class);
                    break;
                case R.id.ddcx:
                    intent = new Intent(getActivity(), Ddcx.class);
                    break;
                case R.id.mykp:
                    intent = new Intent(getActivity(), Wdkp.class);
                    break;
                case R.id.ydgj:
                    intent = new Intent(getActivity(), Ydgj.class);
                    break;
                case R.id.sqfp:
                    intent = new Intent(getActivity(), Sqfp.class);
                    break;
                case R.id.myss:
                    intent = new Intent(getActivity(), MyEventActivity.class);
                    break;
                case R.id.tzbg:
                    intent = new Intent(getActivity(), Tzbg.class);
                    break;
                case R.id.settings:
                    intent = new Intent(getActivity(), Settings.class);
                    break;
                case R.id.iv_icon:
                    intent = new Intent(getActivity(), Wdzl.class);
                    break;
                case R.id.llmy_credits:
                    intent = new Intent(getActivity(), My_Credits.class);
                    break;
            }
        }
        startActivity(intent);
    }

    @Subscribe
    public void onEventMainThread(LoginEvent event) {
            if(event.getUrl()!=null){
                ImageLoader.getInstance().displayImage(event.getUrl(), iv,
                        ImageLoaderUtil.getCircleOption(null, 0));
            }
            if(event.getNickname()!=null){
                nickname.setText(event.getNickname());
            }
            tv_money.setText(event.getMoney()+"元");
            tv_fapiao.setText(event.getFapiao()+"元");
            tv_credits.setText(event.getCredits()+"分");
    }
    @Subscribe
    public void onEventMainThread(ExitEvent event) {
        iv.setImageResource(event.getDefaulticon());
        nickname.setText(event.getState());
        tv_money.setText(0.00+"");
        tv_fapiao.setText(0.00+"");
        tv_credits.setText(0+"");
    }
    @Subscribe
    public void onEventMainThread(ChangeIconEvent event) {
        ImageLoader.getInstance().displayImage(event.getUrl(), iv,
                ImageLoaderUtil.getCircleOption(null, 0));
    }
    @Subscribe
    public void onEventMainThread(ChangeNicknameEvent event) {

    }
    @Subscribe
    public void onEventMainThread(ChangeFaPiaoEvent event) {
        tv_fapiao.setText(event.getFapiao()+"元");
    }
    @Subscribe
    public void onEventMainThread(ChangeCreditsEvent event) {
        tv_credits.setText(event.getCredits()+"分");
    }
    @Subscribe
    public void onEventMainThread(ChangeMoneyEvent event) {
        tv_money.setText(event.getMoney()+"元");
    }
}
