package com.qf.administrator.sports.modules.event.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qf.administrator.sports.R;
import com.qf.administrator.sports.activity.BaseFragment;
import com.qf.administrator.sports.adatper.CommonAdapter;
import com.qf.administrator.sports.adatper.ViewHolder;
import com.qf.administrator.sports.i.BaseCallBack;
import com.qf.administrator.sports.modules.event.bean.EventInfo;
import com.qf.administrator.sports.modules.event.dao.EventDao;
import com.qf.administrator.sports.util.ImageLoaderUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class FragmentEvent_One extends BaseFragment {
    private PullToRefreshListView listView;
    private CommonAdapter<EventInfo> adapter;
    private List<EventInfo> list;
    private int page = 0;
    private Banner banner;
    private List<String> images;
    private List<String> titles;
    private boolean isFirst;

    @Override
    protected void findViews(View view) {
        listView = (PullToRefreshListView) view.findViewById(R.id.re_listView);
        banner = (Banner) view.findViewById(R.id.banner);
    }

    @Override
    protected void initEvent() {
        //pullToRefreshListView 的上拉加载和下拉刷新
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 0;
                loadData();
            }

            //上拉加载
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                loadData();
            }
        });

        //item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
                EventInfo eventInfo = list.get(position - 1);

                //eventbus post()
//                EventBus.getDefault().post(eventInfo);
                Bundle bundle = new Bundle();
                bundle.putSerializable("info", eventInfo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //banner点击事件

        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                goIntent(position);
            }
        });
    }

    private void goIntent(int position) {
        Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
        EventInfo eventInfo = list.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", eventInfo);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void init() {
        list = new ArrayList<>();
        images = new ArrayList<>();
        titles = new ArrayList<>();

        adapter = new CommonAdapter<EventInfo>(getContext(), list, R.layout.adapter_item_event) {
            @Override
            public void convert(ViewHolder helper, int position, EventInfo item) {
                helper.setText(R.id.tv_match_name, item.getMatch_name());
                helper.setText(R.id.tv_address, item.getAddress());
                helper.setText(R.id.tv_match_sport, item.getMatch_sport());
                helper.setImageByUrl(R.id.iv_photo, item.getPhoto_url());
                helper.setText(R.id.tv_joined_enroll_num, item.getJoined_enroll_num() + "/");
                helper.setText(R.id.tv_max_enroll_num, item.getMax_enroll_num() + item.getMatch_team_type() + "报名");


                //设置活动状态
                TextView tv = helper.getView(R.id.tv_status);
                String status = item.getMatch_status();
                helper.setText(R.id.tv_status, status);
                if (status.equals(EventInfo.MatchStatus.ENROLLING)) {
                    tv.setTextColor(getResources().getColor(R.color.colorEnroll));
                } else if (status.equals(EventInfo.MatchStatus.OVER)) {
                    tv.setTextColor(getResources().getColor(R.color.colorOver));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.colorIng));
                }

                helper.setText(R.id.tv_begintime, item.getEnroll_begintime());
                helper.setText(R.id.tv_endtime, item.getEnroll_endtime());
                if ("0".equals(item.getItemlist()[0].getEnroll_fee())) {
                    helper.setText(R.id.tv_isfee, "免费");
                } else {
                    helper.setText(R.id.tv_isfee, "自费");
                }
            }
        };


        //加载适配器
        listView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        EventDao.getEventInfoList(page, new BaseCallBack() {
            @Override
            public void success(Object data) {
                listView.onRefreshComplete();// 关闭正在刷新的状态
                List<EventInfo> temp = (List<EventInfo>) data;


                if (page == 0) {
                    list.clear();
                }
                list.addAll(temp);
                adapter.notifyDataSetChanged();
//                if (!isFirst) {
                    initBanner();
//                    isFirst = true;
//                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                listView.onRefreshComplete();// 关闭正在刷新的状态
                Toast.makeText(getActivity(), "数据请求失败", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initBanner() {
        for (int i = 0; i < 5; i++) {
            String url = list.get(i).getPhoto_url();
            images.add(url);

            String name = list.get(i).getMatch_name();
            titles.add(name);
        }
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new MyImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.CubeIn);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);

        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_event_one;
    }


    //重写图片加载器
    public class MyImageLoader implements ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage((String) path, imageView, ImageLoaderUtil.getDefaultOption());

        }
    }
}
