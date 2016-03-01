package com.tang.moneylogger.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.tang.moneylogger.R;
import com.tang.moneylogger.adapter.AddTabAdapter;
import com.tang.moneylogger.bean.Activities;
import com.tang.moneylogger.bean.Total;
import com.tang.moneylogger.config.MyApplication;
import com.tang.moneylogger.dao.ActivityDao;
import com.tang.moneylogger.dao.TotalDao;
import com.tang.moneylogger.dao.impl.ActivityDaoImpl;
import com.tang.moneylogger.dao.impl.TotalDaoImpl;
import com.tang.mybase.db.DBManager;
import com.tang.mybase.ui.BaseActivity;
import com.tang.mybase.util.PixelUtil;

/**
 * Created by Wangto Tang on 2015/6/6.
 */
public class AddActivity extends BaseActivity{

    String title = "记录";
    Indicator fiv_add_tab;
    ViewPager vp_add_content;
    IndicatorViewPager indicator_content;
    Thread thread;
    DBManager dbManager;
    ActivityDao activityDao;
    TotalDao totalDao;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            setActionBar(msg.what);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initViews();
        init();
    }

    private void init() {
        indicator_content = new IndicatorViewPager(fiv_add_tab,vp_add_content);
        indicator_content.setAdapter(new AddTabAdapter(getSupportFragmentManager(), AddActivity.this));
        fiv_add_tab.setScrollBar(new ColorBar(this, getResources().getColor(R.color.base_color_tint_blue), PixelUtil.dp2px(6)));
        vp_add_content.setOffscreenPageLimit(3);
        setActionBar(0);
        Intent intent = getIntent();
        int to = intent.getIntExtra("to", 0);
        switch(to){
            case 0:
                indicator_content.setCurrentItem(0, false);
                break;
            case 1:
                indicator_content.setCurrentItem(1, false);
                break;
            case 2:
                indicator_content.setCurrentItem(2, false);
                break;
        }
        dbManager =  MyApplication.getDBManager();
        activityDao = new ActivityDaoImpl(dbManager);
        totalDao = new TotalDaoImpl(dbManager);
    }

    private void initViews() {
        fiv_add_tab = (Indicator) findViewById(R.id.fiv_add_tab);
        vp_add_content = (ViewPager) findViewById(R.id.vp_add_content);
    }

    public void setActionBar(int index) {
        switch (index){
            case 0:
                initLeftTitleWithButtonAndMenu(title, new OnBackupListener(), R.drawable.btn_yes_selector,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkExpense();
                            }
                });
                break;
            case 1:
                initLeftTitleWithButtonAndMenu(title, new OnBackupListener(), R.drawable.btn_yes_selector,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkIncome();
                            }
                        });
                break;
            case 2:
                initLeftTitleWithButtonAndMenu(title, new OnBackupListener(), R.drawable.btn_yes_selector,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkBudget();
                            }
                        });
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

    protected void checkExpense(){
        TextView tv_expense_type = (TextView) findViewById(R.id.tv_expense_type);
        TextView tv_expense_time = (TextView) findViewById(R.id.tv_expense_time);
        EditText et_expense_amount = (EditText) findViewById(R.id.et_expense_amount);
        EditText et_expense_description = (EditText) findViewById(R.id.et_expense_description);
        if(tv_expense_type.getTag() == null){
            showToast(R.string.tip_select_activity_type);
            return;
        }
        if(TextUtils.isEmpty(et_expense_amount.getText())){
            showToast(R.string.tip_input_activity_amount);
            return;
        }
        String from = getIntent().getStringExtra("from");
        int to = getIntent().getIntExtra("to", 0);
        Activities activities = null;
        String time = null;
        double amount = 0;
        if("update".equals(from)&&to==0){
            activities = (Activities) getIntent().getSerializableExtra("activity");
            time = activities.getTime();
            amount = activities.getAmount();
        }else{
            activities = new Activities();
        }
        activities.setName(tv_expense_type.getText().toString());
        activities.setType_id(0);
        activities.setIcon((Integer) tv_expense_type.getTag());
        activities.setAmount(Double.parseDouble(et_expense_amount.getText().toString()));
        if(TextUtils.isEmpty(et_expense_description.getText())){
            activities.setDescription(activities.getName());
        }else{
            activities.setDescription(et_expense_description.getText().toString());
        }
        activities.setTime((String)tv_expense_time.getTag());
        if("update".equals(from)&&to==0){
            activityDao.updateActivity(activities);
            Total oldTotal = totalDao.queryForTimeAndTypeId(time,0);
            oldTotal.setAmount(oldTotal.getAmount() - amount);
            totalDao.updateTotal(oldTotal);
        }else{
            activityDao.addActivity(activities);
        }
        Total total = totalDao.queryForTimeAndTypeId(activities.getTime(),activities.getType_id());
        if(total != null){
            total.setAmount(total.getAmount()+activities.getAmount());
            totalDao.updateTotal(total);
        }else{
            total = new Total();
            total.setType_id(activities.getType_id());
            total.setTime(activities.getTime());
            total.setAmount(activities.getAmount());
            totalDao.addTotal(total);
        }
        finish();
    }

    protected void checkIncome(){
        TextView tv_income_type = (TextView) findViewById(R.id.tv_income_type);
        TextView tv_income_time = (TextView) findViewById(R.id.tv_income_time);
        EditText et_income_amount = (EditText) findViewById(R.id.et_income_amount);
        EditText et_income_description = (EditText) findViewById(R.id.et_income_description);
        if(tv_income_type.getTag() == null){
            showToast(R.string.tip_select_activity_type);
            return;
        }
        if(TextUtils.isEmpty(et_income_amount.getText())){
            showToast(R.string.tip_input_activity_amount);
            return;
        }
        String from = getIntent().getStringExtra("from");
        int to = getIntent().getIntExtra("to", 1);
        Activities activities = null;
        String time = null;
        double amount = 0;
        if("update".equals(from)&&to==1){
            activities = (Activities) getIntent().getSerializableExtra("activity");
            time = activities.getTime();
            amount = activities.getAmount();
        }else{
            activities = new Activities();
        }
        activities.setName(tv_income_type.getText().toString());
        activities.setType_id(1);
        activities.setIcon((Integer) tv_income_type.getTag());
        activities.setAmount(Double.parseDouble(et_income_amount.getText().toString()));
        if(TextUtils.isEmpty(et_income_description.getText())){
            activities.setDescription(activities.getName());
        }else{
            activities.setDescription(et_income_description.getText().toString());
        }
        activities.setTime((String) tv_income_time.getTag());
        if("update".equals(from)&&to==1){
            activityDao.updateActivity(activities);
            Total oldTotal = totalDao.queryForTimeAndTypeId(time,1);
            oldTotal.setAmount(oldTotal.getAmount() - amount);
            totalDao.updateTotal(oldTotal);
        }else{
            activityDao.addActivity(activities);
        }
        Total total = totalDao.queryForTimeAndTypeId(activities.getTime(), activities.getType_id());
        if(total != null){
            total.setAmount(total.getAmount() + activities.getAmount());
            totalDao.updateTotal(total);
        }else{
            total = new Total();
            total.setType_id(activities.getType_id());
            total.setTime(activities.getTime());
            total.setAmount(activities.getAmount());
            totalDao.addTotal(total);
        }
        finish();
    }

    protected void checkBudget(){
        TextView tv_budget_time = (TextView) findViewById(R.id.tv_budget_time);
        EditText et_budget_amount = (EditText) findViewById(R.id.et_budget_amount);
        if(TextUtils.isEmpty(et_budget_amount.getText())){
            showToast(R.string.tip_input_activity_amount);
            return;
        }
        String time = (String)tv_budget_time.getTag();
        double amount = Double.parseDouble(et_budget_amount.getText().toString());
        Total total = totalDao.queryForTimeAndTypeId(time,2);
        if(total != null){
            total.setAmount(amount);
            totalDao.updateTotal(total);
        }else{
            total = new Total();
            total.setType_id(2);
            total.setTime(time);
            total.setAmount(amount);
            totalDao.addTotal(total);
        }
        finish();
    }
}
