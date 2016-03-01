package com.tang.mybase.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用页面管理工具类
 * Created by Wangto Tang on 2015/6/21.
 */
public class AppUtil {

    //页面List
    private static List<Activity> activities = new ArrayList<>();

    /**
     * 添加页面
     * @param activity 页面
     */
    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    /**
     * 退出程序，销毁所有页面
     */
    public static void exitApp(){
        for (Activity activity : activities) {
            activity.finish();
        }
        System.exit(0);
    }
}
