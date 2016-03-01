package com.tang.moneylogger.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;

import com.tang.moneylogger.bean.Activities;
import com.tang.moneylogger.dao.ActivityDao;
import com.tang.mybase.db.DBManager;

import java.util.List;

/**
 * Created by Wangto Tang on 2015/6/21.
 */
public class ActivityDaoImpl implements ActivityDao{

    private DBManager dbManager;
    private String tableName = "activity";
    private String _id = "_id";
    private String type_id = "type_id";
    private String name = "name";
    private String icon = "icon";
    private String description = "description";
    private String time = "time";
    private String amount = "amount";

    public ActivityDaoImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public long addActivity(Activities activity) {
        ContentValues values = new ContentValues();
        values.put(type_id,activity.getType_id());
        values.put(name,activity.getName());
        values.put(icon,activity.getIcon());
        values.put(description,activity.getDescription());
        values.put(time,activity.getTime());
        values.put(amount, activity.getAmount());
        long rowId = dbManager.insert(tableName,values);
        activity.setId((int)rowId);
        return rowId;
    }

    @Override
    public boolean deleteActivity(Activities activity) {
        return dbManager.deleteById(tableName, "_id", activity.getId() + "");
    }

    @Override
    public boolean updateActivity(Activities activity) {
        ContentValues values = new ContentValues();
        values.put(type_id,activity.getType_id());
        values.put(name,activity.getName());
        values.put(icon,activity.getIcon());
        values.put(description,activity.getDescription());
        values.put(time,activity.getTime());
        values.put(amount, activity.getAmount());
        return dbManager.updateById(tableName, values, "_id", activity.getId() + "");
    }

    @Override
    public List<Activities> queryAll() {
        return dbManager.<Activities>queryForList(tableName, null, null, time + " desc," + _id + " desc",
                new DBManager.RowGetter<Activities>() {
                    @Override
                    public Activities getRow(Cursor cursor) {
                        Activities activity = new Activities();
                        activity.setId(cursor.getInt(cursor.getColumnIndex(_id)));
                        activity.setType_id(cursor.getInt(cursor.getColumnIndex(type_id)));
                        activity.setName(cursor.getString(cursor.getColumnIndex(name)));
                        activity.setIcon(cursor.getInt(cursor.getColumnIndex(icon)));
                        activity.setDescription(cursor.getString(cursor.getColumnIndex(description)));
                        activity.setTime(cursor.getString(cursor.getColumnIndex(time)));
                        activity.setAmount(cursor.getDouble(cursor.getColumnIndex(amount)));
                        return activity;
                    }
                });
    }

    @Override
    public List<Activities> queryForTime(String selectedTime) {
        return dbManager.<Activities>queryForList(tableName, time + "=? and "+type_id+"=?", new String[]{selectedTime,0+""},amount+" desc",
                new DBManager.RowGetter<Activities>() {
                    @Override
                    public Activities getRow(Cursor cursor) {
                        Activities activity = new Activities();
                        activity.setId(cursor.getInt(cursor.getColumnIndex(_id)));
                        activity.setType_id(cursor.getInt(cursor.getColumnIndex(type_id)));
                        activity.setName(cursor.getString(cursor.getColumnIndex(name)));
                        activity.setIcon(cursor.getInt(cursor.getColumnIndex(icon)));
                        activity.setDescription(cursor.getString(cursor.getColumnIndex(description)));
                        activity.setTime(cursor.getString(cursor.getColumnIndex(time)));
                        activity.setAmount(cursor.getDouble(cursor.getColumnIndex(amount)));
                        return activity;
                    }
                });
    }

    @Override
    public List<Activities> queryForNameAndTime(String[] mName,String[] mTime){
        StringBuilder names = new StringBuilder();
        names.append("(");
        for(String s:mName){
            names.append("'").append(s).append("'").append(",");
        }
        names.replace(names.length()-1,names.length(),")");
        StringBuilder times = new StringBuilder();
        times.append("(");
        for(String s:mTime){
            times.append("'").append(s).append("'").append(",");
        }
        times.replace(times.length()-1,times.length(),")");
        String sql = "select * from "+tableName+" where "+name+" in "+names.toString()+" and "+time+" in "+times.toString();
        return dbManager.<Activities>rawQueryForList(sql, null, new DBManager.RowGetter<Activities>() {
            @Override
            public Activities getRow(Cursor cursor) {
                Activities activity = new Activities();
                activity.setId(cursor.getInt(cursor.getColumnIndex(_id)));
                activity.setType_id(cursor.getInt(cursor.getColumnIndex(type_id)));
                activity.setName(cursor.getString(cursor.getColumnIndex(name)));
                activity.setIcon(cursor.getInt(cursor.getColumnIndex(icon)));
                activity.setDescription(cursor.getString(cursor.getColumnIndex(description)));
                activity.setTime(cursor.getString(cursor.getColumnIndex(time)));
                activity.setAmount(cursor.getDouble(cursor.getColumnIndex(amount)));
                return activity;
            }
        });
    }

}
