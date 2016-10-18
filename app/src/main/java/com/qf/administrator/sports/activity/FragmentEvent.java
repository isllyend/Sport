package com.qf.administrator.sports.activity;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.qf.administrator.sports.R;
import com.qf.administrator.sports.modules.event.activity.FragmentEvent_Football;
import com.qf.administrator.sports.modules.event.activity.FragmentEvent_One;
import com.qf.administrator.sports.modules.event.activity.FragmentEvent_News;
import com.qf.administrator.sports.modules.event.activity.FragmentEvent_Nba;

public class FragmentEvent extends BaseFragment {
    private FragmentPagerItemAdapter adapter;
    private SmartTabLayout viewPagerTab ;
    private ViewPager viewPager;

    @Override
    protected void findViews(View view) {
        viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        viewPager= (ViewPager) view.findViewById(R.id.viewpager);

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
         adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.titleA, FragmentEvent_One.class)
                .add(R.string.titleB, FragmentEvent_Nba.class)
                .add(R.string.titleC, FragmentEvent_News.class)
                .add(R.string.titleD, FragmentEvent_Football.class)
                .create());

        viewPager.setAdapter(adapter);

        viewPagerTab.setViewPager(viewPager);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_event;
    }
}
