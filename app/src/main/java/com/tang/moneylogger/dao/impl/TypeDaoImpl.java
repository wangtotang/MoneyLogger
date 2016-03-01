package com.tang.moneylogger.dao.impl;

import android.database.Cursor;

import com.tang.moneylogger.bean.Type;
import com.tang.moneylogger.dao.TypeDao;
import com.tang.mybase.db.DBManager;

import java.util.List;

/**
 * Created by Wangto Tang on 2015/6/22.
 */
public class TypeDaoImpl implements TypeDao {

    DBManager dbManager;
    String tableName = "type";
    String _id = "_id";
    String name = "name";

    public TypeDaoImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public Type queryById(int id) {
        return dbManager.<Type>queryForObject(tableName, _id + "=?", new String[]{id + ""}, null,
                new DBManager.RowGetter<Type>() {
                    @Override
                    public Type getRow(Cursor cursor) {
                        Type type = new Type();
                        type.setId(cursor.getInt(cursor.getColumnIndex(_id)));
                        type.setName(cursor.getString(cursor.getColumnIndex(name)));
                        return type;
                    }
                });
    }

    @Override
    public Type queryByName(String mName) {
        return dbManager.<Type>queryForObject(tableName, name + "=?", new String[]{mName}, null,
                new DBManager.RowGetter<Type>() {
                    @Override
                    public Type getRow(Cursor cursor) {
                        Type type = new Type();
                        type.setId(cursor.getInt(cursor.getColumnIndex(_id)));
                        type.setName(cursor.getString(cursor.getColumnIndex(name)));
                        return type;
                    }
                });
    }

    @Override
    public List<Type> queryAll() {
        return dbManager.<Type>queryForList(tableName, null, null, null,
                new DBManager.RowGetter<Type>() {
                    @Override
                    public Type getRow(Cursor cursor) {
                        Type type = new Type();
                        type.setId(cursor.getInt(cursor.getColumnIndex(_id)));
                        type.setName(cursor.getString(cursor.getColumnIndex(name)));
                        return type;
                    }
                });
    }

}
