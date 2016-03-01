package com.tang.mybase.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 * Created by Wangto Tang on 2015/6/21.
 */
public class TimeUtil {

    //日期格式
    static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 毫秒数转换成日期字符串
     * @param time 毫秒数
     * @return 日期字符串
     */
    public static String long2string(long time){
        return format.format(new Date(time));
    }

    /**
     * 日期字符串转换成毫秒数
     * @param time 日期字符串
     * @return 毫秒数
     */
    public static long string2long(String time){
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 计算天数
     * @param start 开始日期
     * @param end 结束日期
     * @return 天数
     */
    public static long countDays(String start,String end){
        try {
            Date dateStart = format.parse(start);
            Date dateEnd = format.parse(end);
            long days = (dateStart.getTime()-dateEnd.getTime())/(1000L*60*60*24);
            return days;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取一天的毫秒数
     * @return 毫秒数
     */
    public static long getOneDayForMilliSeconds(){
        return 1000L*60*60*24;
    }

    /**
     * 根据时间分别获取年月日
     * @param time 时间
     * @return 年月日的数组
     */
    public static int[] getSplitDate(String time){
        String[] from = time.split("-");
        int[] to = new int[3];
        if(from.length == 3){
            for(int i = 0; i < 3;i++){
                to[i] = Integer.parseInt(from[i]);
            }
        }
        return to;
    }

    /**
     * 根据时间获取中文年月日
     * @param time 时间
     * @return 年月日
     */
    public static String getNianYueRi(String time){
        String[] date = time.split("-");
        if(date.length == 3){
            return date[0] + "年" + date[1] + "月" + date[2]+"日";
        }else {
            return time;
        }
    }

    /**
     * 根据年月获得当月天数
     * @param time 年月
     * @return 年月日数组
     */
    public static String[] getDays(String time){
        String[] date = time.split("-");
        int n = 0;
        if(date.length == 2) {
            switch (Integer.parseInt(date[1])){
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    n = 31;
                break;
                case 4:
                case 6:
                case 9:
                case 11:
                    n = 30;
                break;
                case 2:
                    int year = Integer.parseInt(date[0]);
                    if(year%400==0||(year%100!=0&&year%4==0)){
                        n = 29;
                    }else{
                        n = 28;
                    }
                break;
            }
            String[] times = new String[n];
            for(int i = 1;i <= n;i++ ){
                times[i-1] = time+"-"+i;
            }
            return times;
        }
        return null;
    }

    /**
     * 根据年月获得当月天数
     * @param time 年月
     * @return 天数
     */
    public static int countDays(String time){
        String[] date = time.split("-");
        int n = 0;
        if(date.length == 2) {
            switch (Integer.parseInt(date[1])){
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    n = 31;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    n = 30;
                    break;
                case 2:
                    int year = Integer.parseInt(date[0]);
                    if(year%400==0||(year%100!=0&&year%4==0)){
                        n = 29;
                    }else{
                        n = 28;
                    }
                    break;
            }
        }
        return n;
    }

    /**
     * 根据开始、结束时间获得天数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 年月日数组
     */
    public static String[] getDays(String startTime,String endTime){
        long start = TimeUtil.string2long(startTime);
        long end = TimeUtil.string2long(endTime);
        long distance = TimeUtil.getOneDayForMilliSeconds();
        List<String> list = new ArrayList<>();
        for(long i = start;i <= end; i += distance){
            String time = TimeUtil.long2string(i);
            String[] date = time.split("-");
            if(date[1].startsWith("0")){
                date[1] = date[1].substring(1);
            }
            if(date[2].startsWith("0")){
                date[2] = date[2].substring(1);
            }
            time = date[0]+"-"+date[1]+"-"+date[2];
            list.add(time);
        }
        String[] times = new String[list.size()];
        list.toArray(times);
        return times;
    }
}
