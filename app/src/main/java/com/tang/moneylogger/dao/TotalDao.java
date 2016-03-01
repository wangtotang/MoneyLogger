package com.tang.moneylogger.dao;

import com.tang.moneylogger.bean.Total;

import java.util.List;

/**
 * Created by Wangto Tang on 2015/6/21.
 */
public interface TotalDao {
    public boolean isExisted(int type_id,String time);
    public long addTotal(Total total);
    public boolean updateTotal(Total total);
    public Total queryForTimeAndTypeId(String time,int type_id);
    public List<Total> queryAllForTimeAndTypeId(String startTime,String endTime, int type_id);
    public List<Total> queryAllForTime(String[] times);
}
