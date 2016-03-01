package com.tang.moneylogger.config;

import com.github.mikephil.charting.utils.ColorTemplate;
import com.tang.moneylogger.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wangto Tang on 2015/6/21.
 */
public class Config {

    public static String dbName = "moneylogger.db";

    public static String[] createTbl = new String[]{
            "create table activity(" +
                    "_id integer primary key autoincrement," +
                    "type_id integer," +
                    "name varchar(20)," +
                    "icon integer," +
                    "description varchar(20)," +
                    "time varchar(20)," +
                    "amount double" +
                    ");",
            "create table total(" +
                    "_id integer primary key autoincrement," +
                    "type_id integer," +
                    "time varchar(20)," +
                    "amount double" +
                    ");",
            "create table type(" +
                    "_id integer primary key autoincrement," +
                    "name varchar(20)" +
                    ");"
    };

    public static int dbVersion = 1;

    public static Integer[] expenseIcon = {
            R.mipmap.icon_film, R.mipmap.icon_meat, R.mipmap.icon_tv, R.mipmap.icon_phone,
            R.mipmap.icon_shop, R.mipmap.icon_online, R.mipmap.icon_car, R.mipmap.icon_book,
            R.mipmap.icon_clothe, R.mipmap.icon_medical, R.mipmap.icon_travel, R.mipmap.icon_taobao,
            R.mipmap.icon_game, R.mipmap.icon_snake
    };

    public static String[] expenseType = {
            "电影", "餐饮", "电器", "通信",
            "购物", "上网", "交通", "书籍",
            "服装", "医疗", "旅游", "淘宝",
            "娱乐", "零食"
    };

    public static Integer[] incomeIcon = {
            R.mipmap.icon_stock, R.mipmap.icon_lottery, R.mipmap.icon_wage, R.mipmap.icon_extra,
            R.mipmap.icon_hongbao, R.mipmap.icon_gift, R.mipmap.icon_interest, R.mipmap.icon_invest,
            R.mipmap.icon_profit
    };

    public static String[] incomeType = {
            "股票", "彩票", "工资", "外快",
            "红包", "中奖", "利息", "投资",
            "利润"
    };

    public static List<Map<String, Integer>> getExpenseTypeList() {
        List<Map<String, Integer>> typeList = new ArrayList<>();
        for (int i = 0; i < expenseType.length; i++) {
            Map<String, Integer> map = new HashMap<>();
            map.put(expenseType[i], expenseIcon[i]);
            typeList.add(map);
        }
        return typeList;
    }

    public static List<Map<String, Integer>> getIncomeTypeList() {
        List<Map<String, Integer>> typeList = new ArrayList<>();
        for (int i = 0; i < incomeType.length; i++) {
            Map<String, Integer> map = new HashMap<>();
            map.put(incomeType[i], incomeIcon[i]);
            typeList.add(map);
        }
        return typeList;
    }

    public static List<Integer> getColors(){
        List<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        return colors;
    }

    public static Integer[] reportIcon = {
           R.mipmap.icon_expense, R.mipmap.icon_income,R.mipmap.icon_budget
    };

    public static String[] reportType = {
             "支出", "收入","预算"
    };

    public static List<Map<String, Integer>> getReportTypeList() {
        List<Map<String, Integer>> typeList = new ArrayList<>();
        for (int i = 0; i < reportType.length; i++) {
            Map<String, Integer> map = new HashMap<>();
            map.put(reportType[i], reportIcon[i]);
            typeList.add(map);
        }
        return typeList;
    }
}
