package com.qf.administrator.sports.modules.event.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qf.administrator.sports.R;
import com.qf.administrator.sports.activity.BaseFragment;
import com.qf.administrator.sports.adatper.CommonAdapter;
import com.qf.administrator.sports.adatper.ViewHolder;
import com.qf.administrator.sports.i.BaseCallBack;
import com.qf.administrator.sports.modules.event.bean.NewsInfo;
import com.qf.administrator.sports.modules.event.dao.NewsDao;

import java.util.ArrayList;
import java.util.List;

public class FragmentEvent_News extends BaseFragment {
    private ListView lv;
    private CommonAdapter<NewsInfo>  adapter;
    private List<NewsInfo> list;
    private int page=1;
    private int num=20;

    //header
    private ImageView iv_delete;
    private EditText et_content;
    private TextView tv_search,tv_return;
    private String  word;//体育明星关键字
    private RelativeLayout relayout;
    private View view_footer;
    //footer
    private ImageView iv_anim;
    @Override
    protected void findViews(View view) {
        lv= (ListView) view.findViewById(R.id.new_lv);
        relayout= (RelativeLayout) view.findViewById(R.id.relayout);
        tv_return= (TextView) view.findViewById(R.id.tv_return);
    }

    @Override
    protected void initEvent() {
        //lv 每个item的点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),MyWebActivity.class);
                NewsInfo info=list.get(position-lv.getHeaderViewsCount());
                intent.putExtra("url",info.getUrl());
                intent.putExtra("title",info.getTitle());
                startActivity(intent);
            }
        });

        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.setVisibility(View.VISIBLE);
                relayout.setVisibility(View.GONE);
                et_content.setText("");
                loadData();
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void init() {
        list=new ArrayList<>();
        //添加listview的头部
        View view_header= LayoutInflater.from(getActivity()).inflate(R.layout.delete_letter,null);
        lv.addHeaderView(view_header);

        //header
        iv_delete = (ImageView) view_header.findViewById(R.id.ivDeleteText);
        et_content= (EditText) view_header.findViewById(R.id.et_content);
        tv_search= (TextView) view_header.findViewById(R.id.tv_search);

        initHeaderEvent();


        //添加底部
        view_footer= LayoutInflater.from(getActivity()).inflate(R.layout.footer_item,null);
        lv.addFooterView(view_footer);
        iv_anim= (ImageView) view_footer.findViewById(R.id.iv_anim);
        AnimationDrawable animation= (AnimationDrawable) iv_anim.getBackground();
        animation.start();


        //搜索的点击事件
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word=et_content.getText().toString();
                list.clear();
                adapter.notifyDataSetChanged();

                loadSerachData();
            }
        });

        adapter=new CommonAdapter<NewsInfo>(getActivity(),list,R.layout.adapter_news_item) {
            @Override
            public void convert(ViewHolder helper, int position, NewsInfo item) {
                    helper.setText(R.id.tv_title,item.getTitle());
                    helper.setText(R.id.tv_des,item.getDescription());
                    helper.setText(R.id.tv_ctime,item.getCtime());
                    helper.setImageByUrl(R.id.iv_left,item.getPicUrl());
//                ImageLoader.getInstance().displayImage(item.getPicUrl(),(ImageView) helper.getView(R.id.iv_left));

            }
        };

        //lv的滑动事件
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState)
                {
                    case SCROLL_STATE_IDLE:
                        // TODO by Chigo : 停止
                        Log.e("Chigo",lv.getLastVisiblePosition()+"====="+list.size());
                        if (lv.getLastVisiblePosition()==(list.size()+lv.getHeaderViewsCount())){
                            int childCount=lv.getChildCount();
                            View lastChildView = lv
                                    .getChildAt(childCount - 1);
                            Log.e("Chigo",lastChildView.getBottom()+"==="+lv.getBottom());
                            // 获取当前显示最后一个子控件
                            if (lastChildView.getBottom()==lv.getBottom()){
                                page++;
                                AnimationDrawable animation= (AnimationDrawable) iv_anim.getBackground();
                                animation.start();
                                loadData();
                            }
                        }
                        break;
                }            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        lv.setAdapter(adapter);
    }

    private void initHeaderEvent() {
            //X图片的点击事件
            iv_delete.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    et_content.setText("");

                }
            });

            //搜索文字的改变监听
            et_content.addTextChangedListener(new TextWatcher() {

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // TODO Auto-generated method stub

                }

                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                    // TODO Auto-generated method stub

                }

                public void afterTextChanged(Editable s) {
                    if (s.length() == 0) {
                        iv_delete.setVisibility(View.GONE);
                    } else {
                        iv_delete.setVisibility(View.VISIBLE);
                    }
                }
            });
    }

    @Override
    protected void loadData() {
        NewsDao.getNewsInfo(page, new BaseCallBack() {
            @Override
            public void success(Object data) {
                List<NewsInfo> temp= (List<NewsInfo>) data;
               /* // 当listview里面没有footer的情况给listview加上footer
                if (lv.getFooterViewsCount() == 0)
                {
                    lv.addFooterView(view_footer);// 添加尾部加载更多控件
                }*/
                // 如果返回的数据数量不足一页的数量，去掉footer，表示数据加载完毕
                if (temp != null && temp.size() < 10)
                {
                    lv.removeFooterView(view_footer);
                }
             /*   // 当page为1就代表下拉刷新,此时需要清空所有数据
                if (page == 1)
                {
                    list.clear();
                }*/

                list.addAll(temp);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(getActivity(), "请检查网络连接...",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSerachData(){
        NewsDao.getNewsInfo(page,word, new BaseCallBack() {
            @Override
            public void success(Object data) {
                List<NewsInfo> temp= (List<NewsInfo>) data;
                if (temp.size()!=0){
                    list.addAll(temp);
                    adapter.notifyDataSetChanged();
                }else {
                            lv.setVisibility(View.GONE);
                            relayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void failed(int errorCode, Object data) {

            }
        });
    }
    @Override
    protected int setViewId() {
        return R.layout.fragment_event_news;
    }
}
