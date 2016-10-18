package com.qf.administrator.sports.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qf.administrator.sports.R;
import com.qf.administrator.sports.adatper.CommonAdapter;
import com.qf.administrator.sports.adatper.ViewHolder;
import com.qf.administrator.sports.i.BaseCallBack;
import com.qf.administrator.sports.modules.coach.activity.CourseDetailActivity;
import com.qf.administrator.sports.modules.coach.bean.BooleanOpen;
import com.qf.administrator.sports.modules.coach.bean.CoachInfo;
import com.qf.administrator.sports.modules.coach.bean.EventBusBean;
import com.qf.administrator.sports.modules.coach.bean.SecondCoachInfo;
import com.qf.administrator.sports.modules.coach.dao.CoachDao;
import com.qf.administrator.sports.modules.coach.widget.SizeHelper;
import com.se7en.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;

public class FragmentCoach extends BaseFragment implements   RadioGroup.OnCheckedChangeListener {
    private LinearLayout linearLayout;

    private TextView textView;

    private HorizontalScrollView horizontalScrollView;

    private List<Integer> list = new ArrayList<>();

    private List<String> strings = new ArrayList<>();

    private RadioGroup radioGroups;

    private int channel;

    private int lastButton=-1;

    private String str;

    private float mcurrnetradiobuttonleft;

    private RadioGroup radioGroup;

    private RadioButton radioButton1,radioButton2,radioButton3;

    private ImageView imageView1,imageView2,imageView3;

    private LinearLayout linear;


    private List<CoachInfo>coachlist;

    private CommonAdapter<CoachInfo> coachAdapter;

    private boolean isLoadingData;//判断当前是否正在请求数据

    private int array;//进行选择哪个网址

    private int page=0;

    private LinearLayout faillinear;

    private PullToRefreshListView coachListView;

    private CommonAdapter<SecondCoachInfo> secondAdapter;

    private List<Integer>itemlist=new ArrayList<>();

    private LinearLayout itemlinear;

    private boolean isOpen;
    private BooleanOpen lastopen=new BooleanOpen();
    private int lastposition=-1;

    private EventBusBean eventBusBean;

    private ListView mylistview;

    private CommonAdapter<String> myAdapter;

    private List<String>mylist=new ArrayList<>();












    //教练
    @Override
    protected void findViews(final View view) {
        list.add(R.mipmap.wq);
        list.add(R.mipmap.ymq);
        list.add(R.mipmap.ppq);
        list.add(R.mipmap.zq);
        list.add(R.mipmap.lq);
        list.add(R.mipmap.qyjs);
        list.add(R.mipmap.js);
        list.add(R.mipmap.yj);
        list.add(R.mipmap.yy);
        list.add(R.mipmap.tjq);
        list.add(R.mipmap.tqd);
        list.add(R.mipmap.ws);
        list.add(R.mipmap.wd);
        list.add(R.mipmap.pb);
        list.add(R.mipmap.qt);
        strings.add("网球");
        strings.add("羽毛球");
        strings.add("乒乓球");
        strings.add("足球");
        strings.add("篮球");
        strings.add("企业健身");
        strings.add("健身");
        strings.add("瑜伽");
        strings.add("游泳");
        strings.add("太极拳");
        strings.add("跆拳道");
        strings.add("武术");
        strings.add("舞蹈");
        strings.add("跑步");
        strings.add("其他");
        mylist.add("全部");
        mylist.add("上门");
        mylist.add("驻场");
        linearLayout = (LinearLayout) view.findViewById(R.id.news_linear);
        horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.horizontal_scroll);
        textView = (TextView) view.findViewById(R.id.textview);
        radioGroups= (RadioGroup) view.findViewById(R.id.radiogroup);
        radioButton1= (RadioButton) view.findViewById(R.id.radio1);
        radioButton2= (RadioButton) view.findViewById(R.id.radio2);
        radioButton3= (RadioButton) view.findViewById(R.id.radio3);
        imageView1= (ImageView) view.findViewById(R.id.image1);
        imageView2= (ImageView) view.findViewById(R.id.image2);
        imageView3= (ImageView) view.findViewById(R.id.image3);
        linear= (LinearLayout) view.findViewById(R.id.coach_linear);
        coachListView= (PullToRefreshListView) view.findViewById(R.id.coach_listview);
        mylistview= (ListView) view.findViewById(R.id.my_list);
        faillinear= (LinearLayout) view.findViewById(R.id.faillinear);






        //初始化选项卡

        radioGroup = new RadioGroup(getContext());
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        radioGroup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.addView(radioGroup);

        for (int i = 0; i < list.size(); i++) {
            channel=list.get(i);

            RadioButton radioButton = new RadioButton(getContext());
            RadioGroup.LayoutParams l = new RadioGroup.LayoutParams((int) SizeHelper.dp2px(getContext(), 48), (int) SizeHelper.dp2px(getContext(), 48));
            int left = (int) SizeHelper.dp2px(getContext(), 25);
            int right = (int) SizeHelper.dp2px(getContext(), 25);
            int top = (int) SizeHelper.dp2px(getContext(), 14);
            l.setMargins(left, top, right, 0);
            radioButton.setLayoutParams(l);
            radioButton.setBackgroundResource(channel);
            radioButton.setTag(i);
            radioButton.setButtonDrawable(android.R.color.transparent);
            radioGroup.addView(radioButton);


        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = (RadioButton) view.findViewById(checkedId);
                if (lastButton==checkedId){
                    return;
                }
                if (lastButton!=-1){
                   RadioButton lasts = (RadioButton) view.findViewById(lastButton);
                   coachScale(lasts,1,0);
               }
                coachScale(rb,0,1);
                lastButton=checkedId;

                array= (int) rb.getTag();

                str=strings.get(array);

                mcurrnetradiobuttonleft = rb.getLeft();
                int width = (int) SizeHelper.dp2px(getContext(), 160);
                horizontalScrollView.smoothScrollTo((int) (mcurrnetradiobuttonleft - width), 0);
                textView.setText(str);
                radioButton1.setText("授课方式");

                if (isOpen==true){
                    changeViewHeightradiobutton(linear, 1000, 0);
                    isOpen=false;
                }

                page=0;
                loadData();




            }
        });
        if (!list.isEmpty()) {
            radioGroup.check(radioGroup.getChildAt(0).getId());
            lastButton=radioGroup.getChildAt(0).getId();

        }


    }

    //移动动画效果
    public void coachScale(final RadioButton rb,int start,int end) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(getContext(), "xxx", start, end).setDuration(100);
                objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float progress = (float) animation.getAnimatedValue();
                        rb.setScaleX(1 + 0.2f * progress);
                        rb.setScaleY(1 + 0.2f * progress);


                    }
                });
        objectAnimator.start();

    }

    //点击事件
    @Override
    protected void initEvent() {
      radioGroups.setOnCheckedChangeListener(this);
        coachListView.setMode(PullToRefreshBase.Mode.BOTH);
        coachListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 0;
                loadData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                loadData();
            }
        });


        coachListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }

        });

        myAdapter=new CommonAdapter<String>(getContext(),mylist,R.layout.my_listview) {
            @Override
            public void convert(ViewHolder helper, int position, String item) {
                helper.setText(R.id.mytext,mylist.get(position));
            }
        };
        mylistview.setAdapter(myAdapter);

        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                radioButton1.setText(mylist.get(position));
                changeViewHeightradiobutton(linear, 1000, 0);
                isOpen=false;
                if (position==2){
                    faillinear.setVisibility(View.VISIBLE);
                    coachListView.setVisibility(View.GONE);
                }else{
                    faillinear.setVisibility(View.GONE);
                    coachListView.setVisibility(View.VISIBLE);
                }

            }
        });








    }

    @Override
    protected void init() {
        //初始化
       coachlist=new ArrayList<>();
        coachAdapter=new CommonAdapter<CoachInfo>(getContext(),coachlist,R.layout.coach_listview_item) {
            @Override
            public void convert(final ViewHolder helper, final int position, final CoachInfo item) {

                helper.setText(R.id.listview_name, item.getCaoch_name());
                helper.setText(R.id.listview_type, strings.get(array) + "教练");
                helper.setText(R.id.listview_year, "执教" + item.getCoaching_years() + "年");
                helper.setText(R.id.listview_pingjia, item.getComment() + "人评价");
                helper.setText(R.id.listview_price, item.getPrice() / 100 + "元/课时");
                helper.setImageByUrl(R.id.listview_image, item.getIcon());
                RatingBar ratingBar = helper.getView(R.id.listview_ratingbar);
                ratingBar.setNumStars(item.getRank());
                final List secondlist=item.getList();
                final LinearLayout second_linear= helper.getView(R.id.second_linear);
                final View convertView=helper.getConvertView();
                ViewGroup.LayoutParams params=convertView.getLayoutParams();
                params.height=DeviceUtils.dip2px(96);
                convertView.setLayoutParams(params);
                second_linear.removeAllViews();
//                ViewGroup.LayoutParams p=second_linear.getLayoutParams();
//                p.height=secondlist.size()*DeviceUtils.dip2px(36);
//                second_linear.setLayoutParams(p);
                final BooleanOpen booleanOpen=new BooleanOpen();
                booleanOpen.setOpen(false);

                helper.getView(R.id.rl_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (booleanOpen.getOpen()==true) {
                            View convertView = helper.getConvertView();
                            changeViewHeight(convertView, secondlist.size() * DeviceUtils.dip2px(40) + DeviceUtils.dip2px(96), DeviceUtils.dip2px(96));
                            booleanOpen.setOpen(false);

                        } else {

                            Log.i("vm",helper.getPosition()+"");
                            eventBusBean = new EventBusBean();
                            eventBusBean.setIcon(item.getIcon());
                            eventBusBean.setYear(item.getCoaching_years());
                            eventBusBean.setName(item.getCaoch_name());
                            addImageview(second_linear);
                            addLinearLayout(secondlist, second_linear);

                            View convertView = helper.getConvertView();

                            changeViewHeight(convertView, DeviceUtils.dip2px(96), secondlist.size() * DeviceUtils.dip2px(40) + DeviceUtils.dip2px(96));
                            booleanOpen.setOpen(true);



//                            ViewGroup.LayoutParams params=convertView.getLayoutParams();
//                            params.height=secondlist.size()*DeviceUtils.dip2px(36)+ DeviceUtils.dip2px(96);
//                            convertView.setLayoutParams(params);
                        }


                    }

                });


            }

        };
        coachListView.setAdapter(coachAdapter);
        }

    @Override
    protected void loadData() {
        if(isLoadingData){
            return;
        }
        isLoadingData=true;
        CoachDao.getCoachInfoList(array, page, new BaseCallBack() {
            @Override
            public void success(Object data) {
                isLoadingData = false;
                coachListView.onRefreshComplete();
                if (page == 0) {
                    coachlist.clear();

                }
                if (data != null) {
                    coachlist.addAll((List<CoachInfo>) data);
                    coachAdapter.notifyDataSetChanged();
                    faillinear.setVisibility(View.GONE);
                    coachListView.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void failed(int errorCode, Object data) {
                coachListView.onRefreshComplete();
                isLoadingData = false;
                faillinear.setVisibility(View.VISIBLE);
                coachListView.setVisibility(View.GONE);

            }
        });

    }

    @Override
    protected int setViewId() {

        return R.layout.fragment_coach;
    }








    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case  R.id.radio1:
                ColorStateList colorStateList1=getResources().getColorStateList(R.color.text_color);
                radioButton1.setTextColor(colorStateList1);
                imageView1.setEnabled(true);
                imageView2.setEnabled(false);
                imageView3.setEnabled(false);
                imageView1.setImageResource(R.drawable.img_normal_click);

                //点击时窗帘效果
                if (isOpen==false) {
                    // TODO: 2016/10/10 打开
                    changeViewHeightradiobutton(linear, 0, 1000);

                    isOpen = true;
                    radioButton1.setChecked(false);
                    radioButton1.setTextColor(getResources().getColor(R.color.MyColor));


                }else{
                    changeViewHeightradiobutton(linear, 1000, 0);
                    isOpen=false;
                    radioButton1.setChecked(false);
                    radioButton1.setTextColor(getResources().getColor(R.color.MyColor));
                }



                break;

            case  R.id.radio2:
                ColorStateList colorStateList2=getResources().getColorStateList(R.color.text_color);
                radioButton2.setTextColor(colorStateList2);
                imageView2.setEnabled(true);
                imageView1.setEnabled(false);
                imageView3.setEnabled(false);
                imageView2.setImageResource(R.drawable.img_normal_click);
                radioButton1.setTextColor(getResources().getColor(R.color.gray));
                Toast.makeText(getContext(),"功能开发中，敬请期待",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.radio3:
                ColorStateList colorStateList3=getResources().getColorStateList(R.color.text_color);
                radioButton3.setTextColor(colorStateList3);
                imageView3.setEnabled(true);
                imageView1.setEnabled(false);
                imageView2.setEnabled(false);
                imageView3.setImageResource(R.drawable.img_normal_click);
                radioButton1.setTextColor(getResources().getColor(R.color.gray));
                Toast.makeText(getContext(), "功能开发中，敬请期待", Toast.LENGTH_SHORT).show();
                break;
        }
    }
            //筛选控件窗帘效果

            private void coachtraction(int start,int end) {
                final ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(getContext(),"xxx",start,end);
                objectAnimator.setDuration(1000);
                objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float progress = (float) animation.getAnimatedValue();
                        linear.setPivotY(0);
                        linear.setScaleY(-ViewGroup.LayoutParams.MATCH_PARENT * progress);
                    }
                });
                objectAnimator.start();



    }
    //listview点击的抽屉效果
    private void changeViewHeight(final View view, final int start, final int end) {
        ObjectAnimator objectani = ObjectAnimator.ofFloat(this, "XXX", 0f, 1f).setDuration(1000);
        objectani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float pro = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = (int) (start + (end - start) * pro);
                view.setLayoutParams(params);
            }
        });
        objectani.start();
    }

    private void changeViewHeightradiobutton(final View view, final int start, final int end) {
        ObjectAnimator objectani = ObjectAnimator.ofFloat(this, "XXX", 0f, 1f).setDuration(500);
        objectani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float pro = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = (int) (start + (end - start) * pro);
                view.setLayoutParams(params);
            }
        });
        objectani.start();


    }
    //动态添加LinearLayout
    public void addLinearLayout(final List<SecondCoachInfo>lists,LinearLayout itemlinearlayout){
        for (  int i=0;i<lists.size();i++){
                LinearLayout linearLayout=new LinearLayout(getContext());
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) SizeHelper.dp2px(getContext(),36));
                params.setMargins(10, 8, 10, 0);
                linearLayout.setLayoutParams(params);
                linearLayout.setBackgroundResource(R.color.white);

                TextView textView1=new TextView(getContext());
                LinearLayout.LayoutParams tvparams=new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
                tvparams.weight=1;


                textView1.setLayoutParams(tvparams);
                textView1.setText(lists.get(i).getName());
                textView1.setClickable(true);
                textView1.setTag(i);
                textView1.setSingleLine(true);


                textView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tag= (int) v.getTag();
                        eventBusBean.setCourseID(lists.get(tag).getCourseID());
                        Intent intent=new Intent(getContext(), CourseDetailActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("ev",eventBusBean);
                        intent.putExtras(bundle);

                        startActivity(intent);
                    }
                });
                textView1.setTextColor(getResources().getColor(R.color.red));

                TextView textView2=new TextView(getContext());
                LinearLayout.LayoutParams tvparams2=new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
                tvparams2.weight=1;
                tvparams2.setMargins(5, 0, 0, 0);
                textView2.setSingleLine(true);
                textView2.setLayoutParams(tvparams2);
                textView2.setText(lists.get(i).getCourse_type());
                textView2.setTextColor(getResources().getColor(R.color.gray));
                textView2.setGravity(Gravity.CENTER);
                TextView textView3=new TextView(getContext());
                LinearLayout.LayoutParams tvparams3=new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
                tvparams3.weight=1;
                tvparams3.setMargins(5, 0, 0, 0);
                textView3.setSingleLine(true);
                textView3.setLayoutParams(tvparams3);
                textView3.setText(lists.get(i).getSecond_price() / 100 + "元/课时");
                textView3.setTextColor(getResources().getColor(R.color.gray));
                textView3.setGravity(Gravity.CENTER);
                linearLayout.addView(textView1);
                linearLayout.addView(textView2);
                linearLayout.addView(textView3);
                itemlinearlayout.addView(linearLayout);
        }




    }

    //动态添加图片
    public void addImageview(ViewGroup viewGroup){
        LinearLayout linearLayout=new LinearLayout(getContext());
         LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setGravity(Gravity.CENTER);
        ImageView imageView=new ImageView(getContext());
        imageView.setPadding(10,4,10,4);
        imageView.setImageResource(R.mipmap.bg_info_export);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        linearLayout.addView(imageView);
        viewGroup.addView(linearLayout);

    }


    }
