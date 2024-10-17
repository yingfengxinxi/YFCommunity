package com.wr.common.customize.util;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.customize.constant.HikConstants;
import com.wr.common.customize.model.GarageNodeSearch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author lvzy
 * @Date 2024/3/19 14:48
 */
@Slf4j
public class HaiKUtils {


    /**
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static JSONObject httpPost(CommunityHik communityHik, String url, String params) throws Exception {
        /**
         * https://ip:port/artemis/api/resource/v1/org/orgList
         * 通过查阅AI Cloud开放平台文档或网关门户的文档可以看到获取组织列表的接口定义,该接口为POST请求的Rest接口, 入参为JSON字符串，接口协议为https。
         * ArtemisHttpUtil工具类提供了doPostStringArtemis调用POST请求的方法，入参可传JSON字符串, 请阅读开发指南了解方法入参，没有的参数可传null
         */
        ArtemisConfig config = new ArtemisConfig();
        config.setHost(communityHik.getHikIp() + ":" + communityHik.getHikPort()); // 代理API网关nginx服务器ip端口
        config.setAppKey(communityHik.getHikAk());  // 秘钥appkey
        config.setAppSecret(communityHik.getHikSk());// 秘钥appSecret
        final String getCamsApi = HikConstants.PRE_PATH + url;

        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getCamsApi);
            }
        };
        log.info("url:{}", getCamsApi);
        log.info("params:{}", params);
        String response = ArtemisHttpUtil.doPostStringArtemis(config, path, params, null, null, "application/json");

        return JsonUtil.parseObject(response);
    }

    /**
     * 事件服务接口
     * 查询事件订阅信息
     *
     * @param eventTypes 事件类型
     * @param eventDest  指定事件接收的地址，
     * @param subType    订阅类型，0-订阅原始事件，1-联动事件，2-原始事件和联动事件，不填使用默认值0
     * @return com.alibaba.fastjson2.JSONArray
     */
    public static Boolean eventSubscription(CommunityHik communityHik, Integer[] eventTypes, String eventDest, int subType) {
        String types = Arrays.toString(eventTypes);
        log.info("eventTypes:{},eventDest:{}", types, eventDest);
        String uri = "/api/eventService/v1/eventSubscriptionByEventTypes";
        String params = "{" +
                "\"eventTypes\": " + types + "," +
                "\"eventDest\": \"" + eventDest + "\"," +
                "\"subType\": " + subType + "" +
                "}";
        try {
            return success(httpPost(communityHik, uri, params));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 事件服务接口
     * 按事件类型取消订阅事件
     *
     * @param eventTypes 事件类型
     * @return java.lang.Boolean
     */
    public static Boolean eventUnSubscription(CommunityHik communityHik, Integer[] eventTypes) {
        String types = Arrays.toString(eventTypes);
        String url = "/api/eventService/v1/eventUnSubscriptionByEventTypes";
        String params = "{" +
                "\"eventTypes\":" + types + "" +
                "}";
        try {
            return success(httpPost(communityHik, url, params));
        } catch (Exception e) {
            throw new ServiceException("事件取消订阅失败");
        }
    }

    /**
     * 获取视频事件的图片
     * 视频业务-->视频功能
     *
     * @param svrIndexCode 存储接入服务（图片服务器唯一标识），获取picUri处会提供此参数
     * @param picUri       图片地址
     * @return
     */
    public static String videoEventPictures(CommunityHik communityHik, String svrIndexCode, String picUri) throws Exception {
        if (StringUtils.isEmpty(svrIndexCode)) {
            log.error("图片服务编号为空");
            return "";
        }
        if (StringUtils.isEmpty(picUri)) {
            log.error("图片相对地址为空");
            return "";
        }
        int index = picUri.indexOf("/pic?");
        if (index == -1) {
            log.error("检测图片{}错误", picUri);
            return "";
        }
        String netProtocol = "https";
        String uri = "/api/video/v1/events/picture";
        String params = "{" +
                "\"netProtocol\": \"" + netProtocol + "\"," +
                "\"svrIndexCode\": \"" + svrIndexCode + "\"," +
                "\"picUri\": \"" + picUri.substring(index) + "\"" +
                "}";
        JSONObject jsonObject = httpPost(communityHik, uri, params);

        if (!success(jsonObject)) {
            return null;
        }
        JSONObject data = jsonObject.getJSONObject("data");
        if (null == data) {
            return null;
        }
        return data.getString("picUrl");
    }

    /**
     * 获取监控点预览取流URLv2
     * 视频业务-视频能力
     *
     * @param cameraIndexCode 监控点唯一标识
     * @param protocol        取流协议 见 com.wr.common.customize.constant.ProtocolConstants
     * @return java.lang.String
     */
    public static String camerasPreviewURLs(CommunityHik communityHik, String cameraIndexCode, String protocol) throws Exception {
        String uri = "/api/video/v2/cameras/previewURLs";

        String params = "{" +
                "\"cameraIndexCode\": \"" + cameraIndexCode + "\"," +
                "\"streamType\": 1," +
                "\"protocol\": \"" + protocol + "\"" +
                "}";
        JSONObject jsonObject = httpPost(communityHik, uri, params);
        if (success(jsonObject)) {
            JSONObject data = jsonObject.getJSONObject("data");
            if (null == data) {
                return null;
            }
            return data.getString("url");
        }
        return "";
    }

    /**
     * 获取门禁设备在线状态
     * 一卡通应用服务-->门禁接口-->门禁网关接口
     *
     * @param indexCode 设备唯一编码
     * @return java.lang.Integer
     */
    public static Integer acsDeviceOnline(CommunityHik communityHik, String indexCode) throws Exception {
        String uri = "/api/nms/v1/online/acs_device/get";
        return resourceOnline(communityHik, uri, indexCode);
    }

    /**
     * 可视对讲返控
     *
     * @param communityHik
     * @param deviceIndexCode
     * @param controlValue    反控操作类型:0-常开，1-关闭一次，2-打开一次,3-常闭
     * @return
     * @throws Exception
     */
    public static JSONObject visualDeviceControl(CommunityHik communityHik, String deviceIndexCode, Integer controlValue) throws Exception {
        String uri = "/api/vis/v1/device/control";
        String params = "{\n" +
                "\t\"deviceIndexCode\":\"" + deviceIndexCode + "\",\n" +
                "\t\"controlValue\":" + controlValue + "\n" +
                "}";
        JSONObject jsonObject = httpPost(communityHik, uri, params);
        System.out.println(jsonObject);
        return jsonObject.getJSONObject("data");
    }

    protected static final String accessEventUri = "/api/vis/v1/accessEvent/events";


    /**
     * 查询可视对讲出入事件总数量
     *
     * @param startTime 开始时间【必传】
     * @param endTime   结束时间【必传】
     * @return {@link JSONArray}
     * @ver v1.0.0
     */
    public static Integer accessEventTotal(CommunityHik communityHik, String startTime, String endTime) throws Exception {

        int pageNo = 1;
        int pageSize = 1;

        String params = "{\n" +
                "    \"startTime\": \"" + startTime + "\",\n" +
                "    \"endTime\": \"" + endTime + "\",\n" +
                "    \"pageNo\": " + pageNo + ",\n" +
                "    \"pageSize\": " + pageSize + "\n" +
                "}";

        return resourceSearchTotal(communityHik, params, accessEventUri);
    }


    /**
     * 查询可视对讲出入事件
     *
     * @param pageNo    页码
     * @param pageSize  每页多少条
     * @param startTime 开始时间【必传】
     * @param endTime   结束时间【必传】
     * @return {@link JSONArray}
     * @ver v1.0.0
     */
    public static JSONArray accessEvent(CommunityHik communityHik, int pageNo, int pageSize, String startTime, String endTime) throws Exception {

        String params = "{\n" +
                "    \"startTime\": \"" + startTime + "\",\n" +
                "    \"endTime\": \"" + endTime + "\",\n" +
                "    \"pageNo\": " + pageNo + ",\n" +
                "    \"pageSize\": " + pageSize + "\n" +
                "}";

        return resourceSearch(communityHik, params, accessEventUri);
    }

    protected static final String acsDeviceSearchUrl = "/api/resource/v2/acsDevice/search";

    /**
     * 查询门禁设备总数量
     * 一卡通应用服务-->门禁接口-->门禁资源接口
     *
     * @return com.alibaba.fastjson2.JSONArray
     */
    public static Integer acsDeviceSearchTotal(CommunityHik communityHik) throws Exception {
        int pageNo = 1;
        int pageSize = 1;


        String params = "{" +
                "\"pageNo\": " + pageNo + ", " +
                "\"pageSize\": \"" + pageSize + "\"" +
                "}";
        return resourceSearchTotal(communityHik, params, acsDeviceSearchUrl);
    }

    /**
     * 查询门禁设备列表v2
     * 一卡通应用服务-->门禁接口-->门禁资源接口
     *
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @return com.alibaba.fastjson2.JSONArray
     */
    public static JSONArray acsDeviceSearch(CommunityHik communityHik, int pageNo, int pageSize) throws Exception {

        String params = "{" +
                "\"pageNo\": " + pageNo + ", " +
                "\"pageSize\": \"" + pageSize + "\"" +
                "}";
        return resourceSearch(communityHik, params, acsDeviceSearchUrl);
    }


    /**
     * 获取可视对讲设备在线状态
     * 一卡通应用服务-->可视对讲接口-->可视对讲网关接口
     *
     * @param indexCode 设备唯一编码
     * @return java.lang.Integer
     */
    public static Integer visDeviceOnline(CommunityHik communityHik, String indexCode) throws Exception {
        String uri = "/api/nms/v1/online/vis_device/get";
        return resourceOnline(communityHik, uri, indexCode);
    }

    protected static final String visDeviceSearchUri = "/api/resource/v2/visDevice/search";


    /**
     * 查询可视对讲设备列表总条数
     * 一卡通应用服务-->可视对讲接口-->可视对讲资源接口
     *
     * @return com.alibaba.fastjson2.JSONArray
     */
    public static Integer visDeviceSearchTotal(CommunityHik communityHik) throws Exception {

        int pageNo = 1;
        int pageSize = 1;

        String params = "{" +
                "\"pageNo\": " + pageNo + ", " +
                "\"pageSize\": \"" + pageSize + "\"" +
                "}";
        return resourceSearchTotal(communityHik, params, visDeviceSearchUri);
    }

    /**
     * 查询可视对讲设备列表v2
     * 一卡通应用服务-->可视对讲接口-->可视对讲资源接口
     *
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @return com.alibaba.fastjson2.JSONArray
     */
    public static JSONArray visDeviceSearch(CommunityHik communityHik, int pageNo, int pageSize) throws Exception {
        String uri = "/api/resource/v2/visDevice/search";
        String params = "{" +
                "\"pageNo\": " + pageNo + ", " +
                "\"pageSize\": \"" + pageSize + "\"" +
                "}";
        return resourceSearch(communityHik, params, visDeviceSearchUri);
    }

    protected static final String doorSearchUrl = "/api/resource/v2/door/search";

    /**
     * 查询门禁点总条数
     *
     * @param communityHik
     * @return
     * @throws Exception
     */
    public static Integer doorSearchTotal(CommunityHik communityHik) throws Exception {
        int pageNo = 1;
        int pageSize = 1;
        String params = "{" +
                "\"pageNo\": " + pageNo + ", " +
                "\"pageSize\": \"" + pageSize + "\"" +
                "}";
        return resourceSearchTotal(communityHik, params, doorSearchUrl);
    }

    /**
     * 查询门禁点
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public static JSONArray doorSearch(CommunityHik communityHik, int pageNo, int pageSize) throws Exception {
        String params = "{" +
                "\"pageNo\": " + pageNo + ", " +
                "\"pageSize\": \"" + pageSize + "\"" +
                "}";
        return resourceSearch(communityHik, params, doorSearchUrl);
    }

    /**
     * 门禁点状态
     *
     * @param communityHik
     * @param indexCode
     * @return
     */
    public static Integer doorOnline(CommunityHik communityHik, String indexCode) throws Exception {
        String uri = "/api/acs/v1/door/states";
        String params = "{\n" +
                "    \"doorIndexCodes\": [\n" +
                "        \"" + indexCode + "\"\n" +
                "    ]\n" +
                "}";

        JSONObject jsonObject = httpPost(communityHik, uri, params);
        if (!success(jsonObject)) {
            return -1;
        }
        JSONObject data = jsonObject.getJSONObject("data");
        if (null == data) {
            return -1;
        }
        JSONArray jsonArray = data.getJSONArray("authDoorList");
        if (jsonArray.isEmpty()) {
            return -1;
        }
        JSONObject acs = jsonArray.getJSONObject(0);
        return acs.getInteger("doorState");
    }


    /**
     * 根据人员id查询人员信息
     *
     * @param communityHik
     * @param personId
     * @return
     * @throws Exception
     */
    public static JSONArray personInfo(CommunityHik communityHik, String personId) throws Exception {
        String uri = "/api/resource/v1/person/condition/personInfo";
        String params = "{\n" +
                "    \"paramName\": \"personId\",\n" +
                "    \"paramValue\": [\n" +
                "        \"" + personId + "\"\n" +
                "    ]\n" +
                "}";
        return resourceSearch(communityHik, params, uri);
    }

    /**
     * 根据车牌号查询车主信息
     *
     * @param communityHik
     * @param plateNo
     * @return
     * @throws Exception
     */
    public static JSONArray vehicleList(CommunityHik communityHik, String plateNo) throws Exception {
        String uri = "/api/resource/v2/vehicle/advance/vehicleList";
        String params = "{\n" +
                "    \"plateNo\": \"" + plateNo + "\",\n" +
                "    \"pageNo\":1,\n" +
                "    \"pageSize\":1,\n" +
                "    \"isBandPerson\":1\n" +
                "}";
        return resourceSearch(communityHik, params, uri);
    }

    protected static final String crossRecordUrl = "/api/pms/v1/crossRecords/page";

    /**
     * 车辆车型记录
     *
     * @param communityHik
     * @param pageNo
     * @param pageSize
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public static JSONArray crossRecords(CommunityHik communityHik, int pageNo, int pageSize, String startTime, String endTime) throws Exception {

        String params = "{\n" +
                "    \"startTime\": \"" + startTime + "\",\n" +
                "    \"endTime\": \"" + endTime + "\",\n" +
                "    \"pageNo\": " + pageNo + ",\n" +
                "    \"pageSize\": " + pageSize + "\n" +
                "}";
        return resourceSearch(communityHik, params, crossRecordUrl);
    }

    /**
     * 车辆车型记录总条数
     *
     * @param communityHik
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public static Integer crossRecordsTotal(CommunityHik communityHik, String startTime, String endTime) throws Exception {
        int pageNo = 1;
        int pageSize = 1;

        String params = "{\n" +
                "    \"startTime\": \"" + startTime + "\",\n" +
                "    \"endTime\": \"" + endTime + "\",\n" +
                "    \"pageNo\": " + pageNo + ",\n" +
                "    \"pageSize\": " + pageSize + "\n" +
                "}";
        return resourceSearchTotal(communityHik, params, crossRecordUrl);
    }

    protected static final String visitingSecordUrl = "/api/visitor/v1/event/turnover/search";

    /**
     * 访客记录事件总条数
     *
     * @param communityHik
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public static Integer visitingSecordsTotal(CommunityHik communityHik, String startTime, String endTime, String indexCode) throws Exception {
        int pageNo = 1;
        int pageSize = 1;

        String params = "{\n" +
                "    \"resourceCode\": \"" + indexCode + "\",\n" +
                "    \"eventTimeBegin\": \"" + startTime + "\",\n" +
                "    \"eventTimeEnd\": \"" + endTime + "\",\n" +
                "    \"pageNo\": " + pageNo + ",\n" +
                "    \"pageSize\": " + pageSize + "\n" +
                "}";
        return resourceSearchTotal(communityHik, params, visitingSecordUrl);
    }

    /**
     * 访客记录事件
     *
     * @param communityHik
     * @param pageNo
     * @param pageSize
     * @param startTime
     * @param endTime
     * @param indexCode
     * @return
     * @throws Exception
     */
    public static JSONArray visitingSecords(CommunityHik communityHik, int pageNo, int pageSize, String startTime, String endTime, String indexCode) throws Exception {

        String params = "{\n" +
                "    \"resourceCode\": \"" + indexCode + "\",\n" +
                "    \"eventTimeBegin\": \"" + startTime + "\",\n" +
                "    \"eventTimeEnd\": \"" + endTime + "\",\n" +
                "    \"pageNo\": " + pageNo + ",\n" +
                "    \"pageSize\": " + pageSize + "\n" +
                "}";
        return resourceSearch(communityHik, params, visitingSecordUrl);
    }

    protected static final String visitingRecordUri = "/api/visitor/v2/visiting/records";

    /**
     * 来访记录总数
     *
     * @param communityHik
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public static Integer visitingRecordsTotal(CommunityHik communityHik, String startTime, String endTime) throws Exception {

        int pageNo = 1;
        int pageSize = 1;

        String params = "{\n" +
                "    \"visitStartTimeBegin\": \"" + startTime + "\",\n" +
                "    \"visitStartTimeEnd\": \"" + endTime + "\",\n" +
                "    \"pageNo\": " + pageNo + ",\n" +
                "    \"pageSize\": " + pageSize + "\n" +
                "}";
        return resourceSearchTotal(communityHik, params, visitingRecordUri);
    }

    /**
     * 来访记录
     *
     * @param communityHik
     * @param pageNo
     * @param pageSize
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public static JSONArray visitingRecords(CommunityHik communityHik, int pageNo, int pageSize, String startTime, String endTime) throws Exception {

        String params = "{\n" +
                "    \"visitStartTimeBegin\": \"" + startTime + "\",\n" +
                "    \"visitStartTimeEnd\": \"" + endTime + "\",\n" +
                "    \"pageNo\": " + pageNo + ",\n" +
                "    \"pageSize\": " + pageSize + "\n" +
                "}";
        return resourceSearch(communityHik, params, visitingRecordUri);
    }

    protected static final String doorEventsUrl = "/api/acs/v2/door/events";

    /**
     * 查询门禁点事件总条数
     *
     * @param communityHik
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public static Integer doorEventsTotal(CommunityHik communityHik, String startTime, String endTime) throws Exception {
        int pageNo = 1;
        int pageSize = 1;

        String params = "{\n" +
                "\t\"pageNo\":\"" + pageNo + "\",\n" +
                "\t\"pageSize\":\"" + pageSize + "\",\n" +
                "\t\"startTime\":\"" + startTime + "\",\n" +
                "\t\"endTime\":\"" + endTime + "\"\n" +
                "}";
        return resourceSearchTotal(communityHik, params, doorEventsUrl);
    }

    /**
     * 查询门禁点事件
     *
     * @param communityHik
     * @param pageNo
     * @param pageSize
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public static JSONArray doorEvents(CommunityHik communityHik, int pageNo, int pageSize, String startTime, String endTime) throws Exception {
        String params = "{\n" +
                "\t\"pageNo\":\"" + pageNo + "\",\n" +
                "\t\"pageSize\":\"" + pageSize + "\",\n" +
                "\t\"startTime\":\"" + startTime + "\",\n" +
                "\t\"endTime\":\"" + endTime + "\"\n" +
                "}";
        String url = "/api/acs/v2/door/events";
        return resourceSearch(communityHik, params, url);
    }

    protected static final String cameraSearchUrl = "/api/resource/v2/camera/search";

    /**
     * 监控点总条数查询
     *
     * @param communityHik
     * @return
     * @throws Exception
     */
    public static Integer cameraSearchTotal(CommunityHik communityHik) throws Exception {
        int pageNo = 1;
        int pageSize = 1;

        String params = "{" +
                "\"pageNo\": " + pageNo + ", " +
                "\"pageSize\": \"" + pageSize + "\"" +
                "}";
        return resourceSearchTotal(communityHik, params, cameraSearchUrl);
    }

    /**
     * 监控点查询
     *
     * @param communityHik
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public static JSONArray cameraSearch(CommunityHik communityHik, int pageNo, int pageSize) throws Exception {
        String params = "{" +
                "\"pageNo\": " + pageNo + ", " +
                "\"pageSize\": \"" + pageSize + "\"" +
                "}";
        return resourceSearch(communityHik, params, cameraSearchUrl);
    }

    private static JSONArray resourceSearch(CommunityHik communityHik, String params, String uri) throws Exception {
        JSONObject jsonObject = httpPost(communityHik, uri, params);
        if (success(jsonObject)) {
            JSONObject data = jsonObject.getJSONObject("data");
            if (ObjectUtil.isEmpty(data)) {
                return new JSONArray();
            }
            JSONArray list = data.getJSONArray("list");
            if (list == null) {
                list = data.getJSONArray("rows");
            }
            return list;
        }
        return new JSONArray();
    }


    /**
     * 查询分页接口总条数
     *
     * @param communityHik
     * @param params
     * @param uri
     * @return
     * @throws Exception
     */
    private static Integer resourceSearchTotal(CommunityHik communityHik, String params, String uri) throws Exception {
        JSONObject jsonObject = httpPost(communityHik, uri, params);
        if (success(jsonObject)) {
            JSONObject data = jsonObject.getJSONObject("data");
            Integer total = Integer.valueOf(String.valueOf(data.get("total")));
            return total;
        }
        return 1;
    }

    public static Boolean success(JSONObject jsonObject) {
        if (null == jsonObject) {
            return false;
        }
        return "0".equals(jsonObject.getString("code"));
    }

    /**
     * 获取监控点在线状态
     * 视频业务-->视频网关
     *
     * @param indexCode 设备唯一编码
     * @return java.lang.Integer
     */
    public static Integer cameraOnline(CommunityHik communityHik, String indexCode) throws Exception {
        String uri = "/api/nms/v1/online/camera/get";
        return resourceOnline(communityHik, uri, indexCode);
    }

    private static Integer resourceOnline(CommunityHik communityHik, String uri, String indexCode) throws Exception {

        String params = "{" +
                "\"indexCodes\": [\"" + indexCode + "\"]," +
                "\"pageNo\": 1," +
                "\"pageSize\": 1" +
                "}";
        JSONObject jsonObject = httpPost(communityHik, uri, params);
        if (!success(jsonObject)) {
            return -1;
        }
        JSONObject data = jsonObject.getJSONObject("data");
        if (null == data) {
            return -1;
        }
        JSONArray jsonArray = data.getJSONArray("list");
        if (jsonArray.isEmpty()) {
            return -1;
        }
        JSONObject acs = jsonArray.getJSONObject(0);
        return acs.getInteger("online");
    }


    /**
     * 获取停车库列表
     * 车辆管控-->车辆及车库信息接口
     *
     * @return JSONArray
     */
    public static JSONArray parkList(CommunityHik communityHik, String parkIndexCodes) throws Exception {
        String params = "{\n" +
                "    \"parkIndexCodes\": \"" + parkIndexCodes + "\"\n" +
                "}";
        String url = "/api/resource/v1/park/parkList";
        JSONObject jsonObject = httpPost(communityHik, url, params);
        if ("0".equals(jsonObject.get("code"))) {
            return (JSONArray) jsonObject.get("data");
        } else {
            return new JSONArray();
        }
    }


    /**
     * 根据停车场编码反控道闸
     * 车库管控-->根据停车场编码反控道闸
     *
     * @param communityHik 海康配置管理
     * @param parkSyscode  停车场唯一标识码
     * @param command      控闸命令（0：关闸 1：开闸 3：常开）
     * @return JSONArray
     */
    public static Boolean deviceControlBatch(CommunityHik communityHik, String parkSyscode, Integer command) throws Exception {
        String params = "{" +
                "\"parkSyscode\": \"" + parkSyscode + "\"" +
                "\"command\": \"" + command + "\"" +
                "}";
        String url = "/api/pms/v1/deviceControlBatch";
        JSONObject jsonObject = httpPost(communityHik, url, params);
        return success(jsonObject);
    }

    /**
     * 根据车道编码反控道闸
     * 车库管控-->根据车道编码反控道闸
     *
     * @param communityHik   海康配置管理
     * @param roadwaySyscode 车道唯一标识
     * @param command        控闸命令（0：关闸 1：开闸 3：常开）
     * @return JSONArray
     */
    public static Boolean deviceControl(CommunityHik communityHik, String roadwaySyscode, Integer command) throws Exception {
        String params = "{" +
                "\"roadwaySyscode\": \"" + roadwaySyscode + "\"" +
                "\"command\": \"" + command + "\"" +
                "}";
        String url = "/api/pms/v1/deviceControl";
        JSONObject jsonObject = httpPost(communityHik, url, params);
        return success(jsonObject);
    }

    public static JSONObject getGarageNode(CommunityHik communityHik, GarageNodeSearch search) {
        try {
            return getGarageNode(communityHik, search.getParentIndexCode(), search.getParentResourceType(), search.getResourceTypes(), search.getName(), search.getExpressions(), search.getOrderBy(), search.getOrderType(), search.getPageSize(), search.getPageNo());
        } catch (Exception e) {
            throw new ServiceException("获取停车库节点失败");
        }
    }

    /**
     * 查询停车库节点信息
     * 车库管控-->查询停车库节点信息
     *
     * @param communityHik       海康配置管理
     * @param parentIndexCode    父节点编号
     * @param parentResourceType 父节点类型,parentIndexCode、parentResourceTypes必须同时存在或者同时不填 停车库:parking 出入口：entrance 车道：roadway 楼层：parkFloor 车位：parkSpace
     * @param resourceTypes      资源类型，类型之间为或的关系  停车库:parking 出入口：entrance 车道：roadway 楼层：parkFloor 车位：parkSpace
     * @param name               节点名称,模糊匹配
     * @param expressions        查询表达式
     *                           +key 资源属性名,目前支持查询的字段：updateTime、createTime、indexCode，例如key传updateTime，operator传between可以查询特定时间段更新的数据，考虑到校时和夏令时，建议值查询过去一天的数据变更；
     *                           +operator	0 ：=; 1 ：>=; 2 ：<=; 3 ：in; 4 ：not in; 5 ：between; 6 ：like
     *                           +values 资源属性值,=、>=、<=、like、values数组长度只能是1； in、not in，values数组长度大于1； between只能用于整形、日期； like只能用于字符串
     * @param orderBy            排序字段,注意：排序字段必须是查询条件，否则返回参数错误
     * @param orderType          降序升序,降序：desc 升序：asc
     * @param pageSize           分页大小,0<pageSize≤1000
     * @param pageNo             当前页码,pageNo≥1
     * @return JSONArray
     */
    public static JSONObject getGarageNode(CommunityHik communityHik, String parentIndexCode, String parentResourceType, List<String> resourceTypes, String name, List<Map<String, Object>> expressions, String orderBy, String orderType, Integer pageSize, Integer pageNo) throws Exception {
        StringBuilder paramBuilder = new StringBuilder();
        paramBuilder.append("{");
        if (StringUtils.isNotBlank(parentIndexCode)) {
            paramBuilder.append("\"parentIndexCode\":\"").append(parentIndexCode).append("\",");
        }
        if (StringUtils.isNotBlank(parentResourceType)) {
            paramBuilder.append("\"").append("parentResourceType\":\"").append(parentResourceType).append("\",");
        }
        if (!CollectionUtils.isEmpty(resourceTypes)) {
            paramBuilder.append("\"resourceTypes\":[");
            for (int i = 0; i < resourceTypes.size(); i++) {
                paramBuilder.append("\"").append(resourceTypes.get(i)).append("\"");
                if (i < resourceTypes.size() - 1) {
                    paramBuilder.append(",");
                }
            }
            paramBuilder.append("],");
        }
        if (!CollectionUtils.isEmpty(expressions)) {
            paramBuilder.append("\"expressions\":[");
            for (int i = 0; i < expressions.size(); i++) {
                paramBuilder.append("{");
                paramBuilder.append("\"key\":\"").append(expressions.get(i).get("key")).append("\",");
                paramBuilder.append("\"operator\":\"").append(expressions.get(i).get("operator")).append("\",");
                List<String> valueList = (List<String>) expressions.get(i).get("values");
                paramBuilder.append("\"values\":[");
                for (int j = 0; j < valueList.size(); j++) {
                    paramBuilder.append("\"").append(valueList.get(j)).append("\"");
                    if (j < valueList.size() - 1) {
                        paramBuilder.append(",");
                    }
                }
                paramBuilder.append("]");
                paramBuilder.append("}");
                if (i < expressions.size() - 1) {
                    paramBuilder.append(",");
                }
            }
            paramBuilder.append("],");
        }
        if (StringUtils.isNotBlank(name)) {
            paramBuilder.append("\"").append("name\":\"").append(name).append("\",");
        }
        if (StringUtils.isNotBlank(orderBy)) {
            paramBuilder.append("\"").append("orderBy\":\"").append(orderBy).append("\",");
        }
        if (StringUtils.isNotBlank(orderType)) {
            paramBuilder.append("\"").append("orderType\":\"").append(orderType).append("\",");
        }
        if (null != pageSize) {
            paramBuilder.append("\"").append("pageSize\":\"").append(pageSize).append("\",");
        }
        if (null != pageNo) {
            paramBuilder.append("\"").append("pageNo\":\"").append(pageNo).append("\"");
        }
        paramBuilder.append("}");
        String url = "/api/resource/v1/park/search";
        System.out.println(paramBuilder);
        return httpPost(communityHik, url, paramBuilder.toString());
    }

    /**
     * 查询出入口设备关联关系
     *
     * @param communityHik 海康配置管理
     * @param syscode      结点唯一标志，查询停车库节点信息接口获取返回参数indexCode
     * @param nodeType     节点类型（1：停车场，2：出入口，4：车道，16：子库停车场）
     * @param subNode      是否包含下级节点，不填默认false
     * @param deviceType   设备资源类型， terminal：岗亭缴费终端； controlDevice：出入口控制设备； displayDevice：出入口显示设备； cardReader：读卡设备
     * @return JSONObject
     */
    public static JSONObject deviceRelation(CommunityHik communityHik, String syscode, Integer nodeType, String subNode, String deviceType) throws Exception {
        StringBuilder paramBuilder = new StringBuilder();
        paramBuilder.append("{");
        paramBuilder.append("\"syscode\":\"").append(syscode).append("\",");
        paramBuilder.append("\"nodeType\":\"").append(nodeType).append("\"");
        if (StringUtils.isNotBlank(subNode)) {
            paramBuilder.append(",");
            paramBuilder.append("\"subNode\":\"").append(subNode).append("\"");
        }
        if (StringUtils.isNotBlank(deviceType)) {
            paramBuilder.append(",");
            paramBuilder.append("\"deviceType\":\"").append(deviceType).append("\"");
        }
        paramBuilder.append("}");
        String url = "/api/pms/v1/park/deviceRelation/search";
        System.out.println(paramBuilder);
        return httpPost(communityHik, url, paramBuilder.toString());
    }

    /**
     * 获取出入口列表
     * 车辆管控-->车辆及车库信息接口
     *
     * @param parkIndexCode 停车场唯一标识
     * @return JSONArray
     */
    public static JSONArray entranceList(CommunityHik communityHik, String parkIndexCode) {
        String uri = "/api/resource/v1/entrance/entranceList";
        String param = "{" +
                "\"parkIndexCodes\":\"" + parkIndexCode + "\"" +
                "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = httpPost(communityHik, uri, param);
        } catch (Exception e) {
            throw new RuntimeException("获取出入口列表失败");
        }
        if ("0".equals(jsonObject.get("code"))) {
            return (JSONArray) jsonObject.get("data");
        }
        return new JSONArray();
    }

    /**
     * 获取车道列表
     * 车辆管控-->车辆及车库信息接口
     *
     * @param entranceIndexCode 出入口唯一标识
     * @return JSONArray
     */
    public static JSONArray roadwayList(CommunityHik communityHik, String entranceIndexCode) {
        String uri = "/api/resource/v1/roadway/roadwayList";
        String param = "{" +
                "\"entranceIndexCodes\":\"" + entranceIndexCode + "\"" +
                "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = httpPost(communityHik, uri, param);
        } catch (Exception e) {
            throw new RuntimeException("获取车道列表失败");
        }
        if ("0".equals(jsonObject.get("code"))) {
            return (JSONArray) jsonObject.get("data");
        }
        return new JSONArray();
    }

    /**
     * 资源信息接口 -> 获取资源列表v2 Total
     *
     * @param communityHik 海康配置管理
     * @return
     */
    public static Integer deviceResourceTotal(CommunityHik communityHik, String resourceType) {
        int pageNo = 1;
        int pageSize = 1;
        String uri = "/api/irds/v2/deviceResource/resources";
        String params = "{" +
                "\"pageNo\": " + pageNo + ", " +
                "\"pageSize\": " + pageSize + ", " +
                "\"resourceType\": \"" + resourceType + "\"" +
                "}";
        try {
            return resourceSearchTotal(communityHik, params, uri);
        } catch (Exception e) {
            throw new RuntimeException("资源信息获取失败");
        }
    }

    /**
     * 资源信息接口 -> 获取资源列表v2
     *
     * @param pageNo       页码
     * @param pageSize     每页条数
     * @param resourceType 设备类型
     * @return
     */
    public static JSONArray deviceResource(CommunityHik communityHik, int pageNo, int pageSize, String resourceType) {
        String uri = "/api/irds/v2/deviceResource/resources";
        String params = "{" +
                "\"pageNo\": " + pageNo + ", " +
                "\"pageSize\": " + pageSize + ", " +
                "\"resourceType\": \"" + resourceType + "\"" +
                "}";
        try {
            return resourceSearch(communityHik, params, uri);
        } catch (Exception e) {
            throw new RuntimeException("资源信息获取失败");
        }
    }

    /**
     * 查询资源列表v2
     *
     * @param communityHik 海康配置管理
     * @param name         名称，模糊搜索，最大长度32，若包含中文，最大长度指不超过按照指定编码的字节长度，即getBytes(“utf-8”).length
     * @param resourceType 资源类型
     * @param pageNo       当前页码
     * @param pageSize     分页大小
     * @return JSONArray
     */
    public static JSONObject resourcesByParams(CommunityHik communityHik, String name, String resourceType, Integer pageNo, Integer pageSize) {
        String url = "/api/irds/v2/resource/resourcesByParams";
        StringBuilder paramBuilder = new StringBuilder();
        paramBuilder.append("{");
        paramBuilder.append("\"pageNo\":\"").append(pageNo).append("\",");
        paramBuilder.append("\"pageSize\":\"").append(pageSize).append("\",");
        paramBuilder.append("\"resourceType\":\"").append(resourceType).append("\"");
        if (StringUtils.isNotBlank(name)) {
            paramBuilder.append(",");
            paramBuilder.append("\"name\":\"").append(name).append("\"");
        }
        paramBuilder.append("}");
        System.out.println(paramBuilder);
        try {
            return httpPost(communityHik, url, String.valueOf(paramBuilder));
        } catch (Exception e) {
            throw new RuntimeException("查询资源列表失败");
        }
    }

    /**
     * 查询梯控历史事件信息
     *
     * @param communityHik 海康配置管理
     * @param startTime    事件开始时间 采用ISO8601时间格式 与endTime配对使用，不能单独使用，时间范围最大不能超过3个月(90天)
     * @param endTime      事件结束时间
     * @param pageNo       当前页码，当前页码需大于0（pageNo>0）
     * @param pageSize     每页记录总数，每页记录展示的数目应大于0，小于等于1000（0<pageSize<=1000）
     * @param eventTypes   事件类型，10进制，多个事件类型用”,”隔开
     * @param sort         排序字段，支持personName、deviceName、eventTime，填写排序的字段名称
     * @param order        升/降序，指定排序字段是使用升序（asc）还是降序（desc）
     * @param deviceCodes  设备id组(最大支持500个设备集搜索,多个用”,”隔开)
     * @param personIds    人员id组(人员Id通过查询人员信息接口获取，最大支持1000个人员搜索,多个用”,”隔开)
     * @param cardNos      卡片组(最大支持1000张卡片搜索,多个用”,”隔开)
     * @return JSONArray
     */
    public static JSONArray elevatorSearch(CommunityHik communityHik, String startTime, String endTime, Integer pageNo, Integer pageSize, String eventTypes, String sort, String order, String deviceCodes, String personIds, String cardNos) {
        String url = "/api/ecs/v1/access_events/search";
        StringBuilder paramBuilder = new StringBuilder();
        paramBuilder.append("{");
        paramBuilder.append("\"startTime\":\"").append(startTime).append("\",");
        paramBuilder.append("\"endTime\":\"").append(endTime).append("\",");
        paramBuilder.append("\"pageNo\":").append(pageNo);
        paramBuilder.append("\"pageSize\":").append(pageSize);
        if (StringUtils.isNotBlank(eventTypes)) {
            paramBuilder.append("\"eventTypes\":\"").append(eventTypes).append("\",");
        }
        if (StringUtils.isNotBlank(sort)) {
            paramBuilder.append("\"sort\":\"").append(sort).append("\",");
        }
        if (StringUtils.isNotBlank(order)) {
            paramBuilder.append("\"order\":\"").append(order).append("\",");
        }
        if (StringUtils.isNotBlank(deviceCodes)) {
            paramBuilder.append("\"deviceCodes\":\"").append(deviceCodes).append("\",");
        }
        if (StringUtils.isNotBlank(personIds)) {
            paramBuilder.append("\"personIds\":\"").append(personIds).append("\",");
        }
        if (StringUtils.isNotBlank(cardNos)) {
            paramBuilder.append("\"cardNos\":\"").append(cardNos).append("\"");
        }
        paramBuilder.append("}");
        System.out.println(paramBuilder);
        try {
            return resourceSearch(communityHik, String.valueOf(paramBuilder), url);
        } catch (Exception e) {
            throw new RuntimeException("查询梯控历史事件失败");
        }
    }

    public static Integer elevatorSearchTotal(CommunityHik communityHik, String startTime, String endTime, Integer pageNo, Integer pageSize, String eventTypes, String sort, String order, String deviceCodes, String personIds, String cardNos) {
        String url = "/api/ecs/v1/access_events/search";
        StringBuilder paramBuilder = new StringBuilder();
        paramBuilder.append("{");
        paramBuilder.append("\"startTime\":\"").append(startTime).append("\",");
        paramBuilder.append("\"endTime\":\"").append(endTime).append("\",");
        paramBuilder.append("\"pageNo\":").append(pageNo);
        paramBuilder.append("\"pageSize\":").append(pageSize);
        if (StringUtils.isNotBlank(eventTypes)) {
            paramBuilder.append("\"eventTypes\":\"").append(eventTypes).append("\",");
        }
        if (StringUtils.isNotBlank(sort)) {
            paramBuilder.append("\"sort\":\"").append(sort).append("\",");
        }
        if (StringUtils.isNotBlank(order)) {
            paramBuilder.append("\"order\":\"").append(order).append("\",");
        }
        if (StringUtils.isNotBlank(deviceCodes)) {
            paramBuilder.append("\"deviceCodes\":\"").append(deviceCodes).append("\",");
        }
        if (StringUtils.isNotBlank(personIds)) {
            paramBuilder.append("\"personIds\":\"").append(personIds).append("\",");
        }
        if (StringUtils.isNotBlank(cardNos)) {
            paramBuilder.append("\"cardNos\":\"").append(cardNos).append("\"");
        }
        paramBuilder.append("}");
        System.out.println(paramBuilder);
        try {
            return resourceSearchTotal(communityHik, String.valueOf(paramBuilder), url);
        } catch (Exception e) {
            throw new RuntimeException("查询梯控历史事件失败");
        }
    }
}
