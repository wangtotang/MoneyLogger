package com.tang.moneylogger.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tang.moneylogger.R;
import com.tang.moneylogger.bean.Activities;
import com.tang.moneylogger.config.Config;

import java.util.List;

/**
 * Created by Wangto Tang on 2015/6/22.
 */
public class ActivityAdapter extends BaseAdapter {

    List<Activities> activityList = null;
    Context context;

    public ActivityAdapter(List<Activities> list,Context context) {
        this.activityList = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return activityList.size();
    }

    @Override
    public Object getItem(int position) {
        return activityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_note, null);
            viewHolder.iv_activity_icon = (ImageView) convertView.findViewById(R.id.iv_activity_icon);
            viewHolder.tv_activity_description = (TextView) convertView.findViewById(R.id.tv_activity_description);
            viewHolder.tv_activity_time = (TextView) convertView.findViewById(R.id.tv_activity_time);
            viewHolder.tv_activity_amount = (TextView) convertView.findViewById(R.id.tv_activity_amount);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Activities activity = activityList.get(position);
        viewHolder.iv_activity_icon.setContentDescription(activity.getId() + "");
        viewHolder.tv_activity_description.setText(activity.getDescription());
        viewHolder.tv_activity_time.setText(activity.getTime());
        if (activity.getType_id()==0){
            viewHolder.tv_activity_amount.setText("﹣"+String.valueOf(activity.getAmount()));
            viewHolder.iv_activity_icon.setImageResource(Config.expenseIcon[activity.getIcon()]);
        }else if(activity.getType_id()==1){
            viewHolder.tv_activity_amount.setText("+ "+String.valueOf(activity.getAmount()));
            viewHolder.iv_activity_icon.setImageResource(Config.incomeIcon[activity.getIcon()]);
        }
        return convertView;
    }

    static class ViewHolder{
        ImageView iv_activity_icon;
        TextView tv_activity_description;
        TextView tv_activity_time;
        TextView tv_activity_amount;
    }

    public void remove(Activities activity){
        activityList.remove(activity);
        notifyDataSetChanged();
    }

    public void update(List<Activities> activityList){
        this.activityList = activityList;
        notifyDataSetChanged();
    }

}
