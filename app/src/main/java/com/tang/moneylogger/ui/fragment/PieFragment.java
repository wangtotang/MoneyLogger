package com.tang.moneylogger.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.tang.moneylogger.R;
import com.tang.mybase.ui.fragment.BaseFragment;


public class PieFragment extends BaseFragment{

    private PieChart pc_expense;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pie);
        initViews();
        init();
    }

    public void initViews(){
        pc_expense = (PieChart) findViewById(R.id.pc_expense);
    }

    public void init(){
        pc_expense.setUsePercentValues(true);//false显示度数，true显示百分比
        pc_expense.setDescription(null);//不设置会默认description
        pc_expense.setNoDataText("您没有记录任何消费哦！");
        pc_expense.setDragDecelerationFrictionCoef(0.95f);//设置动画缓慢减速
        pc_expense.setDrawSliceText(false);
        //设置中间圆圈
        pc_expense.setDrawHoleEnabled(true);//设置是否中间有圆圈
        pc_expense.setHoleColorTransparent(true);//设置中间圆圈是否透明
        pc_expense.setTransparentCircleColor(Color.BLACK);//设置中间圆圈外围的颜色
        pc_expense.setHoleRadius(40f);//中间圆圈内圆半径
        pc_expense.setTransparentCircleRadius(44f);//中间圆圈外圆半径
        pc_expense.setDrawCenterText(true);//设置是否显示中间文本
        pc_expense.setCenterTextSize(16f);
        pc_expense.setRotationAngle(0);
        // enable rotation of the chart by touch
        pc_expense.setRotationEnabled(false);//设置是否可以旋转
        //设置图标说明
        Legend legend = pc_expense.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setXEntrySpace(10f);
        legend.setYOffset(46f);
        legend.setYEntrySpace(0f);
        legend.setFormSize(16f);
        legend.setTextSize(14f);
        legend.setWordWrapEnabled(true);
    }

}
