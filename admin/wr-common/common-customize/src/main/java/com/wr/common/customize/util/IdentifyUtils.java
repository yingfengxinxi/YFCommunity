package com.wr.common.customize.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.HealthyConstants;
import com.wr.common.customize.constant.HikConstants;
import com.wr.common.customize.model.Identify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: RainCity
 * @Date: 2021-07-20 21:00:45
 * @Desc: 识别相关工具类
 */
public class IdentifyUtils {
    private static final Logger log = LoggerFactory.getLogger(IdentifyUtils.class);

    public static Date sysDateSubtractEight(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY,-8);
        return cal.getTime();
    }

    public static Date sysDateSubtractEight(Date time){
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.HOUR_OF_DAY,-8);
        return cal.getTime();
    }

    /**
     * byte数组转字符串
     * @param data
     * @return
     */
    private static String byteArrayToString(byte[] data){
        return new String(data, StandardCharsets.UTF_8);
    }

    /**
     * 失败
     * @param identify
     * @param msg 返回提示语
     * @return 失败
     */
    public static AjaxResult fail(Identify identify, String msg){
        AjaxResult ajaxResult = new AjaxResult(HikConstants.CODE_FAIL,msg);

        JSONObject data = new JSONObject();
        data.put("eventId",identify.getEventId());
        data.put("deviceIndexCode",identify.getDeviceIndexCode());
        data.put("healthMsg",msg);
        data.put("openGate",2);

        ajaxResult.put("data",data);

        return ajaxResult;
    }

    /**
     * 人证比对模式不支持返回
     * @param identify
     * @return AjaxResult 暂不支持人证比对模式
     */
    public static AjaxResult notSupportMode(Identify identify){

        return fail(identify,"识别模式不支持");
    }

    /**
     * 温度异常返回
     * @param identify
     * @return AjaxResult 温度异常
     */
    public static AjaxResult abnormalTemperature(Identify identify){

        return fail(identify,"温度异常");
    }

    /**
     * 绿码通行
     * @param identify 人员身份信息
     * @param idCardNo 身份证号
     * @return AjaxResult 绿码通行
     */
    public static AjaxResult greenResult(Identify identify, String idCardNo){
        AjaxResult ajaxResult = new AjaxResult(HikConstants.CODE_SUCCESS,"success");
        JSONObject data = new JSONObject();
        data.put("eventId",identify.getEventId());
        data.put("deviceIndexCode",identify.getDeviceIndexCode());
        data.put("idCardNo",idCardNo);
        data.put("personName",identify.getName());
        data.put("healthCode",1);
        data.put("healthMsg", HealthyConstants.HEALTH_MSG_GREEN);
        data.put("openGate",1);

        ajaxResult.put("data",data);
        return ajaxResult;
    }

    /**
     * 黄码请复核
     * @param identify
     * @return AjaxResult 黄码请复核
     */
    public static AjaxResult yellowResult(Identify identify){

        return fail(identify, HealthyConstants.HEALTH_MSG_YELLOW);
    }

    /**
     * 红码禁止通行
     * @param identify
     * @return AjaxResult 红码禁止通行
     */
    public static AjaxResult redResult(Identify identify){

        return fail(identify,HealthyConstants.HEALTH_MSG_RED);
    }

    /**
     * 安康码数据预处理
     * @param body 数据
     * @return JSONObject
     */
    public static JSONObject healthyResultPreHandler(byte[] body){
        String bodyStr = byteArrayToString(body);
        log.info("healthyResult:{}",bodyStr);
        JSONObject jsonObject = JSON.parseObject(bodyStr);
        boolean bodyFlag = jsonObject.getBoolean("flag");
        if(bodyFlag){
            return jsonObject.getJSONObject("data");
        }
        return null;
    }

    /**
     * 安康码返回数据封装
     * @param identify
     * @param resultData
     * data:
     *  idCardNo==>扫码人证件号码；userPhone==>手机号；healthLevel=>安康码颜色，1绿色，2黄色，3红色
     *  name==>扫码人姓名；authField==>授权的字段信息；dataSource==>数据来源：1，安徽省安康码；2、国家健康码
     * @return
     */
    public static AjaxResult qrCodeDataHandler(Identify identify, JSONObject resultData){
        if(null != resultData){
            boolean dataFlag = resultData.getBoolean("flag");
            if(!dataFlag){
                return fail(identify, resultData.getString("errMsg"));
            }
            JSONObject qrCodeData = resultData.getJSONObject("data");
            //通用授权字段，扫码人证件号码
            String idCardNo = qrCodeData.getString("idCardNo");
            //通用授权字段，当前安康码颜色，1绿色，2黄色，3红色
            String healthLevel = qrCodeData.getString("healthLevel");
            //通用授权字段，扫码人姓名
            String name = qrCodeData.getString("name");
            log.info("qrCodeData==>idCardNo:{},healthLevel:{},name:{}",idCardNo,healthLevel,name);

            //绿码
            if("1".equals(healthLevel)){
                identify.setName(name);
                return greenResult(identify, idCardNo);
            }
            //黄码
            if("2".equals(healthLevel)){
                return yellowResult(identify);
            }
            //红码
            if("3".equals(healthLevel)){
                return redResult(identify);
            }
        }
        return fail(identify, "安康码识别错误");
    }

    /**
     * 证件号码核验返回数据封装
     * @param identify
     * @param resultData
     * data:
     *  name==>被核验人姓名；idCardNo==>被核验人证件号码；userPhone==>手机号；
     *  healthLevel==>当前安康码颜色，1绿色，2黄色，3红色
     *  nativeType==>用户授权字段，住户类型：居住，差旅；
     *  jzdSjCode==>用户授权字段，居住地或目的地_市级编码；
     *  jzdQxCode==>用户授权字段，居住地或目的地_区县编码；
     *  jzdJdCode==>用户授权字段，居住地或目的地_街道编码；
     *  jzdSqCode==>用户授权字段，居住地或目的地_社区编码；
     *  colourReason==>颜色红色或者黄色原因；changeTime==>上次码色变更时间。时间格式yyyymmddhh24miss
     *  dataSource==>数据来源：1，安徽省安康码；2、国家健康码；2、国家健康码；authField==>授权的字段信息；
     *  detailedAddress==>详细地址
     * @return
     */
    public static AjaxResult idCardNoDataHandler(Identify identify, JSONObject resultData){
        if(null != resultData){
            boolean dataFlag = resultData.getBoolean("flag");
            if(!dataFlag){
                return fail(identify, resultData.getString("errMsg"));
            }
            JSONObject idCardNoData = resultData.getJSONObject("data");
            //被核验人姓名
            String name = idCardNoData.getString("name");
            //被核验人证件号码
            String idCardNo = idCardNoData.getString("idCardNo");
            //当前安康码颜色，1绿色，2黄色，3红色
            String healthLevel = idCardNoData.getString("healthLevel");
            log.info("idCardNoData==>idCardNo:{},healthLevel:{},name:{}",idCardNo,healthLevel,name);

            //绿码
            if("1".equals(healthLevel)){
                identify.setName(name);
                return greenResult(identify, idCardNo);
            }
            //黄码
            if("2".equals(healthLevel)){
                return yellowResult(identify);
            }
            //红码
            if("3".equals(healthLevel)){
                return redResult(identify);
            }
        }
        return fail(identify, "人脸识别错误");
    }
}
