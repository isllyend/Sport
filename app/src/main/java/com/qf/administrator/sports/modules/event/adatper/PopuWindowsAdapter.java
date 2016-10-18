package com.qf.administrator.sports.modules.event.adatper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qf.administrator.sports.R;
import com.qf.administrator.sports.modules.event.bean.ItemListInfo;

/**
 * Created by Chigo on 10/10/2016.
 */

public class PopuWindowsAdapter extends BaseAdapter {
    private ItemListInfo[] list;
    private Context context;

    public PopuWindowsAdapter(ItemListInfo[] list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return list[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder{
         TextView match_name,enroll_fee;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
       if (convertView==null){
           convertView= LayoutInflater.from(context).inflate(R.layout.adapter_popuwindow,parent,false);
           viewHolder=new ViewHolder();
           convertView.setTag(viewHolder);
       }else {
           viewHolder= (ViewHolder) convertView.getTag();
       }
        viewHolder.match_name= (TextView) convertView.findViewById(R.id.match_name);
        viewHolder.enroll_fee= (TextView) convertView.findViewById(R.id.fee);

        viewHolder.match_name.setText(list[position].getItem_name());
        String fee=list[position].getEnroll_fee();

        viewHolder.enroll_fee.setText(Double.parseDouble(fee)/100+"元");
        return convertView;
    }
}
