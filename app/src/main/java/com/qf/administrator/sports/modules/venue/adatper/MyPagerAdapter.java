package com.qf.administrator.sports.modules.venue.adatper;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2016/10/9.
 */
public class MyPagerAdapter extends PagerAdapter {
   List<ImageView> list;

    public MyPagerAdapter(List<ImageView> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view=list.get(position % list.size());
        ViewParent vp= view.getParent();
        if(vp!=null){
            ViewGroup parent= (ViewGroup) vp;
            parent.removeView(view);
        }
        container.addView(view);

        return (View) list.get(position % list.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.removeView(list.get(position % list.size()));
    }
}
