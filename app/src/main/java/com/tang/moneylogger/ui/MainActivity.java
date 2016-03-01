package com.tang.moneylogger.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.tang.moneylogger.R;
import com.tang.moneylogger.adapter.MainTabAdapter;
import com.tang.moneylogger.bean.Activities;
import com.tang.moneylogger.bean.Total;
import com.tang.moneylogger.config.Config;
import com.tang.moneylogger.config.MyApplication;
import com.tang.moneylogger.dao.ActivityDao;
import com.tang.moneylogger.dao.TotalDao;
import com.tang.moneylogger.dao.impl.ActivityDaoImpl;
import com.tang.moneylogger.dao.impl.TotalDaoImpl;
import com.tang.mybase.db.DBManager;
import com.tang.mybase.ui.BaseActivity;
import com.tang.mybase.ui.dialog.BaseDialog;
import com.tang.mybase.util.PixelUtil;
import com.tang.mybase.util.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 主界面Activity
 * Created by Wangto Tang on 2015/5/26.
 */
public class MainActivity extends BaseActivity {

    Indicator fiv_main_tab;
    ViewPager vp_main_content;
    IndicatorViewPager indicator_content;
    Thread thread;
    PieChart pc_expense;
    LineChart lc_total;
    DBManager dbManager;
    ActivityDao activityDao;
    TotalDao totalDao;
    RelativeLayout rl_time_actionbar;
    TextView tv_time_actionbar;
    int year,month,day;
    int startDay;
    List<Activities> activitiesList;
    List<Total> totalList;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            setActionBar(msg.what);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        indicator_content = new IndicatorViewPager(fiv_main_tab,vp_main_content);
        indicator_content.setAdapter(new MainTabAdapter(getSupportFragmentManager(), MainActivity.this));
        setActionBar(0);
        dbManager = MyApplication.getDBManager();
        activityDao = new ActivityDaoImpl(dbManager);
        totalDao = new TotalDaoImpl(dbManager);
        checkBudgetTip();
    }

    public void initViews(){
        fiv_main_tab = (Indicator) findViewById(R.id.fiv_main_tab);
        vp_main_content = (ViewPager) findViewById(R.id.vp_main_content);
        //fiv_main_tab.setScrollBar(new ColorBar(this, Color.parseColor("#49d3f1"), PixelUtil.dp2px(6)));
        fiv_main_tab.setScrollBar(new ColorBar(this, getResources().getColor(R.color.base_color_tint_blue), PixelUtil.dp2px(6)));
    }

    public void setActionBar(int index) {
        switch (index){
            case 0:
                initLeftTileWithMenu("Activity", R.drawable.btn_menu_add_selector, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, AddActivity.class);
                        intent.putExtra("from", "add");
                        intent.putExtra("to",0);
                        startActivity(intent);
                    }
                });
                break;
            case 1:
                initMiddleViewWithMenu(R.layout.item_time_actionbar, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPiePopupWindow(v);
                    }
                }, R.drawable.btn_menu_add_selector, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, AddActivity.class);
                        intent.putExtra("from", "add");
                        intent.putExtra("to",1);
                        startActivity(intent);
                    }
                });
                rl_time_actionbar = (RelativeLayout) findViewById(R.id.rl_time_actionbar);
                tv_time_actionbar = (TextView) rl_time_actionbar.findViewById(R.id.tv_time_actionbar);
                checkPie();
                break;
            case 2:
                initMiddleViewWithMenu(R.layout.item_time_actionbar, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showTrendPopupWindow(v);
                    }
                }, R.drawable.btn_menu_add_selector, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, AddActivity.class);
                        intent.putExtra("from", "add");
                        intent.putExtra("to",2);
                        startActivity(intent);
                    }
                });
                rl_time_actionbar = (RelativeLayout) findViewById(R.id.rl_time_actionbar);
                tv_time_actionbar = (TextView) rl_time_actionbar.findViewById(R.id.tv_time_actionbar);
                checkTrend();
                break;
            case 3:
                initLeftTitle("More");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        thread = new Thread() {
            @Override
            public void run() {
                int oldIndex = 0, newIndex = 0;
                while (!interrupted()) {
                    newIndex = indicator_content.getCurrentItem();
                    if (oldIndex != newIndex) {
                        handler.sendEmptyMessage(newIndex);
                    }
                    oldIndex = newIndex;
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        thread.interrupt();
    }

    protected void checkPie(){
        pc_expense = (PieChart) findViewById(R.id.pc_expense);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        tv_time_actionbar.setText((month + 1) + "-" + day + "," + year);
        String time = year+"-"+(month+1)+"-"+day;
        activitiesList = activityDao.queryForTime(time);
        setPieData(activitiesList);
    }

    private void setPieData(List<Activities> list) {
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            Activities activities = list.get(i);
            yVals.add(new Entry((float)activities.getAmount(), i));
            xVals.add(activities.getName());
        }
        PieDataSet dataSet = new PieDataSet(yVals, "");
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(Config.getColors());
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.BLACK);
        if(list.size() > 0) {
            pc_expense.setDrawHoleEnabled(true);
            pc_expense.setDrawCenterText(true);
            pc_expense.setCenterText(year + "年\n" + (month + 1) + "月" + day + "日");//设置中间文本
            pc_expense.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        }else{
            pc_expense.setDrawHoleEnabled(false);
            pc_expense.setDrawCenterText(false);
        }
        pc_expense.setData(data);
        pc_expense.invalidate();
    }

    protected void checkTrend(){
        lc_total = (LineChart) findViewById(R.id.lc_total);
        Calendar calendar = Calendar.getInstance();
        int nextYear = year = calendar.get(Calendar.YEAR);
        int nextMonth = month = calendar.get(Calendar.MONTH);
        int maxDay = TimeUtil.countDays(year + "-" + (month + 1));
        calendar.set(Calendar.DAY_OF_WEEK,1);
        startDay = calendar.get(Calendar.DAY_OF_MONTH);
        int endDay = startDay+6;
        if(maxDay - endDay < 0){
            nextMonth = month + 1;
            if(nextMonth > 11){
                nextYear += 1;
                nextMonth = 0;
            }
            endDay -= maxDay;
        }
        tv_time_actionbar.setText((month + 1) + "月" + startDay + "-" + (nextMonth + 1) + "月" + endDay);
        String[] times = TimeUtil.getDays(year + "-" + (month + 1) + "-" + startDay, nextYear + "-" + (nextMonth + 1) + "-" + endDay);
        totalList = totalDao.queryAllForTime(times);
        setTrendData(totalList, times);
    }

    private void setTrendData(List<Total> list,String[] times){
        int[] mColors = new int[] {
                Color.rgb(116,218,48),Color.rgb(245,69,51)
        };
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> value0 = new ArrayList<Entry>();
        ArrayList<Entry> value1 = new ArrayList<Entry>();
        for (int i = 0;i < times.length;i++) {
            for (Total total : list) {
                if(total.getType_id()==0&&times[i].equals(total.getTime())){
                    value0.add(new Entry((float) total.getAmount(), i));
                }else if(total.getType_id()==1&&times[i].equals(total.getTime())){
                    value1.add(new Entry((float) total.getAmount(), i));
                }
            }
            if(value0.size() <= i){
                value0.add(new Entry(0f,i));
            }
            if(value1.size() <= i){
                value1.add(new Entry(0f,i));
            }
            String[] date = times[i].split("-");
            String time = date[1]+"-"+date[2];
            xVals.add(time);
        }
        LineDataSet d0 = new LineDataSet(value0, "支出 ");
        d0.setLineWidth(3f);
        d0.setCircleSize(4f);
        d0.setColor(mColors[0]);
        d0.setCircleColor(mColors[0]);
        LineDataSet d1 = new LineDataSet(value1, "收入 ");
        d1.setLineWidth(3f);
        d1.setCircleSize(4f);
        d1.setColor(mColors[1]);
        d1.setCircleColor(mColors[1]);

        dataSets.add(d0);
        dataSets.add(d1);

        LineData data = new LineData(xVals, dataSets);
        lc_total.animateY(1500);
        lc_total.setData(data);
        lc_total.invalidate();
    }

    protected void showPiePopupWindow(View v){
        View view = View.inflate(MainActivity.this,R.layout.popup_window_pie,null);
        PopupWindow popupWindow = new PopupWindow();
        popupWindow.setContentView(view);
        popupWindow.setWidth(PixelUtil.dp2px(160));
        popupWindow.setHeight(PixelUtil.dp2px(40));
        ImageButton ib_pie_indicator_left = (ImageButton) view.findViewById(R.id.ib_pie_indicator_left);
        ib_pie_indicator_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextDay = day - 1;
                if (nextDay <= 0) {
                    month -= 1;
                    if (month < 0) {
                        year -= 1;
                        month = 11;
                    }
                    int maxDay = TimeUtil.countDays(year + "-" + (month + 1));
                    nextDay += maxDay;
                }
                day = nextDay;
                tv_time_actionbar.setText((month + 1) + "-" + day + "," + year);
                setPieData(activityDao.queryForTime(year + "-" + (month + 1) + "-" + day));
            }
        });
        ImageButton ib_pie_indicator_right = (ImageButton) view.findViewById(R.id.ib_pie_indicator_right);
        ib_pie_indicator_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maxDay = TimeUtil.countDays(year + "-" + (month + 1));
                int nextDay = day + 1;
                if (nextDay > maxDay) {
                    month += 1;
                    if (month > 11) {
                        year += 1;
                        month = 0;
                    }
                    nextDay -= maxDay;
                }
                day = nextDay;
                tv_time_actionbar.setText((month + 1) + "-" + day + "," + year);
                setPieData(activityDao.queryForTime(year + "-" + (month + 1) + "-" + day));
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.base_color_background)));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        int x = popupWindow.getWidth()/2-v.getWidth()/2;
        popupWindow.showAsDropDown(v, -x, 0);
    }

    protected void showTrendPopupWindow(View v){
        View view = View.inflate(MainActivity.this,R.layout.popup_window_trend,null);
        PopupWindow popupWindow = new PopupWindow();
        popupWindow.setContentView(view);
        popupWindow.setWidth(PixelUtil.dp2px(160));
        popupWindow.setHeight(PixelUtil.dp2px(40));
        ImageButton ib_pie_indicator_left = (ImageButton) view.findViewById(R.id.ib_pie_indicator_left);
        ib_pie_indicator_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maxDay;
                startDay -= 7;
                if (startDay < 0) {
                    month -= 1;
                    if (month < 0) {
                        year -= 1;
                        month = 11;
                    }
                    maxDay = TimeUtil.countDays(year + "-" + (month + 1));
                    startDay += maxDay;
                }
                int nextYear = year;
                int nextMonth = month;
                int endDay = startDay + 6;
                maxDay = TimeUtil.countDays(year + "-" + (month + 1));
                if (maxDay - endDay < 0) {
                    nextMonth = month + 1;
                    if (nextMonth > 11) {
                        nextYear += 1;
                        nextMonth = 0;
                    }
                    endDay -= maxDay;
                }
                tv_time_actionbar.setText((month + 1) + "月" + startDay + "-" + (nextMonth + 1) + "月" + endDay);
                String[] times = TimeUtil.getDays(year + "-" + (month + 1) + "-" + startDay, nextYear + "-" + (nextMonth + 1) + "-" + endDay);
                totalList = totalDao.queryAllForTime(times);
                setTrendData(totalList, times);
            }
        });
        ImageButton ib_pie_indicator_right = (ImageButton) view.findViewById(R.id.ib_pie_indicator_right);
        ib_pie_indicator_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maxDay = TimeUtil.countDays(year + "-" + (month + 1));
                startDay += 7;
                if (startDay > maxDay) {
                    month += 1;
                    if (month > 11) {
                        year += 1;
                        month = 0;
                    }
                    startDay -= maxDay;
                }
                int nextYear = year;
                int nextMonth = month;
                int endDay = startDay + 6;
                maxDay = TimeUtil.countDays(year + "-" + (month + 1));
                if (maxDay - endDay < 0) {
                    nextMonth = month + 1;
                    if (nextMonth > 11) {
                        nextYear += 1;
                        nextMonth = 0;
                    }
                    endDay -= maxDay;
                }
                tv_time_actionbar.setText((month + 1) + "月" + startDay + "-" + (nextMonth + 1) + "月" + endDay);
                String[] times = TimeUtil.getDays(year + "-" + (month + 1) + "-" + startDay, nextYear + "-" + (nextMonth + 1) + "-" + endDay);
                totalList = totalDao.queryAllForTime(times);
                setTrendData(totalList, times);
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.base_color_background)));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        int x = popupWindow.getWidth() / 2 - v.getWidth()/2;
        popupWindow.showAsDropDown(v, -x, 0);
    }

    public void checkBudgetTip(){
        boolean flag = MyApplication.getInstance().getSharedPreferencesUtil().isOpenBudgetTip();
        if(flag){
            Calendar calendar = Calendar.getInstance();
            if(calendar.get(Calendar.DAY_OF_MONTH)==1){
                final BaseDialog dialog = new BaseDialog(this);
                dialog.show();
                dialog.init(BaseDialog.noCustomView);
                dialog.setTitle("月初预算提醒");
                dialog.setMessage("是否进行预算？");
                dialog.setNegativeButton("取消", null);
                dialog.setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, AddActivity.class);
                        intent.putExtra("from", "add");
                        intent.putExtra("to",2);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
            }
        }
    }

    
}
