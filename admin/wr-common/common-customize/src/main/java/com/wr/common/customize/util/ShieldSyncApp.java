package com.wr.common.customize.util;

import com.iflytek.fsp.shield.java.sdk.constant.HttpConstant;
import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.Method;
import com.iflytek.fsp.shield.java.sdk.enums.ParamPosition;
import com.iflytek.fsp.shield.java.sdk.http.ApiClient;
import com.iflytek.fsp.shield.java.sdk.http.BaseApp;
import com.iflytek.fsp.shield.java.sdk.model.ApiRequest;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;

/**
 * @Author: RainCity
 * @Date: 2021-07-14 18:07:18
 * @Desc: 安康码工具类
 */
public class ShieldSyncApp extends BaseApp {

    public ShieldSyncApp() {
        this.apiClient = new ApiClient();
        this.apiClient.init();
        // 管理平台应用查看处获取并修改
        this.appId = "5e439a484e654c66b1ce5b6999f08da6";
        // 管理平台应用查看处获取并修改
        this.appSecret = "D5FBC23B4DCF3A616422AABBFBDF9B54";
        // 核心层ip  223.214.194.39
        this.host = "223.214.194.198";
        //核心层上下文
        this.contextPath ="";
        
        // 核心层暴露的http端口 8800
        this.httpPort = 4989;
        
        
        // 核心层暴露的https端口
        this.httpsPort = 443;
        
        // sdk生成时选择的环境 RELEASE=线上  TEST=测试 PRE=预生产
        this.stage = "RELEASE";
        // 此参数暂时无用
        this.equipmentNo = "XXX";
        // 此参数暂时无用
        this.signStrategyUrl = "/getSignStrategy";
        // 此参数暂时无用
        this.tokenUrl = "/getTokenUrl";
        // 管理平台应用查看处获取并修改
        this.publicKey = "305C300D06092A864886F70D0101010500034B003048024100A6E90ED78C31D9027532162225227B13FCE2D4A5676CB281881C84B57E830B0F44E12F74967480AEF5086CF731DD5A8F538F510CAF39DBF5A6417A03B95C052D0203010001";
        // 关闭云锁验证
        this.icloudlockEnabled = false;
    }

    /**
     * 识读服务
     * Version:202011190956002448
     * @param cityNo
     * @param qrCodeUrl
     * @param sceneCode
     * @param targetType
     * @return
     */
    public ApiResponse transcodingReadServe(String cityNo, String qrCodeUrl, String sceneCode, String targetType) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/prov-city-rest/transcoding/transcodingReadServe", SdkConstant.AUTH_TYPE_DEFAULT, "1");
        
        
        apiRequest.addParam("cityNo", cityNo, ParamPosition.QUERY, false);
        
        apiRequest.addParam("qrCodeUrl", qrCodeUrl, ParamPosition.QUERY, false);
        
        apiRequest.addParam("sceneCode", sceneCode, ParamPosition.QUERY, false);
        
        apiRequest.addParam("targetType", targetType, ParamPosition.QUERY, false);
        
        return syncInvoke(apiRequest);
    }

    /**
     * 消息推送
     * Version:202011190956036938
     * @param templateId
     * @param apiKey
     * @param idCards
     * @param jsonStr
     * @return
     */
    public ApiResponse pushMessage(String templateId, String apiKey, String idCards, String jsonStr) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/wst-gateway/message/pushMessage", SdkConstant.AUTH_TYPE_DEFAULT, "1");
        
        
        apiRequest.addParam("templateId", templateId, ParamPosition.FORM, true);
        
        apiRequest.addParam("apiKey", apiKey, ParamPosition.FORM, true);
        
        apiRequest.addParam("idCards", idCards, ParamPosition.FORM, true);
        
        apiRequest.addParam("jsonStr", jsonStr, ParamPosition.FORM, true);
        
        return syncInvoke(apiRequest);
    }

    /**
     * 证件号码核验
     * Version:202011190956036938
     * @param authToken
     * @param checkUsrIdCard
     * @param checkUsrName
     * @param cityNo
     * @param dataSource
     * @param idcardNo
     * @param siteId
     * @param userName
     * @return
     */
    public ApiResponse checkHealthyByIdCardNo(String authToken, String checkUsrIdCard, String checkUsrName, String cityNo,
                                              String dataSource, String idcardNo, String siteId, String userName) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/prov-city-rest/check/checkHealthyByIdCardNo", SdkConstant.AUTH_TYPE_DEFAULT, "1");
        
        
        apiRequest.addParam("authToken", authToken, ParamPosition.QUERY, false);
        
        apiRequest.addParam("checkUsrIdCard", checkUsrIdCard, ParamPosition.QUERY, false);
        
        apiRequest.addParam("checkUsrName", checkUsrName, ParamPosition.QUERY, false);
        
        apiRequest.addParam("cityNo", cityNo, ParamPosition.QUERY, false);
        
        apiRequest.addParam("dataSource", dataSource, ParamPosition.QUERY, false);
        
        apiRequest.addParam("idcardNo", idcardNo, ParamPosition.QUERY, false);
        
        apiRequest.addParam("siteId", siteId, ParamPosition.QUERY, false);
        
        apiRequest.addParam("userName", userName, ParamPosition.QUERY, false);
        
        return syncInvoke(apiRequest);
    }

    /**
     * 用户被扫
     * Version:202011190956469695
     * @param authToken
     * @param checkUsrIdCard
     * @param checkUsrName
     * @param cityNo
     * @param dataSource
     * @param qrCodeUrl
     * @param siteId
     * @return
     */
    public ApiResponse getQrcodeInfo(String authToken, String checkUsrIdCard, String checkUsrName, String cityNo, String dataSource, String qrCodeUrl, String siteId) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/prov-city-rest/check/getQrcodeInfo", SdkConstant.AUTH_TYPE_DEFAULT, "1");
        
        
        apiRequest.addParam("authToken", authToken, ParamPosition.QUERY, false);
        
        apiRequest.addParam("checkUsrIdCard", checkUsrIdCard, ParamPosition.QUERY, false);
        
        apiRequest.addParam("checkUsrName", checkUsrName, ParamPosition.QUERY, false);
        
        apiRequest.addParam("cityNo", cityNo, ParamPosition.QUERY, false);
        
        apiRequest.addParam("dataSource", dataSource, ParamPosition.QUERY, false);
        
        apiRequest.addParam("qrCodeUrl", qrCodeUrl, ParamPosition.QUERY, false);
        
        apiRequest.addParam("siteId", siteId, ParamPosition.QUERY, false);
        
        return syncInvoke(apiRequest);
    }

    /**
     * 授权服务
     * Version:202011190956372526
     * @param requestInfo
     * @return
     */
    public ApiResponse authorizationApply(String requestInfo) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/prov-city-rest/authorization/authorizationApply", SdkConstant.AUTH_TYPE_DEFAULT, "1");
        
        
        apiRequest.addParam("requestInfo", requestInfo, ParamPosition.QUERY, false);
        
        return syncInvoke(apiRequest);
    }

    /**
     * 用户扫码核验
     * Version:202011190956546163
     * @param authToken
     * @param cityNo
     * @param dataSource
     * @param inout
     * @param latitudeNum
     * @param longitudeNum
     * @param siteId
     * @param token
     * @return
     */
    public ApiResponse checkByUser(String authToken, String cityNo, String dataSource, String inout, String latitudeNum, String longitudeNum, String siteId, String token) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/prov-city-rest/check/checkByUser", SdkConstant.AUTH_TYPE_DEFAULT, "1");
        
        
        apiRequest.addParam("authToken", authToken, ParamPosition.QUERY, false);
        
        apiRequest.addParam("cityNo", cityNo, ParamPosition.QUERY, false);
        
        apiRequest.addParam("dataSource", dataSource, ParamPosition.QUERY, false);
        
        apiRequest.addParam("inout", inout, ParamPosition.QUERY, false);
        
        apiRequest.addParam("latitudeNum", latitudeNum, ParamPosition.QUERY, false);
        
        apiRequest.addParam("longitudeNum", longitudeNum, ParamPosition.QUERY, false);
        
        apiRequest.addParam("siteId", siteId, ParamPosition.QUERY, false);
        
        apiRequest.addParam("token", token, ParamPosition.QUERY, false);
        
        return syncInvoke(apiRequest);
    }

    /**
     * 身份核验
     * Version:202011190957362031
     * @param cityNo
     * @param qrCodeUrl
     * @return
     */
    public ApiResponse qrcodeInfoCheck(String cityNo, String qrCodeUrl) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/prov-city-rest/check/qrcodeInfoCheck", SdkConstant.AUTH_TYPE_DEFAULT, "1");
        
        
        apiRequest.addParam("cityNo", cityNo, ParamPosition.QUERY, false);
        
        apiRequest.addParam("qrCodeUrl", qrCodeUrl, ParamPosition.QUERY, false);
        
        return syncInvoke(apiRequest);
    }
   
}