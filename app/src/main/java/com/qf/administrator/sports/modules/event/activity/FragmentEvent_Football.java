package com.qf.administrator.sports.modules.event.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qf.administrator.sports.R;
import com.qf.administrator.sports.activity.BaseFragment;

import static com.qf.administrator.sports.R.id.ivDeleteText;

public class FragmentEvent_Football extends BaseFragment {
    private ImageView iv_delete,iv_bg;
    private EditText et_content;
    private TextView tv_search ,tv_match_name;
    private ListView lv;
    private RelativeLayout top;
    private LinearLayout layout;
    @Override
    protected void findViews(View view) {
        iv_delete = (ImageView) view.findViewById(ivDeleteText);
        iv_bg= (ImageView) view.findViewById(R.id.iv_bg);
        lv = (ListView) view.findViewById(R.id.tv_football_lv);
        top = (RelativeLayout) view.findViewById(R.id.top);
        et_content= (EditText) view.findViewById(R.id.et_content);
        tv_search= (TextView) view.findViewById(R.id.tv_search);
        tv_match_name= (TextView) view.findViewById(R.id.tv_match_name);

        layout= (LinearLayout) view.findViewById(R.id.layout);
    }

    @Override
    protected void initEvent() {
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "1111111111", Toast.LENGTH_SHORT).show();
            }
        });

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        //X图片的点击事件
        iv_delete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                et_content.setText("");
            }
        });
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_content.setText("");
                iv_delete.setVisibility(View.GONE);
                lv.setVisibility(View.VISIBLE);
                layout.setVisibility(View.VISIBLE);
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
    protected void init() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_event_football;
    }
}
