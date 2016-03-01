package com.tang.moneylogger.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tang.moneylogger.R;
import com.tang.moneylogger.config.Config;

import java.util.List;
import java.util.Map;

/**
 * Created by Wangto Tang on 2015/6/25.
 */
public class IncomeTypeAdapter extends BaseAdapter{

    Context context;
    List<Map<String,Integer>> typeList;

    public IncomeTypeAdapter(Context context){
        this.context = context;
        typeList = Config.getIncomeTypeList();
    }

    @Override
    public int getCount() {
        return typeList.size();
    }

    @Override
    public Object getItem(int position) {
        return typeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context,R.layout.item_income_dialog,null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_income_dialog);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Map<String,Integer> map = typeList.get(position);
        String name = null;
        int icon = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            name = entry.getKey();
            icon = entry.getValue();
        }
        viewHolder.textView.setText(name);
        viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(0,icon,0,0);
        return convertView;
    }

    static class ViewHolder{
        TextView textView;
    }
}
