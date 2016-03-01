package com.tang.moneylogger.dao;

import com.tang.moneylogger.bean.Activities;

import java.util.List;

/**
 * Created by Wangto Tang on 2015/6/21.
 */
public interface ActivityDao {
    public long addActivity(Activities activity);
    public boolean deleteActivity(Activities activity);
    public boolean updateActivity(Activities activity);
    public List<Activities> queryForTime(String time);
    public List<Activities> queryAll();
    public List<Activities> queryForNameAndTime(String[] name,String[] time);
}
