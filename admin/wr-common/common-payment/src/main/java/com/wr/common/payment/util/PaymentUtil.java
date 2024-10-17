package com.wr.common.payment.util;

import com.wr.common.core.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: RainCity
 * @Date: 2021-11-17 11:08:31
 * @Desc:
 */
public class PaymentUtil {

    private PaymentUtil() {
    }

    /**
     * 设置过期时间
     * @param minute
     * @return
     */
    public static String aliTimeExpire(int minute){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE,minute);
        return DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,cal.getTime());
    }

    /**
     * 设置过期时间
     * @param minute
     * @return
     */
    public static String wechatTimeExpire(int minute){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE,minute);
        return DateUtils.parseDateToStr("yyyy-MM-dd'T'HH:mm:ss'+08:00'",cal.getTime());
    }

    /**
     * 获取支付宝POST过来反馈信息
     * @param request
     * @return
     */
    public static Map<String,String> getPostParams(HttpServletRequest request){
        Map<String,String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }
}
