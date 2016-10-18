package com.qf.administrator.sports.modules.event.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.text.format.Time;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qf.administrator.sports.R;
import com.qf.administrator.sports.activity.BaseFragment;
import com.qf.administrator.sports.adatper.CommonAdapter;
import com.qf.administrator.sports.adatper.ViewHolder;
import com.qf.administrator.sports.i.BaseCallBack;
import com.qf.administrator.sports.modules.event.bean.NBAInfo;
import com.qf.administrator.sports.modules.event.dao.EventDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentEvent_Nba extends BaseFragment {
    private ImageView iv_left, iv_right;
    private ListView lv;
    private TextView tv_title;
    private CommonAdapter<NBAInfo> adapter;
    private List<NBAInfo> list;
    private Map<String, List<NBAInfo>> map;
    private List<String> list_key;

    private String date,currentDate;//记录日期
    private String current_key;
    private boolean isFirst=true;
    private int month,day,currentDay;
    @Override
    protected void findViews(View view) {
        lv = (ListView) view.findViewById(R.id.nba_lv);
        iv_left = (ImageView) view.findViewById(R.id.iv_left);
        iv_right = (ImageView) view.findViewById(R.id.iv_right);
        tv_title = (TextView) view.findViewById(R.id.tv_nba_tilte);

        initTime();
    }

    private void initTime() {
        Time t=new Time("GMT+8"); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        t.setToNow(); // 取得系统时间。
        month = t.month+1;
        day = t.monthDay;
//        Calendar c = Calendar.getInstance();
//        month  = c.get(Calendar.MONTH)+1;
//        day = c.get(Calendar.DAY_OF_MONTH)+1;
        currentDay=day;
        date= month+"-"+day;
        currentDate=date;
        isFirst=false;
    }

    @Override
    protected void initEvent() {
//        SimpleDateFormat sdf=new SimpleDateFormat("MM-dd");
//        Date currentTime=new Date(System.currentTimeMillis());
//        date=sdf.format(currentTime);

        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Math.abs(currentDay-(day-1))<=1){
                    day--;
                    date=month+"-"+day;
                    list.clear();
                    loadData();
                }else {
                    Toast.makeText(getActivity(), "抱歉,只能查看近三天的NBA赛事!",Toast.LENGTH_SHORT).show();
                }

            }
        });

        iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Math.abs(currentDay-(day+1))<=1){
                    day++;
                    date=month+"-"+day;
                    list.clear();
                    loadData();
                }else {
                    Toast.makeText(getActivity(), "抱歉,只能查看近三天的NBA赛事!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void init() {
        list_key = new ArrayList<>();
        list = new ArrayList<>();
        map = new HashMap<>();

        adapter = new CommonAdapter<NBAInfo>(getContext(), list, R.layout.adapter_nba_item) {
            @Override
            public void convert(ViewHolder helper, int position, final NBAInfo item) {
                helper.setText(R.id.tv_time, item.getTime());
                helper.setText(R.id.tv_status, item.getStatus());
                helper.setText(R.id.tv_player1_name, item.getPlayer1_name());
                helper.setText(R.id.tv_player2_name, item.getPlayer2_name());
                String score1 = item.getPlayer1_score();
                String score2 = item.getPlayer2_score();


                TextView tv1 = helper.getView(R.id.tv_player1_score);
                TextView tv2 = helper.getView(R.id.tv_player2_score);
                tv1.setTextColor(Color.GRAY);
                tv2.setTextColor(Color.GRAY);
                if (score1.contains("-")){
                    tv1.setTextColor(Color.BLACK);
                    tv2.setTextColor(Color.BLACK);
                }else {
                    if (Integer.parseInt(score1) > Integer.parseInt(score2)) {
                        tv1.setTextColor(Color.BLACK);
                    } else {
                        tv2.setTextColor(Color.BLACK);
                    }
                }
                helper.setText(R.id.tv_player1_score, score1);
                helper.setText(R.id.tv_player2_score, score2);
//
//                ImageView iv1 = helper.getView(R.id.iv_player1_logo);
//                ImageView iv2 = helper.getView(R.id.iv_player2_logo);
//                ImageLoader.getInstance().displayImage(item.getPlayer1logo(), iv1);
//                ImageLoader.getInstance().displayImage(item.getPlayer2logo(), iv2);
                helper.setImageByUrl(R.id.iv_player1_logo,item.getPlayer1logo());
                helper.setImageByUrl(R.id.iv_player2_logo,item.getPlayer2logo());

                TextView tv_count=helper.getView(R.id.tv_link1);
                TextView tv_vedio=helper.getView(R.id.tv_link2);
                tv_count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(),MyWebActivity.class);
                        intent.putExtra("url",item.getLink2url());
                        startActivity(intent);
                    }
                });
                tv_vedio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(),MyWebActivity.class);
                        intent.putExtra("url",item.getLink1url());
                        startActivity(intent);
                    }
                });
            }
        };
        lv.setAdapter(adapter);

    }

    @Override
    protected void loadData() {
        EventDao.getNBAtInfo(new BaseCallBack() {
            @Override
            public void success(Object data) {
                Map<String, List<NBAInfo>> map_temp = (Map<String, List<NBAInfo>>) data;
                List<NBAInfo> temp = new ArrayList<NBAInfo>();
                for (Map.Entry<String, List<NBAInfo>> entry : map_temp.entrySet()) {
                    String key = entry.getKey();
                    list_key.add(key);
                    if (key.substring(0,5).equals(date)){
                        temp = entry.getValue();
                        current_key=key;
                    }
                }

                list.addAll(temp);
                adapter.notifyDataSetChanged();

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (current_key.substring(0,5).equals(currentDate)){
                            tv_title.setText("今天"+"("+list.size()+"场比赛)");
                        }else
                        tv_title.setText(current_key+"("+list.size()+"场比赛)");
                    }
                });

              }


            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(getActivity(), "请检查网络连接...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_event_nba;
    }
}
