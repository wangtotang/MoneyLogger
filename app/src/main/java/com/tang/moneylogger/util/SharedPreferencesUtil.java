package com.tang.moneylogger.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Wangto Tang on 2015/6/29.
 */
public class SharedPreferencesUtil {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final static String BUDGET_TIP = "budget_tip";

    public SharedPreferencesUtil(Context context) {
         sharedPreferences = context.getSharedPreferences("preference",Context.MODE_PRIVATE);
         editor = sharedPreferences.edit();
    }

    public void setBudgetTipEnabled(boolean enabled){
        editor.putBoolean(BUDGET_TIP,enabled);
        editor.commit();
    }

    public boolean isOpenBudgetTip(){
        return sharedPreferences.getBoolean(BUDGET_TIP,true);
    }
}
