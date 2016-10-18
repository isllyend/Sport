package com.qf.administrator.sports.modules.event.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qf.administrator.sports.R;
import com.qf.administrator.sports.activity.FragmentMy;
import com.qf.administrator.sports.adatper.CommonAdapter;
import com.qf.administrator.sports.adatper.ViewHolder;
import com.qf.administrator.sports.i.BaseCallBack;
import com.qf.administrator.sports.modules.event.adatper.PopuWindowsAdapter;
import com.qf.administrator.sports.modules.event.bean.EventInfo;
import com.qf.administrator.sports.modules.event.bean.ItemListInfo;
import com.qf.administrator.sports.modules.event.dao.EventDao;
import com.qf.administrator.sports.modules.event.i.OnImageLeftClickListener;
import com.qf.administrator.sports.modules.event.widget.EventDetailsTitleBar;
import com.qf.administrator.sports.modules.my.activity.Login;
import com.qf.administrator.sports.modules.my.bean.ChangeBeanEvent;
import com.qf.administrator.sports.modules.my.bean.ChangeCreditsEvent;
import com.qf.administrator.sports.modules.my.bean.ChangeMoneyEvent;
import com.qf.administrator.sports.modules.my.bean.userinfo;
import com.qf.administrator.sports.util.ImageLoaderUtil;
import com.se7en.utils.DeviceUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Chigo on 10/09/2016.
 */

public class EventDetailsActivity extends AppCompatActivity implements OnImageLeftClickListener {
    private TextView tv_remark, tv_name, tv_match_start, tv_match_end,
            tv_enroll_start, tv_enroll_end, tv_enroll, tv_address;
    private int id = 0;
    private ListView lv;
    private LinearLayout parent1, parent2, parent3, parent4;
    private EventDetailsTitleBar titlebar;

    private boolean isCheck1, isCheck2, isCheck3, isCheck4;

    private double price;
    private ImageView iv_photo;

    private List<ItemListInfo> list;

    private EventInfo info;
    private CommonAdapter<ItemListInfo> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_event_details);

        findViews();
        init();
        initEvent();
        loadData();
    }

    private void findViews() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_remark = (TextView) findViewById(R.id.tv_remark);
        tv_match_start = (TextView) findViewById(R.id.tv_match_start);
        tv_match_end = (TextView) findViewById(R.id.tv_match_end);
        tv_enroll_start = (TextView) findViewById(R.id.tv_enroll_start);
        tv_enroll_end = (TextView) findViewById(R.id.tv_enroll_end);
        tv_enroll = (TextView) findViewById(R.id.tv_enroll);
        tv_address = (TextView) findViewById(R.id.tv_address);

        parent1 = (LinearLayout) findViewById(R.id.parent1);
        parent2 = (LinearLayout) findViewById(R.id.parent2);
        parent3 = (LinearLayout) findViewById(R.id.parent3);
        parent4 = (LinearLayout) findViewById(R.id.parent4);

        lv = (ListView) findViewById(R.id.lv);

        iv_photo = (ImageView) findViewById(R.id.iv_photo);

        titlebar = (EventDetailsTitleBar) findViewById(R.id.titlebar);
    }

    private void initEvent() {

        tv_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.popuwindow_item, null);
                final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);// 加上这个popupwindow中的ListView才可以接收点击事件
                //设置非PopupWindow区域是否可触摸
                popupWindow.setOutsideTouchable(false);
                //实例化一个ColorDrawable颜色为半透明
//                ColorDrawable dw = new ColorDrawable(0x00000000);
//                //设置SelectPicPopupWindow弹出窗体的背景
//                popupWindow.setBackgroundDrawable(dw);
                backgroundAlpha(EventDetailsActivity.this, 0.5f);

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backgroundAlpha(EventDetailsActivity.this, 1f);
                    }
                });

                popupWindow.showAtLocation(tv_enroll, Gravity.BOTTOM, 0, 0);

                ListView lv1 = (ListView) view.findViewById(R.id.popu_lv);

                ItemListInfo[] listInfos = info.getItemlist();
                PopuWindowsAdapter adapter1 = new PopuWindowsAdapter(listInfos, getApplicationContext());
                lv1.setAdapter(adapter1);

                TextView tv_dismiss = (TextView) view.findViewById(R.id.dismiss);
                ImageView iv_close = (ImageView) view.findViewById(R.id.close);
                iv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //销毁弹出框
                        popupWindow.dismiss();
                        backgroundAlpha(EventDetailsActivity.this, 1f);
                    }
                });
                tv_dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //销毁弹出框
                        popupWindow.dismiss();
                        backgroundAlpha(EventDetailsActivity.this, 1f);
                    }
                });


                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (!FragmentMy.islogin) {
                            Toast.makeText(EventDetailsActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EventDetailsActivity.this, Login.class));
                        } else if (price > userinfo.getCurrentUser(userinfo.class).getMoney()) {
                            Toast.makeText(EventDetailsActivity.this, "报名费:" + price + ",余额:" + userinfo.getCurrentUser(userinfo.class).getMoney() + "金额不足，请先去充值", Toast.LENGTH_SHORT).show();
                        } else {
                            userinfo user = userinfo.getCurrentUser(userinfo.class);
                            final double aftermoney = user.getMoney() - price;
                            user.setMoney(aftermoney);
                            ArrayList<EventInfo> sports = new ArrayList<EventInfo>();
                            if(user.getSports()!=null){
                                sports.addAll(user.getSports());
                            }
                            sports.add(info);
                            info.setPrice(price);
                            user.setSports(sports);
                            user.update(user.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(EventDetailsActivity.this, "报名成功", Toast.LENGTH_SHORT).show();
                                        EventBus.getDefault().post(new ChangeMoneyEvent(aftermoney));
                                        finish();
                                    } else {
                                        Toast.makeText(EventDetailsActivity.this, "报名失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });


        titlebar.setOnImageLeftClickListener(this);
    }

    private void init() {

        list = new ArrayList<>();

        Intent intent = this.getIntent();
        //获取标题
        info = (EventInfo) intent.getSerializableExtra("info");
        tv_name.setText(info.getMatch_name());
        // 获取ID
        id = Integer.parseInt(info.getId());

        //设置比赛时间
        tv_match_start.setText(info.getMatch_begintime());
        tv_match_end.setText(info.getMatch_endtime());

        //设置报名时间
        tv_enroll_start.setText(info.getEnroll_begintime());
        tv_enroll_end.setText(info.getEnroll_endtime());

        //设置地址
        tv_address.setText(info.getAddress());

        //设置图片
        ImageLoader.getInstance().displayImage(info.getPhoto_url(), iv_photo, ImageLoaderUtil.getDefaultOption());


        String match_status = info.getMatch_status();
        if ("报名中".equals(match_status)) {
            tv_enroll.setVisibility(View.VISIBLE);
        } else {
            tv_enroll.setVisibility(View.GONE);
        }

        //获取团队类型
        final String match_team_type = info.getMatch_team_type();
        //获取itemlist
        ItemListInfo[] itemlist = info.getItemlist();
        for (int i = 0; i < itemlist.length; i++) {
            list.add(itemlist[i]);
        }
        //适配器加载
        adapter = new CommonAdapter<ItemListInfo>(this, list, R.layout.adapter_itemlist) {
            @Override
            public void convert(ViewHolder helper, int position, ItemListInfo item) {
                helper.setText(R.id.item_name, item.getItem_name());
                //获得itemlist的费用
                String enroll_fee = item.getEnroll_fee();
                double fee = Double.parseDouble(enroll_fee);
                price = fee / 100;
                helper.setText(R.id.enroll_fee, fee / 100 + "元/" + match_team_type);
            }
        };
        lv.setAdapter(adapter);
    }

    //加载remark内容
    private void loadData() {
        EventDao.getEventInfoDetails(id, new BaseCallBack() {
            @Override
            public void success(Object data) {
                String result = data.toString();
                tv_remark.setText(Html.fromHtml(result));
            }

            @Override
            public void failed(int errorCode, Object data) {

            }
        });


    }

    //详情页面的item点击效果
    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.layout1:
                if (isCheck1) {
                    changHeight(parent1, DeviceUtils.dip2px(110), DeviceUtils.dip2px(55));
                    isCheck1 = false;
                } else {
                    changHeight(parent1, DeviceUtils.dip2px(55), DeviceUtils.dip2px(110));
                    isCheck1 = true;
                }
                break;
            case R.id.layout2:
                if (isCheck2) {
                    changHeight(parent2, DeviceUtils.dip2px(110), DeviceUtils.dip2px(55));
                    isCheck2 = false;
                } else {
                    changHeight(parent2, DeviceUtils.dip2px(55), DeviceUtils.dip2px(110));
                    isCheck2 = true;
                }
                break;
            case R.id.layout3:
                if (isCheck3) {
                    changHeight(parent3, DeviceUtils.dip2px(160), DeviceUtils.dip2px(55));
                    isCheck3 = false;
                } else {
                    changHeight(parent3, DeviceUtils.dip2px(55), DeviceUtils.dip2px(160));
                    isCheck3 = true;
                }
                break;
            case R.id.layout4:
                if (isCheck4) {
                    changHeight(parent4, DeviceUtils.dip2px(110), DeviceUtils.dip2px(55));
                    isCheck4 = false;
                } else {
                    changHeight(parent4, DeviceUtils.dip2px(55), DeviceUtils.dip2px(110));
                    isCheck4 = true;
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //伸缩动画
    public void changHeight(final View view, final int start, final int end) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "xxx", 0, 1);
        animator.setDuration(500);
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


    //titlebar左边按钮的点击事件
    @Override
    public void onClick(View v) {
        finish();
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
}
