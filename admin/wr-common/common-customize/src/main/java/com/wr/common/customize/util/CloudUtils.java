package com.wr.common.customize.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wr.common.customize.constant.CloudConstants;
import com.wr.common.customize.constant.HealthyConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Author: RainCity
 * @Date: 2021-09-23 17:58:21
 * @Desc: 社保 云 工具类
 */
public class CloudUtils {
    private static final Logger log = LoggerFactory.getLogger(CloudUtils.class);

    /**
     * 字符串转16进制
     * @param param
     * @return
     */
    public static String strToHex(String param) {
        log.info("原数据：{}",param);
        byte[] hex = CloudConstants.HEX_STR.getBytes();
        byte[] decoded = Base64.getDecoder().decode(param);
        byte[] buff = new byte[2 * decoded.length];
        for (int i = 0; i < decoded.length; i++) {
            buff[2 * i] = hex[(decoded[i] >> 4) & 0x0f];
            buff[2 * i + 1] = hex[decoded[i] & 0x0f];
        }
        return new String(buff);
    }

    /**
     * 数据格式化，两两一位
     * @param content
     * @return
     */
    public static String change(String content) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            if (i % 2 == 0) {
                str.append(" ").append(content.charAt(i));
            } else {
                str.append(content.charAt(i));
            }
        }
        return str.toString().trim();
    }

    /**
     * 社保卡--数据校验
     * @param data
     * @return
     */
    public static boolean dataCheck(String data){
        if(StringUtils.isEmpty(data)){
            return false;
        }
        String checkSum = data.substring(data.length()-4,data.length()-2);
        if(StringUtils.isEmpty(checkSum)){
            return false;
        }
        checkSum = checkSum.toUpperCase();
        log.info("校验位：{}",checkSum);
        String end = "03";
        data = change(data.substring(0,data.length()-4));
        String[] b = data.split(" ");
        String result = "";
        int a = 0;
        for (int i = 0; i < b.length; i++) {
            a = a ^ Integer.parseInt(b[i], 16);
        }
        if(a<10){
            StringBuffer sb = new StringBuffer();
            sb.append("0");
            sb.append(a);
            result = sb.toString().toUpperCase();
        }else {
            result = Integer.toHexString(a).toUpperCase();
        }
        log.info("计算结果：{}",result);
        return checkSum.equals(result);
    }

    /**
     * 解析身份证号
     * @param hex
     * @return
     */
    public static String decodeIdCardNo(String hex){
        int idIndex = hex.indexOf(CloudConstants.ID_CARD);
        if(idIndex == -1 ){
            return null;
        }

        int nameIndex = hex.indexOf(CloudConstants.USER_NAME);
        if(nameIndex == -1 ){
            return null;
        }

        String idHex = hex.substring(idIndex+4,nameIndex);
        log.info("身份证号原数据：{}",idHex);
        return hexToString(idHex);
    }

    /**
     * 16进制转字符串
     * @param hex
     * @return
     */
    public static String hexToString(String hex){
        hex = hex.replace(" ", "");
        byte[] baKeyword = new byte[hex.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new String(baKeyword, StandardCharsets.UTF_8);
    }

    /**
     * 解析姓名
     * @param hex
     * @return
     */
    public static String decodeUserName(String hex){
        int nameIndex = hex.indexOf(CloudConstants.USER_NAME);
        if(nameIndex == -1 ){
            return null;
        }

        if(nameIndex+4 >= hex.length()-4){
            return null;
        }
        String preStr = hex.substring(nameIndex+4,hex.length()-4);
        if(StringUtils.isEmpty(preStr)){
            return null;
        }
        String nameStr = preStr.replace("00","");
        log.info("姓名原数据：{}",nameStr);
        return hexToGbk(nameStr);
    }

    /**
     * 16进制转GBK编码字符串
     * @param hex
     * @return
     */
    public static String hexToGbk(String hex){
        try {
            hex = hex.replace(" ", "");
            byte[] baKeyword = new byte[hex.length() / 2];
            for (int i = 0; i < baKeyword.length; i++) {
                try {
                    baKeyword[i] = (byte) (0xff & Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return new String(baKeyword, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 电子社保卡身份查询
     * @param barCode 电子社保卡条码
     * @return
     */
    public static JSONObject elcCardInfo(String barCode, String checkUsrIdCard){
        try {
            String params= "{" +
                    "\"cityNo\": \""+ HealthyConstants.CITY_CODE+"\"," +
                    "\"qrCodeUrl\": \""+barCode+"\"," +
                    "\"targetType\": \"3400001\"," +
                    "\"senceCode\": \"\"," +
                    "\"siteId\": \""+HealthyConstants.SITE_ID+"\"," +
                    "\"checkUsrIdCard\": \""+checkUsrIdCard+"\"," +
                    "\"checkUsrName\": \""+HealthyConstants.CHECK_USR_NAME+"\"," +
                    "\"dataSource\": \"\"" +
                    "}";
            String result = HttpClientUtil.post(CloudConstants.ELC_CARD_URL,params, "application/json");
            log.info("电子社保卡信息查询结===>{}",result);
            return JSON.parseObject(result);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("出错啦==>{}",e.getMessage());
            return null;
        }
    }

}
