package com.tang.moneylogger.ui.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tang.moneylogger.R;
import com.tang.moneylogger.adapter.IncomeTypeAdapter;
import com.tang.moneylogger.bean.Activities;
import com.tang.moneylogger.ui.AddActivity;
import com.tang.mybase.ui.dialog.BaseDialog;
import com.tang.mybase.ui.fragment.BaseFragment;
import com.tang.mybase.util.EditTextUtil;
import com.tang.mybase.util.TimeUtil;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by Wangto Tang on 2015/6/23.
 */
public class IncomeFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    RelativeLayout rl_income_type,rl_income_time;
    TextView tv_income_type,tv_income_time;
    EditText et_income_amount,et_income_description;
    AddActivity addActivity;
    BaseDialog typeDialog;
    IncomeTypeAdapter adapter;
    int year,month,day;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_income);
        initViews();
        init();
    }

    private void initViews() {
        rl_income_type = (RelativeLayout) findViewById(R.id.rl_income_type);
        rl_income_time = (RelativeLayout) findViewById(R.id.rl_income_time);
        tv_income_type = (TextView) findViewById(R.id.tv_income_type);
        tv_income_time = (TextView) findViewById(R.id.tv_income_time);
        et_income_amount = (EditText) findViewById(R.id.et_income_amount);
        et_income_description = (EditText) findViewById(R.id.et_income_description);
    }

    private void init() {
        addActivity = (AddActivity) getActivity();
        String from = addActivity.getIntent().getStringExtra("from");
        int to = addActivity.getIntent().getIntExtra("to",1);
        if("update".equals(from)&&to==1){
            Activities activity = (Activities) addActivity.getIntent().getSerializableExtra("activity");
            tv_income_type.setText(activity.getName());
            tv_income_type.setTag(activity.getIcon());
            et_income_amount.setText(activity.getAmount() + "");
            et_income_description.setText(activity.getDescription());
            tv_income_time.setText(TimeUtil.getNianYueRi(activity.getTime()));
            tv_income_time.setTag(activity.getTime());
            int[] date = TimeUtil.getSplitDate(activity.getTime());
            year = date[0];
            month = date[1]-1;
            day = date[2];
        }else {
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            tv_income_time.setText(year+"年"+(month+1)+"月"+day+"日");
            tv_income_time.setTag(year + "-" + (month + 1) + "-" + day);
        }
        EditTextUtil.setPricePoint(et_income_amount);
        adapter = new IncomeTypeAdapter(addActivity);
        rl_income_type.setOnClickListener(this);
        rl_income_time.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_income_type:
                initTypeDialog();
                break;
            case R.id.rl_income_time:
                initTimeDialog();
                break;
        }
    }

    private void initTypeDialog() {
        typeDialog = new BaseDialog(addActivity);
        typeDialog.show();
        typeDialog.setBounds(0,0,(int)(addActivity.getWindowManager().getDefaultDisplay().getWidth()*0.8),0);
        typeDialog.init(BaseDialog.noMessage | BaseDialog.noButton);
        typeDialog.setTitle("选择类别");
        View view = LayoutInflater.from(addActivity).inflate(R.layout.dialog_income_type,null);
        GridView gv_income_type = (GridView) view.findViewById(R.id.gv_income_type);
        gv_income_type.setAdapter(adapter);
        typeDialog.setCustomView(view);
        gv_income_type.setOnItemClickListener(this);
    }

    private void initTimeDialog() {
        DatePickerDialog dialog = new DatePickerDialog(addActivity, AlertDialog.THEME_HOLO_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv_income_time.setText(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
                        tv_income_time.setTag(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        IncomeFragment.this.year = year;
                        month = monthOfYear;
                        day = dayOfMonth;
                    }
                },year,month,day);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String,Integer> map = (Map<String, Integer>) adapter.getItem(position);
        String name = null;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            name = entry.getKey();
        }
        tv_income_type.setText(name);
        tv_income_type.setTag(position);
        typeDialog.dismiss();
    }

}
