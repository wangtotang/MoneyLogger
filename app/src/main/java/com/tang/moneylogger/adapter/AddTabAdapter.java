package com.tang.moneylogger.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.indicator.IndicatorViewPager;
import com.tang.moneylogger.R;
import com.tang.moneylogger.ui.fragment.BudgetFragment;
import com.tang.moneylogger.ui.fragment.ExpenseFragment;
import com.tang.moneylogger.ui.fragment.IncomeFragment;

/**
 * Created by Wangto Tang on 2015/6/22.
 */
public class AddTabAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

    private String[] tabName = {"支出","收入","预算"};
    private Fragment[] fragments = {new ExpenseFragment(),new IncomeFragment(),new BudgetFragment()};
    Context context;

    public AddTabAdapter(FragmentManager fragmentManager,Context context) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public int getCount() {
        return tabName.length;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_add_tab, container,false);
        }
        TextView tv_add_item = (TextView) convertView;
        tv_add_item.setText(tabName[position]);
        return tv_add_item;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        return fragments[position];
    }
}
