package com.wr.common.customize.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

/**
 * @Author: RainCity
 * @Date: 2021-11-03 17:35:34
 * @Desc: json 工具类
 */
public class JsonUtil extends BaseUtils {
    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    private JsonUtil() {
    }

    /**
     * 校验json
     * @param text 待校验文本
     * @return
     */
    public static Boolean verifyJson(String text){
        try {
            JSON.parseObject(text);
            return true;
        }catch (Exception e){
            log.error("不正确的json字符串");
            return false;
        }
    }

    /**
     * 转json
     * @param text 待转换文本
     * @return JSONObject
     */
    public static JSONObject parseObject(String text){
        try {
            return JSON.parseObject(text);
        }catch (Exception e){
            log.error("不正确的json字符串==>{}",text);
            return null;
        }
    }

    /**
     * 初始化一个 JSONObject
     * @return JSONObject
     */
    public static JSONObject newJSONObject(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CODE_TAG,200);
        jsonObject.put(MSG_TAG,"请求成功");
        return jsonObject;
    }

    /**
     * 请求成功
     * @param msg 提示信息
     * @return JSONObject
     */
    public static JSONObject success(String msg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CODE_TAG,200);
        jsonObject.put(MSG_TAG,msg);
        return jsonObject;
    }

    /**
     * 请求成功
     * @param data 返回数据
     * @return JSONObject
     */
    public static JSONObject success(Object data){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CODE_TAG,200);
        jsonObject.put(MSG_TAG,"请求成功");
        jsonObject.put(DATA_TAG,data);
        return jsonObject;
    }

    /**
     * 请求失败
     * @param msg 提示信息
     * @return JSONObject
     */
    public static JSONObject fail(String msg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CODE_TAG,500);
        jsonObject.put(MSG_TAG,msg);
        return jsonObject;
    }
}
