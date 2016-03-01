package com.tang.moneylogger.config;

import android.app.Application;

import com.tang.moneylogger.util.SharedPreferencesUtil;
import com.tang.mybase.db.DBManager;

/**
 * Created by Wangto Tang on 2015/6/21.
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;
    private static DBManager dbManager;
    private static SharedPreferencesUtil sharedPreferencesUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
    }

    public void init(){
        dbManager = new DBManager(getApplicationContext(),Config.dbName,Config.createTbl,Config.dbVersion);
    }

    public static MyApplication getInstance(){
        return mInstance;
    }

    public static DBManager getDBManager(){
        return dbManager;
    }

    public synchronized SharedPreferencesUtil getSharedPreferencesUtil(){
        if(sharedPreferencesUtil == null){
            sharedPreferencesUtil = new SharedPreferencesUtil(this);
        }
        return sharedPreferencesUtil;
    }
}
