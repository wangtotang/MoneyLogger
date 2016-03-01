package com.tang.moneylogger.ui;

import android.os.Bundle;

import com.tang.moneylogger.R;
import com.tang.mybase.ui.BaseActivity;

/**
 * Created by Wangto Tang on 2015/6/29.
 */
public class AboutActivity extends BaseActivity {

    String title = "关于";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initLeftTitleWithButton(title,new OnBackupListener());
    }

}
