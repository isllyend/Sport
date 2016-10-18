package com.qf.administrator.sports.modules.my.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qf.administrator.sports.R;


/**
 * Created by Administrator on 2016/9/8.
 */
public class My_item extends RelativeLayout {
    private TextView tvTitle;
    private ImageView ivLeft;
    private ImageView ivRight;

    public My_item(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = View.inflate(context, R.layout.my_item, this);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        ivLeft = (ImageView) view.findViewById(R.id.iv_left);
        ivRight = (ImageView) view.findViewById(R.id.iv_right);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TopMenu);
        String textTitle = a.getString(R.styleable.TopMenu_text_title);
        int resIdLeft = a.getResourceId(R.styleable.TopMenu_img_left,
                0);
        int resIdRight = a.getResourceId(R.styleable.TopMenu_img_right,
                0);
        tvTitle.setText(textTitle);
        ivLeft.setImageResource(resIdLeft);
        ivRight.setImageResource(resIdRight);
    }
}
