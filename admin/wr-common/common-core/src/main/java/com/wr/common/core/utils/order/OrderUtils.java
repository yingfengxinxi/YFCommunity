package com.wr.common.core.utils.order;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: 晁盖
 * @Date: 2022-11-03 14:04:21
 * @Desc: 工单工具类
 */
public class OrderUtils {

    /**
     * 生成工单编号
     *
     * @param type
     * @return
     */
    public static String getOrderNo(String type) {

        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int number = (int) ((Math.random() * 9 + 1) * 100000);

        return type + "-" + date + "-" + number;
    }
}
