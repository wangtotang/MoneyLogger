package com.tang.moneylogger.ui;

import android.os.Bundle;

import com.tang.moneylogger.R;
import com.tang.mybase.ui.BaseActivity;

/**
 * Created by Wangto Tang on 2015/6/30.
 */
public class InfoActivity extends BaseActivity {

    String title = "趋势";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initViews();
        init();
    }

    private void initViews() {

    }

    private void init() {
        initLeftTitleWithButton(title,new OnBackupListener());
    }
}
