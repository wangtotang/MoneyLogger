package com.tang.moneylogger.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tang.moneylogger.R;

/**
 * Created by Wangto Tang on 2015/6/29.
 */
public class OtherAdapter extends BaseAdapter {

    Context context;

    int[] icon = new int[]{R.mipmap.icon_graph,R.mipmap.icon_setting,R.mipmap.icon_about};
    String[] name = new String[]{"报告","设置","关于"};

    public OtherAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView =  View.inflate(context,R.layout.item_other_fragment,null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_other_fragment);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(name[position]);
        viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(0,icon[position],0,0);
        return convertView;
    }

    static class ViewHolder{
        TextView textView;
    }
}
