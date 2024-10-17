package com.wr.common.customize.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: RainCity
 * @Date: 2022-02-08 10:29:13
 * @Desc:
 */
public class DateSectionUtil {

    /**
     * 获取前n天的日期字符串
     * @param section 前n天(负数形式)
     * @return
     */
    public static String dateStr(int section){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,section);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    /**
     * ISO8601格式转化
     * @param iso8601
     * @return
     */
    public static String iso8601ToDateStr(String iso8601){
        if(StringUtils.isEmpty(iso8601)){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Date isoDate = df.parse(iso8601);
            return sdf.format(isoDate);
        } catch (ParseException e) {
            iso8601 = iso8601.replace(" ","");
            int index = iso8601.indexOf(".");
            if(index == -1){
                return iso8601;
            }
            return iso8601.replace("T"," ").substring(0,index);
        }
    }

    /**
     * ISO8601格式转化
     * @param iso8601
     * @param date
     * @return
     */
    public static String iso8601ToDateStr(String iso8601, Date date){
        if(StringUtils.isEmpty(iso8601)){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Date isoDate = df.parse(iso8601);
            return sdf.format(isoDate);
        } catch (ParseException e) {
            iso8601 = iso8601.replace(" ","");
            int index = iso8601.indexOf(".");
            if(index == -1){
                return sdf.format(date);
            }
            return iso8601.replace("T"," ").substring(0,index);
        }
    }
}
