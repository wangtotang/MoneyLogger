package com.tang.moneylogger.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tang.moneylogger.R;
import com.tang.moneylogger.adapter.ReportTypeAdapter;
import com.tang.moneylogger.ui.dialog.MyDatePickerDialog;
import com.tang.mybase.ui.BaseActivity;
import com.tang.mybase.ui.dialog.BaseDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Wangto Tang on 2015/6/29.
 */
public class ReportActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    String title = "报告";
    RelativeLayout rl_report_type,rl_report_start_time,rl_report_end_time;
    TextView tv_report_type,tv_report_start_time,tv_report_end_time;
    BaseDialog typeDialog;
    ReportTypeAdapter adapter;
    List<Boolean> checked;
    List<Integer> typeList;
    int startYear,startMonth;
    int endYear,endMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        initViews();
        init();
    }

    private void initViews() {
        rl_report_type = (RelativeLayout) findViewById(R.id.rl_report_type);
        rl_report_start_time = (RelativeLayout) findViewById(R.id.rl_report_start_time);
        rl_report_end_time = (RelativeLayout) findViewById(R.id.rl_report_end_time);
        tv_report_type = (TextView) findViewById(R.id.tv_report_type);
        tv_report_start_time = (TextView) findViewById(R.id.tv_report_start_time);
        tv_report_end_time = (TextView) findViewById(R.id.tv_report_end_time);
    }

    private void init() {
        initLeftTitleWithButtonAndMenu(title, new OnBackupListener(), R.drawable.btn_yes_selector, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(typeList.size() <= 0){
                    showToast("未选择类别！");
                    return;
                }
                if(endYear < startYear ) {
                    showToast("时间段错误！");
                    return;
                }else if(endYear == startYear){
                    if(endMonth < startMonth){
                        showToast("时间段错误！");
                        return;
                    }
                }
                    Intent intent = new Intent(ReportActivity.this, InfoActivity.class);
                    String startTime = startYear + "-" + startMonth;
                    String endTime = endYear + "-" + endMonth;
                    intent.putExtra("startTime", startTime);
                    intent.putExtra("endTime", endTime);
                    Integer[] id = new Integer[typeList.size()];
                    typeList.toArray(id);
                    intent.putExtra("id", id);
                    startActivity(intent);

            }
        });
        rl_report_type.setOnClickListener(this);
        rl_report_start_time.setOnClickListener(this);
        rl_report_end_time.setOnClickListener(this);
        adapter = new ReportTypeAdapter(this);
        checked = adapter.getChecked();
        typeList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        endYear = startYear = c.get(Calendar.YEAR);
        endMonth = startMonth = c.get(Calendar.MONTH);
        tv_report_start_time.setText(startYear + "年" + (startMonth + 1) + "月");
        tv_report_end_time.setText(endYear + "年" + (endMonth + 1) + "月");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_report_type:
                initTypeDialog();
                break;
            case R.id.rl_report_start_time:
                initStartTimeDialog();
                break;
            case R.id.rl_report_end_time:
                initEndTimeDialog();
                break;
        }
    }

    private void initTypeDialog() {
        typeDialog = new BaseDialog(this);
        typeDialog.show();
        typeDialog.setBounds(0, 0, (int) (getWindowManager().getDefaultDisplay().getWidth() * 0.8), 0);
        typeDialog.init(BaseDialog.noMessage);
        typeDialog.setTitle("选择类别");
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_report_type,null);
        ListView lv_report_type = (ListView) view.findViewById(R.id.lv_report_type);
        lv_report_type.setAdapter(adapter);
        typeDialog.setCustomView(view);
        typeDialog.setButtonForOnly("设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeDialog.dismiss();
            }
        });
        lv_report_type.setOnItemClickListener(this);
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
            typeList.remove(Integer.valueOf(position));
        }else{
            checked.set(position,true);
            typeList.add(Integer.valueOf(position));
        }
        adapter.update(checked);
    }

    private void initStartTimeDialog() {
        DatePickerDialog dialog = new MyDatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_report_start_time.setText(year + "年" + (monthOfYear + 1) + "月");
                        startYear = year;
                        startMonth = monthOfYear;
                    }
                },startYear,startMonth);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    private void initEndTimeDialog() {
        DatePickerDialog dialog = new MyDatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_report_end_time.setText(year + "年" + (monthOfYear + 1) + "月");
                        endYear = year;
                        endMonth = monthOfYear;
                    }
                },endYear,endMonth);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }
}
