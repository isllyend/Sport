package com.qf.administrator.sports.modules.coach.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qf.administrator.sports.R;
import com.qf.administrator.sports.activity.BaseActivity;
import com.qf.administrator.sports.adatper.CommonAdapter;
import com.qf.administrator.sports.adatper.ViewHolder;
import com.qf.administrator.sports.i.BaseCallBack;
import com.qf.administrator.sports.modules.coach.adatper.MyPagerAdapter;
import com.qf.administrator.sports.modules.coach.bean.CoachCourse;
import com.qf.administrator.sports.modules.coach.bean.CoachDetailFirst;
import com.qf.administrator.sports.modules.coach.bean.CoachDetailSecond;
import com.qf.administrator.sports.modules.coach.util.CoachCourseJson;
import com.qf.administrator.sports.modules.coach.util.CoachDetailFirstJson;
import com.qf.administrator.sports.modules.coach.util.CoachDetailSecondJson;
import com.qf.administrator.sports.net.HttpUtil;
import com.qf.administrator.sports.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/13.
 */
public class CoachDetailActivity extends BaseActivity {
    private  ImageView icon_detail;

    private  ImageView icon_gender;//男女图片

    private TextView detail_name;//名字

    private  TextView detail_year;//年

    private RadioGroup radioGroup;

    private ViewPager detail_viewpager;

    private int courseID;

    private TextView course_experience;

    private TextView my_introduce;

    private TextView teching;

    private MyPagerAdapter myPagerAdapter;

    private List<View>list;

    private List<CoachCourse>coachCourses;

    private CommonAdapter<CoachCourse> coachCourseCommonAdapter;

    private ListView viewpager_listview;

    private ImageView back_detail;

    @Override
    protected void findViews() {
        Intent intent=getIntent();
        courseID=intent.getIntExtra("courseId",0);
        icon_detail= (ImageView) findViewById(R.id.icon_detail);
        icon_gender= (ImageView) findViewById(R.id.icon_gender);
        detail_name= (TextView) findViewById(R.id.detail_name);
        detail_year= (TextView) findViewById(R.id.detail_year);
        radioGroup= (RadioGroup) findViewById(R.id.coach_radiogroup);
        back_detail= (ImageView) findViewById(R.id.coach_back);

        detail_viewpager= (ViewPager) findViewById(R.id.detail_viewpager);
        radioGroup.check(R.id.coach_kecheng);




    }

    @Override
    protected void initEvent() {
      detail_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
          @Override
          public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {



          }

          @Override
          public void onPageSelected(int position) {
            switch (position){
                case 0:
                    radioGroup.check(R.id.coach_xiangqing);
                    break;
                case 1:
                    radioGroup.check(R.id.coach_kecheng);
                    break;
                case 2:
                    radioGroup.check(R.id.coach_pingjia);
                    break;
            }

          }

          @Override
          public void onPageScrollStateChanged(int state) {

          }
      });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.coach_xiangqing:
                        detail_viewpager.setCurrentItem(0);
                        break;
                    case R.id.coach_kecheng:
                        detail_viewpager.setCurrentItem(1);
                        break;
                    case R.id.coach_pingjia:
                        detail_viewpager.setCurrentItem(2);

                        break;
                }
            }
        });

        back_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();


            }
        });
    }

    @Override
    protected void init() {
      //数据初始化
        View view1= LayoutInflater.from(this).inflate(R.layout.coach_viewpager_first,null,false);
        course_experience= (TextView) view1.findViewById(R.id.course_experience);
        my_introduce= (TextView) view1.findViewById(R.id.my_introduce);
        teching= (TextView) view1.findViewById(R.id.teching);

        list=new ArrayList<>();
        list.add(view1);
        View view2= LayoutInflater.from(this).inflate(R.layout.coach_viewpager_second,null,false);
        viewpager_listview= (ListView) view2.findViewById(R.id.viewpager_listview);

        list.add(view2);
        View view3= LayoutInflater.from(this).inflate(R.layout.coach_viewpagr_third,null,false);
        list.add(view3);
       detail_viewpager.setAdapter(new MyPagerAdapter(list));
        detail_viewpager.setCurrentItem(1);

        coachCourses=new ArrayList<>();
        coachCourseCommonAdapter=new CommonAdapter<CoachCourse>(this,coachCourses,R.layout.coach_viewpager_listview) {
            @Override
            public void convert(ViewHolder helper, int position, CoachCourse item) {
                helper.setText(R.id.type,item.getNameCourse());
                helper.setText(R.id.coach_price,item.getPrice()/100+"元/课时");
                helper.setText(R.id.address,item.getMap_address());
                helper.setText(R.id.course_method,"授课方式:"+item.getCourse_type());
            }
        };
       viewpager_listview.setAdapter(coachCourseCommonAdapter);

    }

    @Override
    protected void loadData() {
        //数据加载
        //教练详情
        HttpUtil.doHttpReqeust("GET", "http://platform-api.1yd.me/api/specialty/" + courseID, null, new BaseCallBack() {
            @Override
            public void success(Object data) {
                Toast.makeText(getApplication(),"数据更新中",Toast.LENGTH_SHORT).show();
                final  List<CoachDetailFirst>templist= CoachDetailFirstJson.getCoachDetaiFirst(data.toString());
               Handler handler=new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0;i<templist.size();i++){

                            detail_year.setText(templist.get(0).getCoachingSpecialty()+"|执教"+templist.get(0).getCoachingYears()+"年");//类型
                            if (i>0){
                                course_experience.setText(templist.get(0).getCoachingSpecialty()+" "+templist.get(0).getCoachingYears()+"年"+"\n\r"+templist.get(1).getCoachingSpecialty()+" "+templist.get(1).getCoachingYears()+"年");
                            }else{
                                course_experience.setText(templist.get(0).getCoachingSpecialty()+"  "+templist.get(0).getCoachingYears()+"年");

                            }

                            Toast.makeText(getApplication(), "数据更新完成", Toast.LENGTH_SHORT).show();

                        }
                    }

                });


            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(getApplication(),"请查看网络状态",Toast.LENGTH_SHORT).show();
            }
        });
        HttpUtil.doHttpReqeust("GET", "http://platform-api.1yd.me/api/coaches/" + courseID, null, new BaseCallBack() {
            @Override
            public void success(Object data) {
                final CoachDetailSecond coachDetailSecond= CoachDetailSecondJson.getCoachDetailSecond(data.toString());
                Handler handler=new Handler();

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        ImageLoader.getInstance().displayImage(coachDetailSecond.getHead_picture(), icon_detail, ImageLoaderUtil.getCircleOption(Color.WHITE, 1));
                        if ("GENDER_MAN".equals(coachDetailSecond.getGender())){
                            icon_gender.setImageResource(R.mipmap.ic_gender_male);
                        }
                        detail_name.setText(coachDetailSecond.getNamesecond());
                        my_introduce.setText(coachDetailSecond.getMyself_intro());
                        teching.setText(coachDetailSecond.getTeach_testimonials());



                    }
                });
            }

            @Override
            public void failed(int errorCode, Object data) {

            }
        });

        //教练课程
        HttpUtil.doHttpReqeust("GET", "http://platform-api.1yd.me/api/v2/course?coach_id=" + courseID, null, new BaseCallBack() {
            @Override
            public void success(Object data) {
                List<CoachCourse>coachCourseJsons= CoachCourseJson.getCoachCourse(data.toString());
                if (coachCourseJsons!=null){
                    coachCourses.addAll(coachCourseJsons);
                    coachCourseCommonAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void failed(int errorCode, Object data) {

            }
        });

    }

    @Override
    protected int setViewId() {
        return R.layout.coach_detail;
    }


}
