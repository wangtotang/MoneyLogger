package com.tang.mybase.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite数据库管理类
 * Created by Wangto Tang on 2015/6/19.
 */
public class DBManager {

    private static String dbName;
    private static String[] createSQL;
    private static Context context;
    private SQLiteOpenHelper helper;
    private int version;

    public DBManager(Context context,String name,String[] createSQL,int version) {
        this.context = context;
        this.dbName = name;
        this.createSQL = createSQL;
        this.version = version;
        helper = new BaseHelper(context,dbName,null,version);
    }

    /**
     * SQLite数据库的创建和升级
     */
    public static class BaseHelper extends SQLiteOpenHelper{

        public BaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            for (String s : createSQL) {
                db.execSQL(s);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("DBManager",dbName+"upgrade from "+oldVersion+"to"+newVersion);
        }
    }

    public SQLiteOpenHelper openHelper(){
        return helper;
    }

    public long insert(String tableName,ContentValues values){
        SQLiteDatabase db = null;
        long rowId = -1;
        try {
           db = helper.getWritableDatabase();
           rowId = db.insert(tableName,null,values);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeDataBase(db,null);
        }
        return rowId;
    }

    public boolean delete(String tableName,String whereClause,String[] whereArgs){
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            int row = db.delete(tableName,whereClause,whereArgs);
            if(row > 0){
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeDataBase(db,null);
        }
        return false;
    }

    public boolean deleteById(String tableName,String idName,String id){
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            int row = db.delete(tableName, idName + "=?", new String[]{id});
            if(row > 0){
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeDataBase(db,null);
        }
        return false;
    }

    public boolean update(String tableName,ContentValues valus,String whereClause,String[] whereArgs){
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            int row = db.update(tableName, valus, whereClause, whereArgs);
            if(row > 0){
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeDataBase(db,null);
        }
        return false;
    }

    public boolean updateById(String tableName,ContentValues valus,String idName,String id){
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            int row = db.update(tableName, valus, idName + "=?", new String[]{id});
            if(row > 0){
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeDataBase(db,null);
        }
        return false;
    }

    public void execSQL(String sql) {
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDataBase(db,null);
        }
    }

    public boolean isExistsBySQL(String prepareStatement,String[] selectionArgs){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try{
            db = helper.getWritableDatabase();
            cursor = db.rawQuery(prepareStatement, selectionArgs);
            if (cursor.getCount() > 0){
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDataBase(db,cursor);
        }
        return false;
    }

    public boolean isExistedById(String tableName,String IdName,String id){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try{
            db = helper.getWritableDatabase();
            StringBuilder sql = new StringBuilder();
            sql.append("select count(*) from ").append(tableName).append(" where ").append(IdName).append(" =?");
            cursor = db.rawQuery(sql.toString(), new String[]{id});
            if (cursor.getCount() > 0){
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDataBase(db,cursor);
        }
        return false;
    }

    public boolean isExistedByField(String tableName,String field,String value){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try{
            db = helper.getWritableDatabase();
            StringBuilder sql = new StringBuilder();
            sql.append("select count(*) from ").append(tableName).append(" where ").append(field).append(" =?");
            cursor = db.rawQuery(sql.toString(), new String[]{value});
            if (cursor.getCount() > 0){
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDataBase(db,cursor);
        }
        return false;
    }

    /**
     * 查询一行数据的回调接口
     * @param <T> 组装的Bean
     */
    public interface RowGetter<T>{

        public T getRow(Cursor cursor);

    }

    public <T> T rawQueryForObject(String sql,String[] selectionArgs,RowGetter<T> rowGetter){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try{
            db = helper.getWritableDatabase();
            cursor = db.rawQuery(sql, selectionArgs);
            if(cursor.getCount() > 0){
                if(cursor.moveToFirst()) {
                    return rowGetter.getRow(cursor);
                }
            }
        }finally {
            closeDataBase(db,cursor);
        }
        return null;
    }

    public <T> T queryForObject(String tableName,String selection,String[] selectionArgs,String orderBy,RowGetter<T> rowGetter){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try{
            db = helper.getWritableDatabase();
            cursor = db.query(tableName, null, selection, selectionArgs, null, null, orderBy);
            if(cursor.getCount() > 0){
                if(cursor.moveToFirst()) {
                    return rowGetter.getRow(cursor);
                }
            }
        }finally {
            closeDataBase(db,cursor);
        }
        return null;
    }

    public <T> List<T> rawQueryForList(String sql,String[] selectionArgs,RowGetter<T> rowGetter){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<T> list = new ArrayList<>();
        try{
            db = helper.getWritableDatabase();
            cursor = db.rawQuery(sql, selectionArgs);
            if(cursor.getCount() > 0){
                while(cursor.moveToNext()){
                    list.add(rowGetter.getRow(cursor));
                }
            }
        }finally {
            closeDataBase(db,cursor);
        }
        return list;
    }

    public <T> List<T> queryForList(String tableName,String selection,String[] selectionArgs,String orderBy,RowGetter<T> rowGetter){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<T> list = new ArrayList<>();
        try{
            db = helper.getWritableDatabase();
            cursor = db.query(tableName, null, selection, selectionArgs, null, null, orderBy);
            if(cursor.getCount() > 0){
                while(cursor.moveToNext()){
                    list.add(rowGetter.getRow(cursor));
                }
            }
        }finally {
            closeDataBase(db,cursor);
        }
        return list;
    }

    public void closeDataBase(SQLiteDatabase db,Cursor cursor){
        if(db != null){
            db.close();
        }
        if (cursor != null){
            cursor.close();
        }
    }

}
