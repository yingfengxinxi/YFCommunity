package com.wr.remote.util;

import com.wr.common.core.utils.DateUtils;

import java.util.Random;
import java.util.StringJoiner;

/**
 * 工单工具类
 *
 * @Author: 奔波儿灞
 * @Date: 2022-11-03 09:38:55
 * @Desc:
 */
public class WorkOrderUtil {

    /**
     * 获取工单编号
     *
     * @param prefix
     * @return java.lang.String
     * @author yx
     * @date 2022/11/3 9:49
     */
    public static String getOrderNo(String prefix) {
        String date = DateUtils.dateTime();
        StringJoiner s = new StringJoiner("-");
        Random random = new Random();
        int i = random.nextInt(1000000);
        return new StringJoiner("-").add(prefix).add(date).add(String.valueOf(i)).toString();
    }

}
