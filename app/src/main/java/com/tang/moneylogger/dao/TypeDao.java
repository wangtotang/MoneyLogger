package com.tang.moneylogger.dao;

import com.tang.moneylogger.bean.Type;

import java.util.List;

/**
 * Created by Wangto Tang on 2015/6/22.
 */
public interface TypeDao {
    public Type queryById(int id);
    public Type queryByName(String name);
    public List<Type> queryAll();
}
