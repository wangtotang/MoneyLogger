package com.tang.moneylogger.ui.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tang.moneylogger.R;
import com.tang.moneylogger.adapter.BudgetTypeAdapter;
import com.tang.moneylogger.bean.Activities;
import com.tang.moneylogger.bean.Total;
import com.tang.moneylogger.config.MyApplication;
import com.tang.moneylogger.dao.ActivityDao;
import com.tang.moneylogger.dao.TotalDao;
import com.tang.moneylogger.dao.impl.ActivityDaoImpl;
import com.tang.moneylogger.dao.impl.TotalDaoImpl;
import com.tang.moneylogger.ui.AddActivity;
import com.tang.moneylogger.ui.dialog.MyDatePickerDialog;
import com.tang.mybase.ui.dialog.BaseDialog;
import com.tang.mybase.ui.fragment.BaseFragment;
import com.tang.mybase.util.EditTextUtil;
import com.tang.mybase.util.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Wangto Tang on 2015/6/23.
 */
public class BudgetFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    RelativeLayout rl_budget_type,rl_budget_time;
    TextView tv_budget_type,tv_budget_time;
    EditText et_budget_amount;
    AddActivity addActivity;
    BaseDialog typeDialog;
    BudgetTypeAdapter adapter;
    int year,month;
    List<String> typeList;
    List<Boolean> checked;
    ActivityDao activityDao;
    TotalDao totalDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_budget);
        initViews();
        init();
    }

    private void initViews() {
        rl_budget_type = (RelativeLayout) findViewById(R.id.rl_budget_type);
        rl_budget_time = (RelativeLayout) findViewById(R.id.rl_budget_time);
        tv_budget_type = (TextView) findViewById(R.id.tv_budget_type);
        tv_budget_time = (TextView) findViewById(R.id.tv_budget_time);
        et_budget_amount = (EditText) findViewById(R.id.et_budget_amount);
    }

    private void init() {
        addActivity = (AddActivity) getActivity();
        EditTextUtil.setPricePoint(et_budget_amount);
        adapter = new BudgetTypeAdapter(addActivity);
        rl_budget_type.setOnClickListener(this);
        rl_budget_time.setOnClickListener(this);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        tv_budget_time.setText(year + "年" + (month + 1) + "月");
        tv_budget_time.setTag(year + "-" + (month + 1));
        EditTextUtil.setPricePoint(et_budget_amount);
        checked = adapter.getChecked();
        activityDao = new ActivityDaoImpl(MyApplication.getDBManager());
        totalDao = new TotalDaoImpl(MyApplication.getDBManager());
        typeList = new ArrayList<>();
        if(totalDao.isExisted(2,(String)tv_budget_time.getTag())){
            Total total = totalDao.queryForTimeAndTypeId((String)tv_budget_time.getTag(),2);
            et_budget_amount.setText(total.getAmount()+"");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_budget_type:
                initTypeDialog();
                break;
            case R.id.rl_budget_time:
                initTimeDialog();
                break;
        }
    }

    private void initTypeDialog() {
        typeDialog = new BaseDialog(addActivity);
        typeDialog.show();
        typeDialog.setBounds(0,0,(int)(addActivity.getWindowManager().getDefaultDisplay().getWidth()*0.8),0);
        typeDialog.init(BaseDialog.noMessage);
        typeDialog.setTitle("选择类别");
        View view = LayoutInflater.from(addActivity).inflate(R.layout.dialog_budget_type,null);
        ListView lv_budget_type = (ListView) view.findViewById(R.id.lv_budget_type);
        lv_budget_type.setAdapter(adapter);
        typeDialog.setCustomView(view);
        typeDialog.setButtonForOnly("设置", new MyListener());
        lv_budget_type.setOnItemClickListener(this);
    }

    private void initTimeDialog() {
        DatePickerDialog dialog = new MyDatePickerDialog(addActivity, AlertDialog.THEME_HOLO_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_budget_time.setText(year+"年"+(monthOfYear+1)+"月");
                        tv_budget_time.setTag(year + "-" + (monthOfYear + 1));
                        BudgetFragment.this.year = year;
                        month = monthOfYear;
                    }
                },year,month);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String,Integer> type = (Map<String, Integer>) adapter.getItem(position);
        String name = null;
        for (Map.Entry<String, Integer> entry : type.entrySet()) {
            name = entry.getKey();
        }
         if(checked.get(position)){
             checked.set(position,false);
             typeList.remove(name);
         }else{
             checked.set(position,true);
             typeList.add(name);
         }
        adapter.update(checked);
    }


    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String time = year+"-"+month;
            String[] times = TimeUtil.getDays(time);
            String[] names = new String[typeList.size()];
            typeList.toArray(names);
            if(names.length > 0) {
                List<Activities> list = activityDao.queryForNameAndTime(names, times);
                double result = 0;
                for (Activities activities : list) {
                    result += activities.getAmount();
                }
                et_budget_amount.setText(result + "");
            }
            typeDialog.dismiss();
        }
    }

}
