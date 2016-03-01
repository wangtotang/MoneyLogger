package com.tang.moneylogger.ui.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

/**
 * Created by Wangto Tang on 2015/6/26.
 */
public class MyDatePickerDialog extends DatePickerDialog {

    public MyDatePickerDialog(Context context, int theme, OnDateSetListener listener, int year, int monthOfYear) {
        super(context, theme, listener, year, monthOfYear,1);
        setTitle(year + "年" + (monthOfYear + 1) + "月");
        ((ViewGroup)((ViewGroup)getDatePicker().getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        super.onDateChanged(view, year, month, day);
        setTitle(year+"年"+(month+1)+"月");
    }



}
