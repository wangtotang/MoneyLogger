package com.tang.moneylogger.ui.fragment;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.tang.moneylogger.R;
import com.tang.mybase.ui.fragment.BaseFragment;

/**
 * Created by Wangto Tang on 2015/6/22.
 */
public class TrendFragment extends BaseFragment {

    LineChart lc_total;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trend);
        initViews();
        init();
    }

    public void initViews(){
        lc_total = (LineChart) findViewById(R.id.lc_total);
    }
    
    public void init(){
        lc_total.setDrawGridBackground(false);//设置是否有背景
        lc_total.setDescription(null);//设置标题
        // enable touch gestures
        lc_total.setTouchEnabled(false);
        lc_total.setDragDecelerationFrictionCoef(0.95f);
        //lc_total.getAxisRight().setEnabled(false);
        XAxis xAxis = lc_total.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelsToSkip(0);
        //xAxis.setSpaceBetweenLabels(7);

        //lc_total.animateX(1500);
        Legend legend = lc_total.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        legend.setFormSize(16f);
        legend.setTextSize(14f);
        legend.setWordWrapEnabled(true);
    }
}
