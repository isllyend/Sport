package com.qf.administrator.sports.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qf.administrator.sports.R;
import com.qf.administrator.sports.adatper.CommonAdapter;
import com.qf.administrator.sports.adatper.ViewHolder;
import com.qf.administrator.sports.i.BaseCallBack;
import com.qf.administrator.sports.modules.venue.activity.CityActivity;
import com.qf.administrator.sports.modules.venue.adatper.MyPagerAdapter;
import com.qf.administrator.sports.modules.venue.bean.DistrictInfo;
import com.qf.administrator.sports.modules.venue.bean.EventBean;
import com.qf.administrator.sports.modules.venue.bean.ResourseInfo;
import com.qf.administrator.sports.modules.venue.bean.SubgymsInfo;
import com.qf.administrator.sports.modules.venue.bean.VenuesInfo;
import com.qf.administrator.sports.modules.venue.dao.DistrictDao;
import com.qf.administrator.sports.modules.venue.dao.SubgymDao;
import com.qf.administrator.sports.modules.venue.dao.VenueDao;
import com.qf.administrator.sports.util.ImageLoaderUtil;
import com.qf.administrator.sports.util.MyUtils;
import com.qf.administrator.sports.widget.MyDrawerLayout;
import com.qf.administrator.sports.widget.MyDrawerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentVenue extends BaseFragment implements View.OnClickListener {
    private TextView venue_tv_city, venue_tv_xm, venue_tv_quyu, venue_tv_shaixuan, venue_tv_paixu, venue_tv_xzq, venue_tv_seleced, venue_tv_venue, venue_tv_position, venue_tv_trff, tv_time1, tv_price1, tv_remain1, tv_qbcc, tv_venue_num, tv_venue_evment, tv_venue_floor, tv_venue_height;
    private ImageView venue_iv_left, venue_iv_right, venue_iv_search, venue_iv_wq, venue_iv_ymq, venue_iv_zq, venue_iv_ppq, venue_iv_lq, venue_iv_zhq, venue_iv_hanb, venue_iv_sj, venue_iv_py, venue_iv_yy, drawview_iv_back, venue_iv_venueimg, iv_phone;
    private EditText venue_et;
    private ViewPager venue_viewpager;
    private ListView venue_listview, venue_area_listview;
    private List<ImageView> imageViewList;
    private List<VenuesInfo> venuesInfoList;
    private List<DistrictInfo> districtInfoList;
    private List<ResourseInfo> resourceInfoList;
    private CommonAdapter<VenuesInfo> madapter;
    private CommonAdapter<DistrictInfo> dadapter;
    private MyPagerAdapter mpa;
    private float FACTOR = 0.3F;
    private ImageView lastim;
    private LinearLayout layout;
    private HorizontalScrollView hsv;
    private SwipeRefreshLayout refresh;
    private MyDrawerLayout drawerLayout;
    private MyDrawerView drawerView;
    private LinearLayout listlayout;

    private boolean isRunning = false;
    private SubgymsInfo info;
    private int page = 0;
    private String city_id = "310100", district_id = "", activity_id = "1", facility_id = "", sort = "", itemid = "";

    //渲染控件
    @Override
    protected void findViews(View view) {

        venue_tv_city = (TextView) view.findViewById(R.id.tv_city);
        venue_tv_xm = (TextView) view.findViewById(R.id.tv_venue_xm);
        venue_tv_quyu = (TextView) view.findViewById(R.id.venue_tv_quyu);
        venue_tv_shaixuan = (TextView) view.findViewById(R.id.venue_tv_shaixuan);
        venue_tv_paixu = (TextView) view.findViewById(R.id.venue_tv_paixu);
        venue_tv_seleced = (TextView) view.findViewById(R.id.venue_tv_select);
        venue_tv_xzq = (TextView) view.findViewById(R.id.venue_tv_xzq);
        venue_iv_left = (ImageView) view.findViewById(R.id.iv_venue_left);
        venue_iv_right = (ImageView) view.findViewById(R.id.iv_venue_right);
        venue_iv_search = (ImageView) view.findViewById(R.id.venue_iv_search);
        venue_iv_wq = (ImageView) view.findViewById(R.id.venue_iv_wq);
        venue_iv_ymq = (ImageView) view.findViewById(R.id.venue_iv_ymq);
        venue_iv_zq = (ImageView) view.findViewById(R.id.venue_iv_zq);
        venue_iv_ppq = (ImageView) view.findViewById(R.id.venue_iv_ppq);
        venue_iv_lq = (ImageView) view.findViewById(R.id.venue_iv_lq);
        venue_iv_zhq = (ImageView) view.findViewById(R.id.venue_iv_zhq);
        venue_iv_hanb = (ImageView) view.findViewById(R.id.venue_iv_hl);
        venue_iv_sj = (ImageView) view.findViewById(R.id.venue_iv_sj);
        venue_iv_py = (ImageView) view.findViewById(R.id.venue_iv_py);
        venue_iv_yy = (ImageView) view.findViewById(R.id.venue_iv_yy);
        venue_et = (EditText) view.findViewById(R.id.et_venue_search);
        venue_viewpager = (ViewPager) view.findViewById(R.id.venue_viewpager);
        venue_listview = (ListView) view.findViewById(R.id.venue_listview);
        venue_area_listview = (ListView) view.findViewById(R.id.venue_area_listview);
        layout = (LinearLayout) view.findViewById(R.id.venue_ll03);
        hsv = (HorizontalScrollView) view.findViewById(R.id.venue_sv);
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.venue_reflash);
        EventBean eventBean = (EventBean) getArguments().getSerializable("eventBean");
        drawerLayout = (MyDrawerLayout) eventBean.getDrawerLayout();
        drawerView = (MyDrawerView) eventBean.getDrawerView();
        venue_tv_venue = (TextView) drawerView.findViewById(R.id.venue_tv_venue);
        venue_tv_position = (TextView) drawerView.findViewById(R.id.venue_tv_position);
        venue_tv_trff = (TextView) drawerView.findViewById(R.id.venue_tv_trff);


        tv_qbcc = (TextView) drawerView.findViewById(R.id.tv_qbcc);
        tv_venue_num = (TextView) drawerView.findViewById(R.id.tv_venue_num);
        tv_venue_evment = (TextView) drawerView.findViewById(R.id.tv_venue_evment);
        tv_venue_floor = (TextView) drawerView.findViewById(R.id.tv_venue_floor);
        tv_venue_height = (TextView) drawerView.findViewById(R.id.tv_venue_height);
        drawview_iv_back = (ImageView) drawerView.findViewById(R.id.drawview_iv_back);
        venue_iv_venueimg = (ImageView) drawerView.findViewById(R.id.venue_iv_venueimg);
        iv_phone = (ImageView) drawerView.findViewById(R.id.iv_phone);
        listlayout = (LinearLayout) drawerView.findViewById(R.id.venuelist_info);

    }

    //控件事件
    @Override
    protected void initEvent() {

        //跳转到选择城市页面
        venue_tv_city.setOnClickListener(this);

        //运动项目导航点击事件
        venue_iv_wq.setOnClickListener(this);
        venue_iv_ymq.setOnClickListener(this);
        venue_iv_zq.setOnClickListener(this);
        venue_iv_ppq.setOnClickListener(this);
        venue_iv_lq.setOnClickListener(this);
        venue_iv_zhq.setOnClickListener(this);
        venue_iv_hanb.setOnClickListener(this);
        venue_iv_sj.setOnClickListener(this);
        venue_iv_py.setOnClickListener(this);
        venue_iv_yy.setOnClickListener(this);

        //筛选功能点击事件
        venue_tv_quyu.setOnClickListener(this);
        venue_tv_paixu.setOnClickListener(this);
        venue_tv_shaixuan.setOnClickListener(this);
        //场馆详情抽屉返回
        drawview_iv_back.setOnClickListener(this);

        //listviewItem的点击事件

        venue_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //drawerLayout.openDrawer(venue_listview);
                drawerLayout.openDrawer(drawerView);
                itemid = venuesInfoList.get(i).getId();
                info = new SubgymsInfo();
                loadSubgymInfoData();
                LoadResourceInfoData();

            }
        });
        //监听listview滑动
        venue_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:// 静止状态
                        Log.i("info", "SCROLL_STATE_IDLE");
                        // listView.getLastVisiblePosition()==list.size()-1只有在当前listview中不包含头部和尾部的时候并且拉到listview的最下面的时候才成立
                        if (venue_listview.getLastVisiblePosition() == venuesInfoList.size() - 1
                                + venue_listview.getHeaderViewsCount()) {
                            int childCount = venue_listview.getChildCount();// 获取当前listview中一共有多少个子控件
                            if (childCount == 0) {
                                return;
                            }
                            View lastChildView = venue_listview
                                    .getChildAt(childCount - 1);// 获取当前显示最后一个子控件
                            if (lastChildView
                                    .getBottom() == venue_listview.getBottom()
                                    - venue_listview.getPaddingBottom()) {
                                page++;
                                loadData();
                            }
                        }

                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:// 手指滑动屏幕
                        Log.i("info", "SCROLL_STATE_TOUCH_SCROLL");
                        break;
                    case SCROLL_STATE_FLING:// 手指抛开后惯性滑动
                        Log.i("info", "SCROLL_STATE_FLING");
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

        //下拉刷新
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("info", "onRefresh");
                page = 0;
                loadData();
            }
        });
    }


    /**
     * 初始化控件
     */
    @Override
    protected void init() {

        //设置刷新控件颜色
        refresh.setColorSchemeColors(Color.YELLOW);
        //初始化ViewPager
        initViewPager();
        mpa = new MyPagerAdapter(imageViewList);
        venue_viewpager.setAdapter(mpa);
        venue_viewpager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % imageViewList.size());
        venue_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        isRunning = true;
        handler.sendEmptyMessageDelayed(0, 2000);//轮播延时

        //模拟点击网球
        venue_iv_wq.performClick();

        //配置adapter
        venuesInfoList = new ArrayList<>();
        madapter = new CommonAdapter<VenuesInfo>(getActivity(), venuesInfoList, R.layout.venue_listview_item) {
            @Override
            public void convert(ViewHolder helper, int position, VenuesInfo item) {
                ImageView iv=helper.getView(R.id.venue_iv_itemimg);
                ImageLoader.getInstance().displayImage(item.getPictrueUrl(),iv,ImageLoaderUtil.getFadeInOption(300, true, true, true));
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                helper.setText(R.id.venue_tv_itemname, item.getName());
                helper.setText(R.id.venue_tv_itemadress, item.getAdress());
                if (item.getFaciliiesId().equals("21")) {
                    //helper.setBackgroundColor(R.id.venue_tv_itemfacilities,R.mipmap.reat_park);
                    helper.setText(R.id.venue_tv_itemfacilities, "停车");
                }

                int price = Integer.valueOf(item.getPrice()) / 100;
                helper.setText(R.id.venue_tv_itemprice, "￥" + price);
                helper.setText(R.id.venue_tv_itemdistance, item.getDistance() + "m");
            }
        };
        venue_listview.setAdapter(madapter);

    }

    /**
     * 加载主页listview数据
     */
    @Override
    protected void loadData() {

        VenueDao.getVenueInfoList(page, city_id, district_id, activity_id, facility_id, sort, new BaseCallBack() {
            @Override
            public void success(Object data) {
                refresh.setRefreshing(false);
                if (page == 0) {
                    venuesInfoList.clear();
                }

                if (data != null) {
                    venuesInfoList.addAll((List<VenuesInfo>) data);
                    //Log.i("info",venuesInfoList.toString());
                    madapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                //isLoading=false;
                refresh.setRefreshing(false);
                Toast.makeText(getActivity(), "数据请求失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 关联布局
     * @return
     */
    @Override
    protected int setViewId() {
        return R.layout.fragment_venue;
    }


    /**
     * 接收选择城市activity的返回数据
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String replyCity = data.getStringExtra("rname");
        String cityId = data.getStringExtra("cityId");
        venue_tv_city.setText(replyCity);
        city_id = cityId;
        loadData();
    }


    /**
     * 创建handler实现viewpager的轮播
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            venue_viewpager.setCurrentItem(venue_viewpager.getCurrentItem() + 1);

            if (isRunning) {
                handler.sendEmptyMessageDelayed(0, 2000);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }


    //控件点击事件方法
    @Override
    public void onClick(View view) {
        districtInfoList = new ArrayList<>();//行政区集合

        View im = view;
        if (im == lastim) {
            return;
        }
        switch (view.getId()) {
            case R.id.venue_iv_wq://TODO 网球
                //更新点击产生的动画
                if (lastim != null) {
                    upDateView(lastim, 1, 0);
                }
                upDateView(venue_iv_wq, 0, 1);
                lastim = (ImageView) im;
                venue_tv_xm.setText("网球");
                activity_id = "1";//传入城市id进行加载数据
                loadData();//加载数据
                break;
            case R.id.venue_iv_ymq://TODO 羽毛球
                if (lastim != null) {
                    upDateView(lastim, 1, 0);
                }
                upDateView(venue_iv_ymq, 0, 1);
                lastim = (ImageView) im;
                venue_tv_xm.setText("羽毛球");
                activity_id = "2";
                loadData();
                break;
            case R.id.venue_iv_zq://TODO 足球
                if (lastim != null) {
                    upDateView(lastim, 1, 0);
                }
                upDateView(venue_iv_zq, 0, 1);
                lastim = (ImageView) im;
                venue_tv_xm.setText("足球");
                activity_id = "23";
                loadData();
                break;
            case R.id.venue_iv_ppq://TODO 乒乓球
                if (lastim != null) {
                    upDateView(lastim, 1, 0);
                }
                upDateView(venue_iv_ppq, 0, 1);
                lastim = (ImageView) im;
                venue_tv_xm.setText("乒乓球");
                hsv.smoothScrollTo(venue_iv_ppq.getLeft() - MyUtils.dip2px(190, getActivity()), 0);
                activity_id = "21";
                loadData();
                break;
            case R.id.venue_iv_lq://TODO 篮球
                if (lastim != null) {
                    upDateView(lastim, 1, 0);
                }
                upDateView(venue_iv_lq, 0, 1);
                lastim = (ImageView) im;
                venue_tv_xm.setText("篮球");
                //scroview的滑动
                hsv.smoothScrollTo(venue_iv_lq.getLeft() - MyUtils.dip2px(150, getActivity()), 0);
                activity_id = "61";
                loadData();
                break;
            case R.id.venue_iv_zhq://TODO 桌球
                if (lastim != null) {
                    upDateView(lastim, 1, 0);
                }
                upDateView(venue_iv_zhq, 0, 1);
                lastim = (ImageView) im;
                venue_tv_xm.setText("桌球");
                hsv.smoothScrollTo(venue_iv_zhq.getLeft() - MyUtils.dip2px(150, getActivity()), 0);
                activity_id = "83";
                loadData();
                break;
            case R.id.venue_iv_hl://TODO 滑轮
                if (lastim != null) {
                    upDateView(lastim, 1, 0);
                }
                upDateView(venue_iv_hanb, 0, 1);
                lastim = (ImageView) im;
                venue_tv_xm.setText("滑轮");
                hsv.smoothScrollTo(venue_iv_hanb.getLeft() - MyUtils.dip2px(150, getActivity()), 0);
                activity_id = "124";
                loadData();
                break;
            case R.id.venue_iv_sj://TODO 射箭
                if (lastim != null) {
                    upDateView(lastim, 1, 0);
                }
                upDateView(venue_iv_sj, 0, 1);
                lastim = (ImageView) im;
                venue_tv_xm.setText("射箭");
                activity_id = "103";
                loadData();
                break;
            case R.id.venue_iv_py://TODO 攀岩
                if (lastim != null) {
                    upDateView(lastim, 1, 0);
                }
                upDateView(venue_iv_py, 0, 1);
                lastim = (ImageView) im;
                venue_tv_xm.setText("攀岩");
                activity_id = "82";
                loadData();
                break;
            case R.id.venue_iv_yy://TODO 游泳
                if (lastim != null) {
                    upDateView(lastim, 1, 0);
                }
                upDateView(venue_iv_yy, 0, 1);
                lastim = (ImageView) im;
                venue_tv_xm.setText("游泳");
                activity_id = "84";
                loadData();
                break;
            case R.id.venue_tv_quyu:
                venue_tv_xzq.setText("行政区");//TODO 行政区
                //判断行政区布局是否隐藏状态，是就显现
                if (!layout.isShown()) {
                    layout.setVisibility(View.VISIBLE);
                    changeItemHeight(layout, 0, MyUtils.dip2px(210, getActivity()));//动画
                } else {

                    changeItemHeight(layout, MyUtils.dip2px(210, getActivity()), 0);

                    layout.setVisibility(View.GONE);
                }

                loadDistrictInfoData();//加载行政区数据
                dadapter = new CommonAdapter<DistrictInfo>(getActivity(), districtInfoList, R.layout.venue_arealistview_item) {
                    @Override
                    public void convert(ViewHolder helper, int position, DistrictInfo item) {
                        helper.setText(R.id.venue_tv_select, item.getDistrictText());
                        //填充行政区布局

                    }
                };
                venue_area_listview.setAdapter(dadapter);
                //根据不同的行政区进行筛选
                venue_area_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        DistrictInfo districtInfo = districtInfoList.get(i);
                        district_id = districtInfo.getDistrictId();
                        loadData();
                        venue_tv_xzq.setText(districtInfo.getDistrictText());
                        layout.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.venue_tv_shaixuan:
                venue_tv_xzq.setText("特色筛选");
                if (!layout.isShown()) {
                    layout.setVisibility(View.VISIBLE);
                    changeItemHeight(layout, 0, MyUtils.dip2px(210, getActivity()));
                } else {

                    changeItemHeight(layout, MyUtils.dip2px(210, getActivity()), 0);

                    layout.setVisibility(View.GONE);
                }
                loadFacilityInfoData();
                dadapter = new CommonAdapter<DistrictInfo>(getActivity(), districtInfoList, R.layout.venue_arealistview_item) {
                    @Override
                    public void convert(ViewHolder helper, int position, DistrictInfo item) {
                        helper.setText(R.id.venue_tv_select, item.getDistrictText());
                    }
                };
                venue_area_listview.setAdapter(dadapter);
                venue_area_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        DistrictInfo districtInfo = districtInfoList.get(i);
                        facility_id = districtInfo.getDistrictId();
                        loadData();
                        venue_tv_shaixuan.setText(districtInfo.getDistrictText());
                        layout.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.venue_tv_paixu:
                venue_tv_xzq.setText("排序");
                if (!layout.isShown()) {
                    layout.setVisibility(View.VISIBLE);
                    changeItemHeight(layout, 0, MyUtils.dip2px(210, getActivity()));
                } else {

                    changeItemHeight(layout, MyUtils.dip2px(210, getActivity()), 0);

                    layout.setVisibility(View.GONE);
                }

                dadapter = new CommonAdapter<DistrictInfo>(getActivity(), districtInfoList, R.layout.venue_arealistview_item) {
                    @Override
                    public void convert(ViewHolder helper, int position, DistrictInfo item) {
                        helper.setText(R.id.venue_tv_select, item.getDistrictText());
                    }
                };
                loadSortData();
                venue_area_listview.setAdapter(dadapter);
                venue_area_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        DistrictInfo districtInfo = districtInfoList.get(i);
                        sort = districtInfo.getDistrictId();
                        loadData();
                        venue_tv_paixu.setText(districtInfo.getDistrictText());
                        layout.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.tv_city://TODO 跳转到选择城市页面
                Intent intent = new Intent(getActivity(), CityActivity.class);
                String cityname = (String) venue_tv_city.getText();
                intent.putExtra("name", cityname);
                startActivityForResult(intent, 101);
                break;
            case R.id.drawview_iv_back://TODO 关闭抽屉
                drawerLayout.closeDrawer(drawerView);
                break;
        }
    }
    //TODO 加载抽屉页面list数据
    private void LoadResourceInfoData() {
        SubgymDao.getResourceInfo(itemid, new BaseCallBack() {
            @Override
            public void success(Object data) {
                resourceInfoList = new ArrayList<>();
                if (resourceInfoList != null) {
                    resourceInfoList.clear();
                }
                if (data != null) {
                    resourceInfoList.addAll((List<ResourseInfo>) data);
                    new Handler().post(new Runnable() {//TODO 开启线程更新UI
                        @Override
                        public void run() {
                            for (int i = 0; i < resourceInfoList.size(); i++) {
                                View linearView;

                                linearView = LayoutInflater.from(getActivity()).inflate(R.layout.venue_listinfo, null, false);

                                tv_time1 = (TextView) linearView.findViewById(R.id.tv_time1);

                                tv_price1 = (TextView) linearView.findViewById(R.id.tv_price1);

                                tv_remain1 = (TextView) linearView.findViewById(R.id.tv_remain1);

                                listlayout.addView(linearView);
                                //根据日期判断星期几
                                String timeText="周";
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar c = Calendar.getInstance();
                                try {
                                    c.setTime(format.parse(resourceInfoList.get(i).getTime()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                switch (c.get(Calendar.DAY_OF_WEEK)){
                                    case 1:
                                        timeText+="日";
                                        break;
                                    case 2:
                                        timeText+="一";
                                        break;
                                    case 3:
                                        timeText+="二";
                                        break;
                                    case 4:
                                        timeText+="三";
                                        break;
                                    case 5:
                                        timeText+="四";
                                        break;
                                    case 6:
                                        timeText+="五";
                                        break;
                                    case 7:
                                        timeText+="六";
                                        break;
                                }
                                //填充页面
                                tv_time1.setText(resourceInfoList.get(i).getTime().substring(5)+"\n"+timeText);
                                tv_price1.setText("￥"+Integer.valueOf(resourceInfoList.get(i).getPrice())/100+".0");
                                tv_remain1.setText("剩余场数："+resourceInfoList.get(i).getRemain());
                            }
                        }
                    });
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(getActivity(), "数据请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 加载抽屉信息数据
     */
    private void loadSubgymInfoData() {
        SubgymDao.getSubgymInfo(itemid, new BaseCallBack() {
            @Override
            public void success(Object data) {
                if (data != null) {

                    info = (SubgymsInfo) data;
                    Log.i("info", info.getAddress());
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            venue_tv_venue.setText(info.getSubGym_name());
                            String url = info.getPicture_url();
                            ImageLoader.getInstance().displayImage(url, venue_iv_venueimg, ImageLoaderUtil.getFadeInOption(300, true, true, true));
                            venue_iv_venueimg.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            venue_tv_position.setText(info.getAddress());
                            venue_tv_trff.setText(info.getTraffic_info());
                            tv_venue_num.setText("场地数量：" + info.getField_count());
                            tv_venue_evment.setText("场地环境：" + info.getEnvironment());
                            tv_venue_floor.setText("场地材质：" + info.getGround_material());
                            tv_venue_height.setText("场地挑高：" + info.getFloor_height());
                        }
                    });
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(getActivity(), "数据请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 加载行政区信息数据
     */
    private void loadDistrictInfoData() {
        DistrictDao.getDistrictInfoList(city_id, new BaseCallBack() {
            @Override
            public void success(Object data) {
                if (districtInfoList != null) {
                    districtInfoList.clear();
                }
                if (data != null) {
                    districtInfoList.addAll((List<DistrictInfo>) data);
                    //Log.i("info",venuesInfoList.toString());
                    dadapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                //isLoading=false;

                Toast.makeText(getActivity(), "数据请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 加载筛选功能数据
     */
    private void loadFacilityInfoData() {
        DistrictDao.getFacilityInfoList(new BaseCallBack() {
            @Override
            public void success(Object data) {
                if (districtInfoList != null) {
                    districtInfoList.clear();
                }
                if (data != null) {
                    districtInfoList.addAll((List<DistrictInfo>) data);
                    //Log.i("info",venuesInfoList.toString());
                    dadapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                //isLoading=false;

                Toast.makeText(getActivity(), "数据请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //加载排序数据
    private void loadSortData() {
        if (districtInfoList != null) {
            districtInfoList.clear();
        }

        DistrictInfo allinfo = new DistrictInfo();
        allinfo.setDistrictText("智能排序");
        allinfo.setDistrictId("");
        districtInfoList.add(allinfo);
        DistrictInfo downinfo = new DistrictInfo();
        downinfo.setDistrictText("价格（降序）");
        downinfo.setDistrictId("price,desc");
        districtInfoList.add(downinfo);
        DistrictInfo upinfo = new DistrictInfo();
        upinfo.setDistrictText("价格（升序）");
        upinfo.setDistrictId("price,asc");
        districtInfoList.add(upinfo);
        dadapter.notifyDataSetChanged();
    }

    /**
     * 筛选布局的动画
     *
     * @param view
     * @param start
     * @param end
     */
    private void changeItemHeight(final View view, final int start,
                                  final int end) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "xxx", 0, 1)
                .setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = (int) (start + (end - start) * progress);
                view.setLayoutParams(params);
            }
        });
        animator.start();
    }

    /**
     * 运动项目动画
     *
     * @param imageView
     * @param star
     * @param end
     */
    public void upDateView(final ImageView imageView, float star, float end) {
        final ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "xxx", star, end).setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float progress = (float) animator.getAnimatedValue();
                imageView.setPivotX(imageView.getWidth() / 2);
                imageView.setPivotY(imageView.getHeight() / 2);
                imageView.setScaleX(1 + FACTOR * progress);
                imageView.setScaleY(1 + FACTOR * progress);
            }
        });
        animator.start();
    }

    /**
     * 配置ViewPager
     */
    private void initViewPager() {
        //图片地址
        String picture_url1 = "http://7o51kz.com2.z0.glb.qiniucdn.com//55f64ada-7e1b-4a22-bf52-cae717bf9791.jpeg";
        String picture_url2 = "http://7o51kz.com2.z0.glb.qiniucdn.com//8ffa95b4-1c74-48c4-b990-9fb65d77e910.png";
        imageViewList = new ArrayList<>();
        ImageView iv1 = new ImageView(getActivity());
        //加载图片
        ImageLoader.getInstance().displayImage(picture_url1, iv1, ImageLoaderUtil.getFadeInOption(300, true, true, true));
        iv1.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageViewList.add(iv1);

        ImageView iv2 = new ImageView(getActivity());
        ImageLoader.getInstance().displayImage(picture_url2, iv2, ImageLoaderUtil.getFadeInOption(300, true, true, true));
        iv2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageViewList.add(iv2);

    }


}
