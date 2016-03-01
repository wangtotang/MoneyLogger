package com.tang.moneylogger.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;

import com.tang.moneylogger.bean.Total;
import com.tang.moneylogger.dao.TotalDao;
import com.tang.mybase.db.DBManager;
import com.tang.mybase.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wangto Tang on 2015/6/21.
 */
public class TotalDaoImpl implements TotalDao {

    private DBManager dbManager;
    private String tableName = "total";
    private String _id = "_id";
    private String type_id = "type_id";
    private String time = "time";
    private String amount = "amount";

    public TotalDaoImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public boolean isExisted(int mType_id,String mTime) {
        return dbManager.isExistsBySQL("select * from total where "+type_id+"=? and "+time+"=? ",new String[]{mType_id+"",mTime});
    }

    @Override
    public long addTotal(Total total) {
        ContentValues values = new ContentValues();
        values.put(type_id,total.getType_id());
        values.put(time,total.getTime());
        values.put(amount, total.getAmount());
        long rowId = dbManager.insert(tableName,values);
        total.setId((int)rowId);
        return rowId;
    }

    @Override
    public boolean updateTotal(Total total) {
        ContentValues values = new ContentValues();
        values.put(type_id,total.getType_id());
        values.put(time,total.getTime());
        values.put(amount, total.getAmount());
        return dbManager.updateById(tableName, values, "_id", total.getId() + "");
    }

    @Override
    public Total queryForTimeAndTypeId(String selectTime, int mType_id) {
        return dbManager.<Total>queryForObject(tableName, time + "=? and " + type_id + "=?", new String[]{selectTime, mType_id + ""}, null,
                new DBManager.RowGetter<Total>() {
                    @Override
                    public Total getRow(Cursor cursor) {
                        Total total = new Total();
                        total.setId(cursor.getInt(cursor.getColumnIndex(_id)));
                        total.setType_id(cursor.getInt(cursor.getColumnIndex(type_id)));
                        total.setTime(cursor.getString(cursor.getColumnIndex(time)));
                        total.setAmount(cursor.getDouble(cursor.getColumnIndex(amount)));
                        return total;
                    }
                });
    }

    @Override
    public List<Total> queryAllForTimeAndTypeId(String startTime,String endTime, int mType_id) {
        long start = TimeUtil.string2long(startTime);
        long end = TimeUtil.string2long(endTime);
        long distance = TimeUtil.getOneDayForMilliSeconds();
        List<Total> list = new ArrayList<>();
        Total total = null;
        for(long i = start;i <= end; i += distance){
            total = dbManager.<Total>queryForObject(tableName, time + "=? and " + type_id + "=?", new String[]{TimeUtil.long2string(i), mType_id + ""}, null,
                    new DBManager.RowGetter<Total>() {
                @Override
                public Total getRow(Cursor cursor) {
                    Total t = new Total();
                    t.setId(cursor.getInt(cursor.getColumnIndex(_id)));
                    t.setType_id(cursor.getInt(cursor.getColumnIndex(type_id)));
                    t.setTime(cursor.getString(cursor.getColumnIndex(time)));
                    t.setAmount(cursor.getDouble(cursor.getColumnIndex(amount)));
                    return t;
                }
            });
            list.add(total);
        }
        return list;
    }

    @Override
    public List<Total> queryAllForTime(String[] mTime){
        StringBuilder times = new StringBuilder();
        times.append("(");
        for(String s:mTime){
            times.append("'").append(s).append("'").append(",");
        }
        times.replace(times.length()-1,times.length(),")");
        String sql = "select * from "+tableName+" where "+time+" in "+times.toString();
        return dbManager.<Total>rawQueryForList(sql, null, new DBManager.RowGetter<Total>() {

            @Override
            public Total getRow(Cursor cursor) {
                Total t = new Total();
                t.setId(cursor.getInt(cursor.getColumnIndex(_id)));
                t.setType_id(cursor.getInt(cursor.getColumnIndex(type_id)));
                t.setTime(cursor.getString(cursor.getColumnIndex(time)));
                t.setAmount(cursor.getDouble(cursor.getColumnIndex(amount)));
                return t;
            }

        });
    }
}
