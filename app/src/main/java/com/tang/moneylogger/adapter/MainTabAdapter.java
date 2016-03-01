package com.tang.moneylogger.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shizhefei.view.indicator.IndicatorViewPager;
import com.tang.moneylogger.R;
import com.tang.moneylogger.ui.fragment.NoteFragment;
import com.tang.moneylogger.ui.fragment.OtherFragment;
import com.tang.moneylogger.ui.fragment.PieFragment;
import com.tang.moneylogger.ui.fragment.TrendFragment;

/**
 * Created by Wangto Tang on 2015/6/16.
 */
public class MainTabAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

    private int[] icon = {R.drawable.icon_note_selector,R.drawable.icon_pie_selector,
                   R.drawable.icon_trend_selector,R.drawable.icon_other_selector};
    private Fragment[] fragments = {new NoteFragment(),new PieFragment(),new TrendFragment(),new OtherFragment()};
    Context context;

    public MainTabAdapter(android.support.v4.app.FragmentManager fragmentManager,Context context) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public int getCount() {
        return icon.length;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_main_tab, container,false);
        }
        ImageView iv_main_item = (ImageView) convertView;
        iv_main_item.setImageResource(icon[position]);
        return iv_main_item;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        return fragments[position];
    }


}
