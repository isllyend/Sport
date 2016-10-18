package com.qf.administrator.sports.modules.coach.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qf.administrator.sports.R;
import com.qf.administrator.sports.i.BaseCallBack;
import com.qf.administrator.sports.modules.coach.bean.DetailInfo;
import com.qf.administrator.sports.modules.coach.bean.EventBusBean;
import com.qf.administrator.sports.modules.coach.util.DetailJson;
import com.qf.administrator.sports.net.HttpUtil;
import com.qf.administrator.sports.util.ImageLoaderUtil;


/**
 * Created by Administrator on 2016/10/12.
 */
public class CourseDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView name;
    private TextView year;
    private ImageView ima;
    private int id;
    private ImageView courseback;
    private TextView coach_detail;
    private int coach_id;
    private RadioGroup timeradio;
    private TextView text_yuye;

    private TextView course_name,course_introduce,course_price,course_place,course_venue;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//透明导航栏
        }
        super.onCreate(savedInstanceState);
        //去掉actionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.coach_course);
        name= (TextView) findViewById(R.id.name);
        year= (TextView) findViewById(R.id.year);
        ima= (ImageView) findViewById(R.id.ima);
        course_name= (TextView) findViewById(R.id.course_name);
        course_introduce= (TextView) findViewById(R.id.course_introduce);
        course_price= (TextView) findViewById(R.id.course_price);
        course_place= (TextView) findViewById(R.id.course_place);
        course_venue= (TextView) findViewById(R.id.course_venue);
        courseback= (ImageView) findViewById(R.id.back);
        courseback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent=getIntent();
        EventBusBean eventBusBean= (EventBusBean) intent.getSerializableExtra("ev");
        name.setText(eventBusBean.getName());
        year.setText("执教" + eventBusBean.getYear() + "年");
        ImageLoader.getInstance().displayImage(eventBusBean.getIcon(), ima, ImageLoaderUtil.getCircleOption(Color.WHITE, 1));
        id=eventBusBean.getCourseID();
        coach_detail= (TextView) findViewById(R.id.coach_detail);
        coach_detail.setOnClickListener(this);
        text_yuye= (TextView) findViewById(R.id.text_yuyue);
        text_yuye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"对不起暂时无法预约",Toast.LENGTH_SHORT).show();
            }
        });




        loadata();



    }

    private void loadata() {
        HttpUtil.doHttpReqeust("GET", "http://platform-api.1yd.me/api//v2/course/" + id, null, new BaseCallBack() {
            @Override
            public void success(  Object data) {
                Toast.makeText(getApplication(),"数据请求中请稍后！",Toast.LENGTH_SHORT).show();
                final DetailInfo detailInfo=DetailJson.getDetailJson(data.toString());

                Handler handler=new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        course_name.setText(detailInfo.getName());
                        course_introduce.setText(detailInfo.getSummary());
                        course_price.setText(detailInfo.getCourse_type()+"   "+detailInfo.getPrice()/100+"元/小时");
                        course_place.setText(detailInfo.getDetailed_address());
                        course_venue.setText(detailInfo.getMap_address());
                        coach_id=detailInfo.getCoach_id();
                        Toast.makeText(getApplication(),"数据更新完成！",Toast.LENGTH_SHORT).show();
                    }
                });



            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(getApplication(),"请检查网络连接状态",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,CoachDetailActivity.class);
        intent.putExtra("courseId",coach_id);
        startActivity(intent);



    }
}

