package com.wr.common.customize.util;

import com.github.pagehelper.PageHelper;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.sql.SqlUtil;
import com.wr.common.core.web.page.PageDomain;
import com.wr.common.core.web.page.TableSupport;
import com.wr.common.customize.constant.TableConstants;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author: RainCity
 * @Date: 2021-10-26 17:27:03
 * @Desc: 动态表工具类
 */
public class TableUtils extends BaseUtils {

    /**
     * 根据表前缀获取表名
     * @param prefix 表前缀
     * @return
     */
    public static String getTableName(String prefix){
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("_yyyy_MM");
        return prefix + localDate.format(format);
    }

    /**
     * 根据表前缀获取表名
     * @param prefix 表前缀
     * @param communityId 小区id
     * @return java.lang.String
     */
    public static String getTableName(String prefix, Long communityId){
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("_yyyy_MM");
        return prefix + "_" + communityId + localDate.format(format);
    }

    /**
     * 月份不固定
     * 根据表前缀获取表名
     * @param prefix 表前缀
     * @param month 月份
     * @return
     */
    public static String getTableNameByMonth(String prefix, int month){
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        if (month<10){
            return prefix + "_" + year + "_0" + month;
        }
        return prefix + "_" + year + "_" + month;
    }

    /**
     * 月份不固定
     * 根据表前缀获取表名
     * @param prefix 表前缀
     * @param communityId 小区id
     * @param month 月份
     * @return
     */
    public static String getTableNameByMonth(String prefix, Long communityId, int month){
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        if (month<10){
            return prefix + "_" + communityId + "_" + year + "_0" + month;
        }
        return prefix + "_" + communityId + "_" + year + "_" + month;
    }

    /**
     * 根据数据库名、表前缀获取表名
     * @param prefix 表前缀
     * @return
     */
    public static String getDbTableName(String prefix){
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("_yyyy_MM");
        return TableConstants.LOG_DB_NAME + "." +prefix + localDate.format(format);
    }

    /**
     * 根据数据库名、表前缀获取表名
     * @param communityId 小区id
     * @param prefix 表前缀
     * @return
     */
    public static String getDbTableName(String prefix, Long communityId){
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("_yyyy_MM");
        return TableConstants.LOG_DB_NAME + "." + prefix + "_" + communityId + localDate.format(format);
    }

    /**
     * 根据表前缀、时间获取表名
     * @param prefix 表前缀
     * @param time yyyy-MM-dd
     * @return
     */
    public static String getTableName(String prefix, String time){
        String[] timeStr = time.split("-");
        String year = timeStr[0];
        String month = timeStr[1];
        return prefix + "_" + year + "_" + month;
    }

    /**
     * 根据表前缀、时间获取表名
     * @param prefix 表前缀
     * @param communityId 小区id
     * @param time yyyy-MM-dd
     * @return
     */
    public static String getTableName(String prefix, Long communityId, String time){
        String[] timeStr = time.split("-");
        String year = timeStr[0];
        String month = timeStr[1];
        return prefix + "_" + communityId + "_" + year + "_" + month;
    }

    /**
     * 根据表前缀、时间获取表名
     * @param prefix 表前缀
     * @param time yyyy-MM-dd
     * @return
     */
    public static String getTableName(String prefix, Date time){
        SimpleDateFormat sdf = new SimpleDateFormat("_yyyy_MM");
        return prefix + sdf.format(time);
    }

    /**
     * 根据表前缀、时间获取表名
     * @param prefix 表前缀
     * @param communityId 小区id
     * @param time yyyy-MM-dd
     * @return
     */
    public static String getTableName(String prefix, Long communityId, Date time){
        SimpleDateFormat sdf = new SimpleDateFormat("_yyyy_MM");
        return prefix + "_" + communityId + sdf.format(time);
    }

    /**
     * 根据表前缀、时间获取表名
     * @param prefix 表前缀
     * @param time yyyy-MM-dd
     * @return
     */
    public static String getDbTableName(String prefix, Date time){
        SimpleDateFormat sdf = new SimpleDateFormat("_yyyy_MM");
        return TableConstants.LOG_DB_NAME + "." +prefix + sdf.format(time);
    }

    /**
     * 根据表前缀、时间获取表名
     * @param prefix 表前缀
     * @param communityId 小区id
     * @param time yyyy-MM-dd
     * @return
     */
    public static String getDbTableName(String prefix, Long communityId, Date time){
        SimpleDateFormat sdf = new SimpleDateFormat("_yyyy_MM");
        return TableConstants.LOG_DB_NAME + "." + prefix + "_" + communityId + sdf.format(time);
    }

    /**
     * 根据数据库名、表前缀、时间获取表名
     * @param prefix 表前缀
     * @param time yyyy-MM-dd
     * @return
     */
    public static String getDbTableName(String prefix, String time){
        String[] timeStr = time.split("-");
        String year = timeStr[0];
        String month = timeStr[1];
        return TableConstants.LOG_DB_NAME + "." +prefix + "_" + year + "_" + month;
    }

    /**
     * 根据数据库名、表前缀、时间获取表名
     * @param prefix 表前缀
     * @param communityId 小区id
     * @param time yyyy-MM-dd
     * @return
     */
    public static String getDbTableName(String prefix, Long communityId, String time){
        String[] timeStr = time.split("-");
        String year = timeStr[0];
        String month = timeStr[1];
        return TableConstants.LOG_DB_NAME + "." + prefix + "_" + communityId + "_" + year + "_" + month;
    }

    public static void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

}
