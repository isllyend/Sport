package com.qf.administrator.sports.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qf.administrator.sports.R;
import com.qf.administrator.sports.modules.venue.bean.EventBean;
import com.qf.administrator.sports.widget.MyDrawerLayout;
import com.qf.administrator.sports.widget.MyDrawerView;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    Fragment fragment_venue, fragment_coach, fragment_event, fragment_my, lastfragment,mCurrentFragment;
    LinearLayout ll_venue, ll_coach, ll_event, ll_my, lastll;
    private MyDrawerView drawerView;
    int index = 0;
    public static FragmentTransaction transaction;
    private MyDrawerLayout drawerLayout;

    @Override
    protected void findViews() {
        ll_venue = (LinearLayout) findViewById(R.id.ll_cg);
        ll_coach = (LinearLayout) findViewById(R.id.ll_jl);
        ll_event = (LinearLayout) findViewById(R.id.ll_ss);
        ll_my = (LinearLayout) findViewById(R.id.ll_my);
        lastll = ll_venue;
        drawerLayout= (MyDrawerLayout) findViewById(R.id.drawerlayout);
        drawerView= (MyDrawerView) findViewById(R.id.drawer_right);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initEvent() {
        ll_venue.setOnClickListener(this);
        ll_coach.setOnClickListener(this);
        ll_event.setOnClickListener(this);
        ll_my.setOnClickListener(this);
    }

    @Override
    protected void init() {
        fragment_venue = new FragmentVenue();
        fragment_coach = new FragmentCoach();
        fragment_event = new FragmentEvent();
        fragment_my = new FragmentMy();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, fragment_venue);
        transaction.add(R.id.fragment_container, fragment_coach);
        transaction.add(R.id.fragment_container, fragment_event);
        transaction.add(R.id.fragment_container, fragment_my);
        transaction.hide(fragment_coach);
        transaction.hide(fragment_event);
        transaction.hide(fragment_my);
        lastfragment = fragment_venue;
        transaction.commit();
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        Bundle bundle=new Bundle();
        EventBean eventBean=new EventBean();
        eventBean.setDrawerLayout(drawerLayout);
        eventBean.setDrawerView(drawerView);
        bundle.putSerializable("eventBean",eventBean);
        fragment_venue.setArguments(bundle);
    }

    @Override
    protected int setViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        LinearLayout ll = (LinearLayout) v;
        if (ll == lastll) {
            return;
        }
        TextView tv = null;
        ImageView iv;
        TextView tvold = null;
        ImageView ivold;
        //将和当前菜单绑定的fragment显示出来
        transaction = getSupportFragmentManager().beginTransaction();
        switch (index) {
            case 0:
                tvold = (TextView) ll_venue.findViewById(R.id.tv_cg);
                ivold = (ImageView) ll_venue.findViewById(R.id.iv_cg);
                ivold.setImageResource(R.mipmap.venue);
                break;
            case 1:
                tvold = (TextView) ll_coach.findViewById(R.id.tv_jl);
                ivold = (ImageView) ll_coach.findViewById(R.id.iv_jl);
                ivold.setImageResource(R.mipmap.coach_pre);
                break;
            case 2:
                tvold = (TextView) ll_event.findViewById(R.id.tv_ss);
                ivold = (ImageView) ll_event.findViewById(R.id.iv_ss);
                ivold.setImageResource(R.mipmap.event_cap_no);
                break;
            case 3:
                tvold = (TextView) ll_my.findViewById(R.id.tv_my);
                ivold = (ImageView) ll_my.findViewById(R.id.iv_my);
                ivold.setImageResource(R.mipmap.self_pre);
                break;
        }
        tvold.setTextColor(Color.parseColor("#C1C0C1"));
        if (lastll != null) {
            transaction.hide(lastfragment);
            switch (v.getId()) {
                case R.id.ll_cg:
                    tv = (TextView) v.findViewById(R.id.tv_cg);
                    iv = (ImageView) v.findViewById(R.id.iv_cg);
                    iv.setImageResource(R.mipmap.venue_on);
                    transaction.show(fragment_venue);
                    lastfragment = fragment_venue;
                    lastll=ll_venue;
                    index = 0;
                    break;
                case R.id.ll_jl:
                    tv = (TextView) v.findViewById(R.id.tv_jl);
                    iv = (ImageView) v.findViewById(R.id.iv_jl);
                    iv.setImageResource(R.mipmap.coach_on);
                    transaction.show(fragment_coach);
                    lastfragment = fragment_coach;
                    lastll=ll_coach;
                    index = 1;
                    break;
                case R.id.ll_ss:
                    tv = (TextView) v.findViewById(R.id.tv_ss);
                    iv = (ImageView) v.findViewById(R.id.iv_ss);
                    iv.setImageResource(R.mipmap.event_cap_yes);
                    transaction.show(fragment_event);
                    lastfragment = fragment_event;
                    lastll=ll_event;
                    index = 2;
                    break;
                case R.id.ll_my:
                    tv = (TextView) v.findViewById(R.id.tv_my);
                    iv = (ImageView) v.findViewById(R.id.iv_my);
                    iv.setImageResource(R.mipmap.self_on);
                    transaction.show(fragment_my);
                    lastfragment = fragment_my;
                    lastll=ll_my;
                    index = 3;
                    break;
            }
            tv.setTextColor(Color.parseColor("#FF6633"));
        }
        transaction.commit();
    }

    private long exitTime = 0;
    //连续两次退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
      /*  if (keyCode == KeyEvent.KEYCODE_BACK){
            if(mCurrentFragment instanceof FragmentVenue&&((FragmentVenue)mCurrentFragment).isDrawLayoutOpen()){
                //((Fragement_recommend)fragement_recommend).startAnimot1();
                ImageView vi=((FragmentVenue)fragment_venue).getBack();
                vi.performClick();
                return true;
            }
        }*/


        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(findViewById(R.id.drawer_right))){
            drawerLayout.closeDrawers();
        }else{
            super.onBackPressed();
        }


    }
}
