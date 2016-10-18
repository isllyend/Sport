package com.qf.administrator.sports.modules.venue.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qf.administrator.sports.R;
import com.qf.administrator.sports.activity.BaseActivity;

/**
 * Created by Administrator on 2016/10/9.
 */
public class CityActivity extends BaseActivity {

    private ImageView venue_iv_back;
    private TextView currentcity,venue_tv_shanghai,venue_tv_nanjing,venue_tv_suzhou,venue_tv_ningbo,venue_tv_jian,venue_tv_jinan;
    @Override
    protected void findViews() {
        currentcity= (TextView) findViewById(R.id.venue_tv_currentcity);
        venue_iv_back= (ImageView) findViewById(R.id.venue_iv_back);
        venue_tv_shanghai= (TextView) findViewById(R.id.venue_tv_shanghai);
        venue_tv_nanjing= (TextView) findViewById(R.id.venue_tv_nanjing);
        venue_tv_suzhou= (TextView) findViewById(R.id.venue_tv_suzhou);
        venue_tv_ningbo= (TextView) findViewById(R.id.venue_tv_ningbo);
        venue_tv_jian= (TextView) findViewById(R.id.venue_tv_jian);
        venue_tv_jinan= (TextView) findViewById(R.id.venue_tv_jinan);
    }

    @Override
    protected void initEvent() {
        venue_iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=getIntent();
                String recevie=intent.getStringExtra("name");
                intent.putExtra("rname",recevie);
                setResult(103,intent);

                finish();
            }
        });
    }

    @Override
    protected void init() {
        final Intent intent=getIntent();
        String recevie=intent.getStringExtra("name");
        currentcity.setText(recevie);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int setViewId() {
        return R.layout.select_city_activity;
    }


    public void doClick(View view){
        Intent intent=getIntent();
        switch (view.getId()){
            case R.id.venue_tv_shanghai:
                intent.putExtra("cityId","310100");
                intent.putExtra("rname",venue_tv_shanghai.getText());
                setResult(102,intent);
                finish();
                break;
            case R.id.venue_tv_nanjing:
                intent.putExtra("cityId","320100");
                intent.putExtra("rname",venue_tv_nanjing.getText());
                setResult(102,intent);
                finish();
                break;
            case R.id.venue_tv_suzhou:
                intent.putExtra("cityId","320500");
                intent.putExtra("rname",venue_tv_suzhou.getText());
                setResult(102,intent);
                finish();
                break;
            case R.id.venue_tv_ningbo:
                intent.putExtra("cityId","330200");
                intent.putExtra("rname",venue_tv_ningbo.getText());
                setResult(102,intent);
                finish();
                break;
            case R.id.venue_tv_jian:
                intent.putExtra("cityId","360800");
                intent.putExtra("rname",venue_tv_jian.getText());
                setResult(102,intent);
                finish();
                break;
            case R.id.venue_tv_jinan:
                intent.putExtra("cityId","370100");
                intent.putExtra("rname",venue_tv_jinan.getText());
                setResult(102,intent);
                finish();
                break;
        }
    }
}
