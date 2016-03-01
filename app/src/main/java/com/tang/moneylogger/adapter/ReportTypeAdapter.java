package com.tang.moneylogger.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tang.moneylogger.R;
import com.tang.moneylogger.config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Wangto Tang on 2015/6/25.
 */
public class ReportTypeAdapter extends BaseAdapter {

    List<Map<String,Integer>> typeList;
    Context context;
    List<Boolean> checked;

    public ReportTypeAdapter(Context context) {
        this.context = context;
        typeList = Config.getReportTypeList();
        checked = new ArrayList<>();
        for(int i = 0;i < typeList.size();i++ ){
            checked.add(false);
        }
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
        ViewHolder viewHolder = null;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context,R.layout.item_report_dialog,null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_report_dialog);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_report_dialog);
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
        viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
        viewHolder.checkBox.setChecked(checked.get(position));
        return convertView;
    }

    static class ViewHolder{
        TextView textView;
        CheckBox checkBox;
    }

    public List<Boolean> getChecked(){
        return checked;
    }

    public void update(List<Boolean> list){
        checked = list;
        notifyDataSetChanged();
    }

}
