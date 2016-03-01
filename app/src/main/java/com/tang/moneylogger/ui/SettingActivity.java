package com.tang.moneylogger.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tang.moneylogger.R;
import com.tang.moneylogger.config.MyApplication;
import com.tang.moneylogger.util.SharedPreferencesUtil;
import com.tang.mybase.ui.BaseActivity;

/**
 * Created by Wangto Tang on 2015/6/29.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    String title = "设置";
    RelativeLayout rl_setting_budget_tip;
    ImageView iv_open_budget_tip,iv_close_budget_tip;
    SharedPreferencesUtil sharedPreferencesUtil;
    boolean budgetTipFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
        init();
    }

    private void initViews() {
        rl_setting_budget_tip = (RelativeLayout) findViewById(R.id.rl_setting_budget_tip);
        iv_open_budget_tip = (ImageView) findViewById(R.id.iv_open_budget_tip);
        iv_close_budget_tip = (ImageView) findViewById(R.id.iv_close_budget_tip);
    }

    private void init() {
        initLeftTitleWithButton(title, new OnBackupListener());
        rl_setting_budget_tip.setOnClickListener(this);
        sharedPreferencesUtil = MyApplication.getInstance().getSharedPreferencesUtil();
        budgetTipFlag = sharedPreferencesUtil.isOpenBudgetTip();
        if(budgetTipFlag){
            iv_open_budget_tip.setVisibility(View.VISIBLE);
            iv_close_budget_tip.setVisibility(View.INVISIBLE);
        }else{
            iv_open_budget_tip.setVisibility(View.INVISIBLE);
            iv_close_budget_tip.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if(budgetTipFlag){
            budgetTipFlag = false;
            iv_open_budget_tip.setVisibility(View.INVISIBLE);
            iv_close_budget_tip.setVisibility(View.VISIBLE);
        }else{
            budgetTipFlag = true;
            iv_open_budget_tip.setVisibility(View.VISIBLE);
            iv_close_budget_tip.setVisibility(View.INVISIBLE);
        }
        sharedPreferencesUtil.setBudgetTipEnabled(budgetTipFlag);
    }
}
