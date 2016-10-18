package com.qf.administrator.sports.modules.event.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qf.administrator.sports.R;
import com.qf.administrator.sports.modules.event.i.OnImageLeftClickListener;

/**
 * Created by Chigo on 10/09/2016.
 */

public class EventDetailsTitleBar extends RelativeLayout {
    private TextView tv;
    private OnImageLeftClickListener onImageLeftClickListener;

    public void setOnImageLeftClickListener(OnImageLeftClickListener onImageLeftClickListener) {
        this.onImageLeftClickListener = onImageLeftClickListener;
    }

    public EventDetailsTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context c) {
        View view=View.inflate(c, R.layout.custom_event_details_titlebar,this);

        ImageView iv= (ImageView) view.findViewById(R.id.iv_left);
        tv= (TextView) view.findViewById(R.id.tv_title);
        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageLeftClickListener.onClick(v);
            }
        });
    }

    public  void setTitle(String text){
        tv.setText(text);
    }
}
