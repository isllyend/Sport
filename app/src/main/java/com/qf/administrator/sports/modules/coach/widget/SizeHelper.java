package com.qf.administrator.sports.modules.coach.widget;

import android.content.Context;

/**
 * Created by Administrator on 2016/10/7.
 */
public class SizeHelper {
    public static float dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dp * scale);
    }
}
