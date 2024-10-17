package com.wr.estate.web.work.inspect;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工单处理提取方法
 * @Author: lvzy
 * @Date: 2022-11-04 09:33:36
 * @Desc: 工单处理提取方法
 */
public class ExtractCode {

    /**
     * 生成工单编号   前缀_年月日_6位随机数
     * @param str 前缀 如: xj
     * @return 如: xj-20221031-310324
     */
    public String sixInspectNo(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = dateFormat.format(new Date());
        int ranDom = (int) ((Math.random() * 9 + 1) * 100000);
        return str + "-" + format + "-" + ranDom;
    }

    /**
     * 开始结束时间处理
     * @param str 10:00
     * @return 年月日时分秒
     */
    public String dateHandle(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        String format = dateFormat.format(new Date());
        return format + str + ":00";
    }
}
