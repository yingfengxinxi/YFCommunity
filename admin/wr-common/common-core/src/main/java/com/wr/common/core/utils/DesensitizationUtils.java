package com.wr.common.core.utils;

/**
 * @Author lvzy【脱敏】
 * @Date 2024/6/3 20:04
 */
public class DesensitizationUtils {


    /**
     * 手机号脱敏
     *
     * @param phoneNumber
     * @return
     */
    public static String phone(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() != 11) {
            return phoneNumber;
        }
        return phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7);
    }


    /**
     * 姓名脱敏
     *
     * @param name
     * @return
     */
    public static String name(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        int length = name.length();
        if (length <= 2) {
            if (length == 2) {

                return name.substring(0, 1) + "*"; // 星号数量等于姓名长度
            }
            return name;
        } else {
            return name.substring(0, 1) + "*" + name.substring(length - 1);
            // 保留第一个字母和最后一个字母，中间用星号覆盖
        }
    }

    /**
     * 身份证脱敏
     *
     * @param cardNo
     * @return
     */
    public static String cardNo(String cardNo) {
        if (StringUtils.isEmpty(cardNo)) {
            return "";
        }
        return cardNo.replaceAll("^(.{6})(.*)(.{4})$", "$1****$3");
    }
}
