package com.qf.administrator.sports.modules.event.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.qf.administrator.sports.R;
import com.qf.administrator.sports.activity.BaseActivity;
import com.qf.administrator.sports.adatper.CommonAdapter;
import com.qf.administrator.sports.adatper.ViewHolder;
import com.qf.administrator.sports.modules.event.bean.EventInfo;
import com.qf.administrator.sports.modules.event.i.OnImageLeftClickListener;
import com.qf.administrator.sports.modules.event.widget.EventDetailsTitleBar;
import com.qf.administrator.sports.modules.my.bean.userinfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chigo on 10/11/2016.
 */

public class MyEventActivity extends BaseActivity implements OnImageLeftClickListener{
    private EventDetailsTitleBar titleBar;
    private ListView lv;
    private CommonAdapter<EventInfo>  adapter;

    private List<EventInfo> list;
    @Override
    protected void findViews() {
        titleBar= (EventDetailsTitleBar) findViewById(R.id.titlebar);
        lv= (ListView) findViewById(R.id.myevent_lv);
    }

    @Override
    protected void initEvent() {
        //返回按钮的点击事件
        titleBar.setOnImageLeftClickListener(this);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    protected void init() {
        list=new ArrayList<>();

        //设置标题栏标题
        titleBar.setTitle("我的赛事");


        Intent intent=getIntent();
        final int posi=intent.getIntExtra("position",-1111);

//        EventInfo info= (EventInfo) intent.getSerializableExtra("info1");
        list= userinfo.getCurrentUser(userinfo.class).getSports();
        if(list==null){
            Toast.makeText(MyEventActivity.this,"您还未报名任何赛事，赶快去参与吧！",Toast.LENGTH_SHORT).show();
            return;
        }
        adapter=new CommonAdapter<EventInfo>(this,list,R.layout.adapter_myevent) {
            @Override
            public void convert(ViewHolder helper, int position, EventInfo item) {
                helper.setText(R.id.tv_match_name, item.getMatch_name());
                helper.setText(R.id.tv_address, item.getAddress());
                helper.setText(R.id.tv_match_sport, item.getMatch_sport());
                helper.setImageByUrl(R.id.iv_photo, item.getPhoto_url());
                helper.setText(R.id.tv_joined_enroll_num, item.getJoined_enroll_num() + "/");
                helper.setText(R.id.tv_max_enroll_num, item.getMax_enroll_num() + item.getMatch_team_type() + "报名");
                helper.setText(R.id.tv_begintime, item.getMatch_begintime());
                helper.setText(R.id.tv_endtime, item.getMatch_endtime());
                String match_team_type=item.getMatch_team_type();
//                String enroll_fee = item.getItemlist()[posi].getEnroll_fee();
//                double fee = Double.parseDouble(enroll_fee);
                helper.setText(R.id.fee, "￥"+item.getPrice()+ "元/" + match_team_type);
            }
        };
        lv.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }


    @Override
    protected int setViewId() {
        //eventbus注册
//        EventBus.getDefault().register(this);
        return R.layout.activity_myevent;

    }

    @Override
    public void onClick(View v) {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }
}
