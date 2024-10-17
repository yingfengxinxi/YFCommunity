package com.wr.common.customize.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.beust.jcommander.internal.Maps;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.Client;
import com.hikvision.artemis.sdk.Request;
import com.hikvision.artemis.sdk.Response;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.hikvision.artemis.sdk.constant.Constants;
import com.hikvision.artemis.sdk.enums.Method;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.customize.constant.HikConstants;
import com.wr.common.customize.model.PersonInfo;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: RainCity
 * @Date: 2021-07-19 15:23:48
 * @Desc: 海康相关工具类
 */
public class HikUtils {
    private static final Logger log = LoggerFactory.getLogger(HikUtils.class);

    public static Date utcToDate(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        try {
            return sdf.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateToUtc(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return sdf.format(date);
    }

    /**
     * 初始化海康配置--测试用
     */
//    public static void initConfigTest(){
//        ArtemisConfig.host = HikConstants.HIK_HOST;
//        ArtemisConfig.appKey = HikConstants.AK;
//        ArtemisConfig.appSecret = HikConstants.SK;
//    }

    /**
     * 初始化海康配置
     *
     * @param ip   海康服务ip
     * @param port 海康服务端口
     * @param ak   海康服务密钥id
     * @param sk   海康服务密钥
     */
    public static void initConfig(String ip, String port, String ak, String sk) {
//        ArtemisConfig.host = ip + ":" + port;
//        ArtemisConfig.appKey = ak;
//        ArtemisConfig.appSecret = sk;
    }

    /**
     * 初始化请求path
     *
     * @param uri
     * @return
     */
    public static Map<String, String> setUpPath(String uri) {

        return new HashMap<String, String>(2) {
            {
                put("https://", HikConstants.PRE_PATH + uri);
            }
        };
    }

    public static Boolean success(JSONObject jsonObject) {
        if (null == jsonObject) {
            return false;
        }
        return "0".equals(jsonObject.getString("code"));
    }

    /**
     * 获取token
     *
     * @return access_token
     */
    public static String getAccessToken() {
//        String uri = "/api/v1/oauth/token";
//        Map<String, String> path = setUpPath(uri);
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, null, null, null,
//                ContentType.APPLICATION_JSON.getMimeType(), null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        if (success(jsonObject)) {
//            JSONObject data = jsonObject.getJSONObject("data");
//            if (null != data) {
//                return data.getString("access_token");
//            }
//        }
        return null;
    }

    /**
     * 分页获取设备
     *
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @param uri      请求路径
     * @return com.alibaba.fastjson2.JSONArray
     */
    private static JSONArray resourceSearch(int pageNo, int pageSize, String uri) {
//        Map<String, String> path = HikUtils.setUpPath(uri);
//        String params = "{" +
//                "\"pageNo\": " + pageNo + ", " +
//                "\"pageSize\": \"" + pageSize + "\"" +
//                "}";
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, params, null, null,
//                ContentType.APPLICATION_JSON.getMimeType(), null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        if (success(jsonObject)) {
//            JSONObject data = jsonObject.getJSONObject("data");
//            return data.getJSONArray("list");
//        }
        return new JSONArray();
    }

    /**
     * 获取设备在线状态
     *
     * @param uri       请求路径
     * @param indexCode 设备唯一标识
     * @return java.lang.Integer
     */
    private static Integer resourceOnline(String uri, String indexCode) {
//        Map<String, String> path = HikUtils.setUpPath(uri);
//        String params = "{" +
//                "\"indexCodes\": [\"" + indexCode + "\"]," +
//                "\"pageNo\": 1," +
//                "\"pageSize\": 1" +
//                "}";
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, params, null, null,
//                ContentType.APPLICATION_JSON.getMimeType(), null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        if (!success(jsonObject)) {
//            return -1;
//        }
//        JSONObject data = jsonObject.getJSONObject("data");
//        if (null == data) {
//            return -1;
//        }
//        JSONArray jsonArray = data.getJSONArray("list");
//        if (jsonArray.isEmpty()) {
//            return -1;
//        }
//        JSONObject acs = jsonArray.getJSONObject(0);
//        return acs.getInteger("online");
        return null;
    }

    /**
     * 查询门禁设备列表v2
     * 一卡通应用服务-->门禁接口-->门禁资源接口
     *
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @return com.alibaba.fastjson2.JSONArray
     */
    public static JSONArray acsDeviceSearch(int pageNo, int pageSize) {
        String uri = "/api/resource/v2/acsDevice/search";
        return resourceSearch(pageNo, pageSize, uri);
    }

    /**
     * 获取门禁设备在线状态
     * 一卡通应用服务-->门禁接口-->门禁网关接口
     *
     * @param indexCode 设备唯一编码
     * @return java.lang.Integer
     */
    public static Integer acsDeviceOnline(String indexCode) {
        String uri = "/api/nms/v1/online/acs_device/get";
        return resourceOnline(uri, indexCode);
    }

    /**
     * 获取门禁事件的图片
     * 一卡通应用服务-->门禁接口-->门禁功能接口
     *
     * @param svrIndexCode 存储接入服务（图片服务器唯一标识），获取picUri处会提供此参数
     * @param picUri       图片地址
     * @return
     */
    public static String acsEventPictures(String svrIndexCode, String picUri) {

        try {
            String uri = "/api/acs/v1/event/pictures";
            return getImageUrl(picUri, svrIndexCode, uri);
        } catch (Exception e) {
            log.error("the Artemis PostString Request is failed:{}", e.getMessage());
            return null;
        }

    }

    /**
     * 查询门禁点列表v2
     * 一卡通应用服务-->门禁接口-->门禁资源接口
     *
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @return com.alibaba.fastjson2.JSONArray
     */
    public static JSONArray doorSearch(int pageNo, int pageSize) {
        String uri = "/api/resource/v2/door/search";
        return resourceSearch(pageNo, pageSize, uri);
    }

    /**
     * 获取门禁点在线状态
     * 一卡通应用服务-->门禁接口-->门禁网关接口
     *
     * @param indexCode 设备唯一编码
     * @return java.lang.Integer
     */
    public static Integer doorOnline(String indexCode) {
        String uri = "/api/nms/v1/online/door/get";
        return resourceOnline(uri, indexCode);
    }

    /**
     * 门禁点反控--开门
     * 一卡通应用服务-->门禁接口-->门禁功能接口
     *
     * @param doorIndexCode 门禁点唯一标识
     * @return
     */
    public static JSONObject doControlOpenDoor(String doorIndexCode) {
//        String uri = "/api/acs/v1/door/doControl";
//        Map<String, String> path = HikUtils.setUpPath(uri);
//        String params = "{" +
//                "    \"doorIndexCodes\": [\"" + doorIndexCode + "\"]," +
//                "    \"controlType\": 2" +
//                "}";
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, params, null, null,
//                ContentType.APPLICATION_JSON.getMimeType(), null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        if (success(jsonObject)) {
//            JSONArray data = jsonObject.getJSONArray("data");
//            if (!data.isEmpty()) {
//                return data.getJSONObject(0);
//            }
//        }
        return null;
    }

    /**
     * 查询可视对讲设备列表v2
     * 一卡通应用服务-->可视对讲接口-->可视对讲资源接口
     *
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @return com.alibaba.fastjson2.JSONArray
     */
    public static JSONArray visDeviceSearch(int pageNo, int pageSize) {
        String uri = "/api/resource/v2/visDevice/search";
        return resourceSearch(pageNo, pageSize, uri);
    }

    /**
     * 获取可视对讲设备在线状态
     * 一卡通应用服务-->可视对讲接口-->可视对讲网关接口
     *
     * @param indexCode 设备唯一编码
     * @return java.lang.Integer
     */
    public static Integer visDeviceOnline(String indexCode) {
        String uri = "/api/nms/v1/online/vis_device/get";
        return resourceOnline(uri, indexCode);
    }

    /**
     * 查询可视对讲出入事件
     *
     * @param pageNo    页码
     * @param pageSize  每页多少条
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@link JSONArray}
     * @ver v1.0.0
     */
    public static JSONObject accessEvent(int pageNo, int pageSize, Date startTime, Date endTime) {
//        String uri = "/api/vis/v1/accessEvent/events";
//        Map<String, String> path = HikUtils.setUpPath(uri);
//        String params = "{" +
//                "    \"startTime\": \"" + dateToUtc(startTime) + "\"," +
//                "    \"endTime\": \"" + dateToUtc(endTime) + "\"," +
//                "    \"sort\": \"eventTime\"," +
//                "    \"order\": \"desc\"," +
//                "    \"pageNo\": \"" + pageNo + "\"," +
//                "    \"pageSize\": \"" + pageSize + "\"" +
//                "}";
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, params, null, null,
//                ContentType.APPLICATION_JSON.getMimeType(), null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        if (success(jsonObject)) {
//            return jsonObject.getJSONObject("data");
//        }
        return new JSONObject();
    }

    /**
     * 获取出入事件的图片
     * 一卡通应用服务-->可视对讲接口-->可视对讲功能接口
     *
     * @param svrIndexCode 存储接入服务（图片服务器唯一标识），获取picUri处会提供此参数
     * @param picUri       图片地址
     * @return {@link String}
     * @ver v1.0.0
     */
    public static String visEventPictures(String svrIndexCode, String picUri) {
        try {
            String uri = "/api/vis/v1/accessEvent/eventPictures";
            return getImageUrl(picUri, svrIndexCode, uri);
        } catch (Exception e) {
            log.error("the Artemis PostString Request is failed:{}", e.getMessage());
            return null;
        }

    }

    /**
     * 查询监控点列表v2
     * 视频业务-->视频资源
     *
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @return com.alibaba.fastjson2.JSONArray
     */
    public static JSONArray cameraSearch(int pageNo, int pageSize) {
        String uri = "/api/resource/v2/camera/search";
        return resourceSearch(pageNo, pageSize, uri);
    }

    /**
     * 获取监控点在线状态
     * 视频业务-->视频网关
     *
     * @param indexCode 设备唯一编码
     * @return java.lang.Integer
     */
    public static Integer cameraOnline(String indexCode) {
        String uri = "/api/nms/v1/online/camera/get";
        return resourceOnline(uri, indexCode);
    }

    /**
     * 获取监控点预览取流URLv2
     * 视频业务-视频能力
     *
     * @param cameraIndexCode 监控点唯一标识
     * @param protocol        取流协议 见 com.wr.common.customize.constant.ProtocolConstants
     * @return java.lang.String
     */
    public static String camerasPreviewURLs(String cameraIndexCode, String protocol) {
//        String uri = "/api/video/v2/cameras/previewURLs";
//        Map<String, String> path = HikUtils.setUpPath(uri);
//        String params = "{" +
//                "\"cameraIndexCode\": \"" + cameraIndexCode + "\"," +
//                "\"streamType\": 1," +
//                "\"protocol\": \"" + protocol + "\"" +
//                "}";
//        /*Map<String, String> header = new HashMap<>();
//        header.put("Cross-Origin-Embedder-Policy","require-corp");
//        header.put("Cross-Origin-Opener-Policy","same-origin");*/
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, params, null, null,
//                ContentType.APPLICATION_JSON.getMimeType(), null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        if (success(jsonObject)) {
//            JSONObject data = jsonObject.getJSONObject("data");
//            if (null == data) {
//                return null;
//            }
//            return data.getString("url");
//        }
        return null;
    }

    /**
     * 获取视频事件的图片
     * 视频业务-->视频功能
     *
     * @param svrIndexCode 存储接入服务（图片服务器唯一标识），获取picUri处会提供此参数
     * @param picUri       图片地址
     * @return
     */
    public static String videoEventPictures(String svrIndexCode, String picUri) {
//        if (StringUtils.isEmpty(svrIndexCode)) {
//            log.error("图片服务编号为空");
//            return "";
//        }
//        if (StringUtils.isEmpty(picUri)) {
//            log.error("图片相对地址为空");
//            return "";
//        }
//        int index = picUri.indexOf("/pic?");
//        if (index == -1) {
//            log.error("检测图片{}错误", picUri);
//            return "";
//        }
//        String netProtocol = "https";
//        String uri = "/api/video/v1/events/picture";
//        Map<String, String> path = HikUtils.setUpPath(uri);
//        String params = "{" +
//                "\"netProtocol\": \"" + netProtocol + "\"," +
//                "\"svrIndexCode\": \"" + svrIndexCode + "\"," +
//                "\"picUri\": \"" + picUri.substring(index) + "\"" +
//                "}";
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, params, null, null,
//                ContentType.APPLICATION_JSON.getMimeType(), null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        if (!success(jsonObject)) {
//            return null;
//        }
//        JSONObject data = jsonObject.getJSONObject("data");
//        if (null == data) {
//            return null;
//        }
        //return data.getString("picUrl");
        return null;
    }

    /**
     * 获取停车库列表
     * 车辆管控-->车辆及车库信息接口
     *
     * @return JSONArray
     */
    public static JSONArray parkList() {
//        String uri = "/api/resource/v1/park/parkList";
//        Map<String, String> path = HikUtils.setUpPath(uri);
//        String params = "{}";
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, params, null, null,
//                ContentType.APPLICATION_JSON.getMimeType(), null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        if (success(jsonObject)) {
//            return jsonObject.getJSONArray("data");
//        }
        return new JSONArray();
    }

    /**
     * 获取出入口列表
     * 车辆管控-->车辆及车库信息接口
     *
     * @param parkIndexCode 停车场唯一标识
     * @return JSONArray
     */
    public static JSONArray entranceList(String parkIndexCode) {
//        String uri = "/api/resource/v1/entrance/entranceList";
//        Map<String, String> path = HikUtils.setUpPath(uri);
//        String params = "{\"parkIndexCodes\": \"" + parkIndexCode + "\"}";
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, params, null, null,
//                ContentType.APPLICATION_JSON.getMimeType(), null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        if (success(jsonObject)) {
//            return jsonObject.getJSONArray("data");
//        }
        return new JSONArray();
    }

    /**
     * 获取车道列表
     * 车辆管控-->车辆及车库信息接口
     *
     * @param entranceIndexCode 出入口唯一标识
     * @return JSONArray
     */
    public static JSONArray roadwayList(String entranceIndexCode) {
//        String uri = "/api/resource/v1/roadway/roadwayList";
//        Map<String, String> path = HikUtils.setUpPath(uri);
//        String params = "{\"entranceIndexCodes\": \"" + entranceIndexCode + "\"}";
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, params, null, null,
//                ContentType.APPLICATION_JSON.getMimeType(), null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        if (success(jsonObject)) {
//            return jsonObject.getJSONArray("data");
//        }
        return new JSONArray();
    }

    /**
     * 车辆管控-->停车场功能接口
     * 根据车道编码反控道闸
     *
     * @param roadwaySysCode 车道唯一标识
     * @param command        控闸命令（0：关闸 1：开闸 3：常开）
     * @return java.lang.Boolean
     */
    public static Boolean roadwayDeviceControl(String roadwaySysCode, int command) {
//        log.info("roadwaySysCode:{},command:{}", roadwaySysCode, command);
//        String uri = "/api/pms/v1/deviceControl";
//        Map<String, String> path = HikUtils.setUpPath(uri);
//        String params = "{" +
//                "\"roadwaySyscode\": \"" + roadwaySysCode + "\"," +
//                "\"command\": \"" + command + "\"" +
//                "}";
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, params, null, null,
//                ContentType.APPLICATION_JSON.getMimeType(), null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        return success(jsonObject);
        return null;
    }

    /**
     * 事件服务接口
     * 查询事件订阅信息
     *
     * @return com.alibaba.fastjson2.JSONArray
     */
    public static JSONArray eventSubscriptionView() {
//        String uri = "/api/eventService/v1/eventSubscriptionView";
//        Map<String, String> path = HikUtils.setUpPath(uri);
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, null, null, null,
//                ContentType.APPLICATION_JSON.getMimeType(), null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        if (success(jsonObject)) {
//            JSONObject data = jsonObject.getJSONObject("data");
//            if (null != data) {
//                return data.getJSONArray("detail");
//            }
//            return new JSONArray();
//        }
        return new JSONArray();
    }

    /**
     * 事件服务接口
     * 按事件类型订阅事件
     *
     * @param eventTypes 事件类型
     * @param eventDest  指定事件接收的地址，
     * @param subType    订阅类型，0-订阅原始事件，1-联动事件，2-原始事件和联动事件，不填使用默认值0
     * @return java.lang.Boolean
     */
    public static Boolean eventSubscription(Integer[] eventTypes, String eventDest, int subType){
//        String types = Arrays.toString(eventTypes);
//        log.info("eventTypes:{},eventDest:{}", types, eventDest);
//        String uri = "/api/eventService/v1/eventSubscriptionByEventTypes";
//        Map<String, String> path = HikUtils.setUpPath(uri);
//        String params = "{" +
//                "\"eventTypes\": " + types + "," +
//                "\"eventDest\": \"" + eventDest + "\"," +
//                "\"subType\": " + subType + "" +
//                "}";
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, params, null, null, null, null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        return success(jsonObject);
        return null;
    }

    /**
     * 事件服务接口
     * 按事件类型取消订阅事件
     *
     * @param eventTypes 事件类型
     * @return java.lang.Boolean
     */
    public static Boolean eventUnSubscription(Integer[] eventTypes) {
//        String types = Arrays.toString(eventTypes);
//        log.info("eventTypes:{}", types);
//        String uri = "/api/eventService/v1/eventUnSubscriptionByEventTypes";
//        Map<String, String> path = HikUtils.setUpPath(uri);
//        String params = "{" +
//                "\"eventTypes\": " + types + "" +
//                "}";
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, params, null, null,
//                ContentType.APPLICATION_JSON.getMimeType(), null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        return success(jsonObject);
        return null;
    }

    /**
     * 图片转 base64
     *
     * @param imgUrl 图片文件地址
     * @return java.lang.String
     * @Author: RainCity
     * @Date: 2022/10/24 12:09
     */
    public static String imageToBase64Str(String imgUrl) {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            URL url = new URL(imgUrl);
            byte[] by = new byte[1024];
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            is = connection.getInputStream();
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // 加密
            /*BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(data.toByteArray()).replaceAll("[\\s*\t\n\r]", "");*/
            return Base64.encodeBase64String(data.toByteArray()).replaceAll("[\\s*\t\n\r]", "");
        } catch (IOException e) {
            log.error("转换失败：{}", e.getMessage());
            return null;
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
                data.close();
            } catch (IOException e) {
                log.error("流关闭失败：{}", e.getMessage());
            }
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
    public static JSONArray deviceResource(int pageNo, int pageSize, String resourceType) {
//        String uri = "/api/irds/v2/deviceResource/resources";
//        Map<String, String> path = HikUtils.setUpPath(uri);
//        String params = "{" +
//                "\"pageNo\": " + pageNo + ", " +
//                "\"pageSize\": " + pageSize + ", " +
//                "\"resourceType\": \"" + resourceType + "\"" +
//                "}";
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, params, null, null,
//                ContentType.APPLICATION_JSON.getMimeType(), null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        if (success(jsonObject)) {
//            JSONObject data = jsonObject.getJSONObject("data");
//            if (null != data) {
//                return data.getJSONArray("list");
//            }
//            return new JSONArray();
//        }
        return new JSONArray();
    }

    /**
     * 根据身份证查询人员信息
     *
     * @param certificateNo 身份证
     * @return
     */
    public static JSONObject personInfo(String certificateNo) {
//        String uri = "/api/resource/v1/person/condition/personInfo";
//        Map<String, String> path = setUpPath(uri);
//        String params = "{" +
//                "\"paramName\": \"certificateNo\"," +
//                "\"paramValue\": [\"" + certificateNo + "\"]" +
//                "}";
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, params, null, null, ContentType.APPLICATION_JSON.getMimeType(), null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        if (success(jsonObject)) {
//            JSONObject data = jsonObject.getJSONObject("data");
//            if (null != data) {
//                JSONArray list = data.getJSONArray("list");
//                if (list.isEmpty()) {
//                    return null;
//                }
//                return list.getJSONObject(0);
//            }
//        }
        return null;
    }

    /**
     * 人员信息接口-->人员图片-->提取人员图片
     *
     * @param picUri          图片相对URI
     * @param serverIndexCode 图片服务器唯一标识
     * @return
     */
    public static String personPicture(String picUri, String serverIndexCode) {
        try {
            String uri = "/api/resource/v1/person/picture";
            return getImageUrl(picUri, serverIndexCode, uri);
        } catch (Exception ex) {
            log.error("the Artemis PostString Request is failed:{}", ex.getMessage());
            return null;
        }
    }

    /**
     * 根据人员与设备通道下载 -> 下发人脸至设备第一步
     * 创建下载任务_根据人员与设备通道指定下载
     * 一个下载任务最大支持100个设备的卡权限下载或者100个通道的人脸。
     *
     * @param taskType 下载任务类型 1：卡片 4：人脸
     * @return taskId
     */
    public static String createTask(int taskType) {
//        log.info("创建任务 taskType==>{}", taskType);
//        String uri = "/api/acps/v1/authDownload/task/addition";
//        Map<String, String> path = setUpPath(uri);
//        String params = "{\"taskType\": " + taskType + "}";
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, params, null, null, ContentType.APPLICATION_JSON.getMimeType(), null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        if (success(jsonObject)) {
//            JSONObject data = jsonObject.getJSONObject("data");
//            if (null != data) {
//                return data.getString("taskId");
//            }
//        }
        return null;
    }

    /**
     * 下发人脸至设备第二步
     * 下载任务中添加数据_根据人员与设备通道指定下载
     *
     * @param taskId            下载任务唯一标识 createTask()获取
     * @param resourceIndexCode 资源的唯一标识集
     * @param personInfos       人员信息集
     * @return
     */
    public static Boolean addData(String taskId, List<String> resourceIndexCode, List<PersonInfo> personInfos) {
//        log.info("添加数据 taskId==>{}", taskId);
//        String uri = "/api/acps/v1/authDownload/data/addition";
//        Map<String, String> path = setUpPath(uri);
//        String params = "{" +
//                "\"taskId\":\"" + taskId + "\"," +
//                "\"resourceInfos\":RESOURCE_INFOS," +
//                "\"personInfos\":PERSON_INFOS" +
//                "}";
//        StringBuilder ris = new StringBuilder();
//        for (String indexCode : resourceIndexCode) {
//            ris.append("{")
//                    .append("\"resourceIndexCode\":\"").append(indexCode).append("\",")
//                    .append("\"resourceType\":\"acsDevice\",")
//                    .append("\"channelNos\": [1]")
//                    .append("},");
//        }
//        String resourceInfos = HikConstants.SQUARE_BRACKET_LEFT + ris.substring(0, ris.length() - 1) + HikConstants.SQUARE_BRACKET_RIGHT;
//        params = params.replace("RESOURCE_INFOS", resourceInfos);
//
//        StringBuilder pis = new StringBuilder();
//        for (PersonInfo personInfo : personInfos) {
//            String faceId = "face" + personInfo.getPersonId();
//            pis.append("{")
//                    .append("\"personId\":\"").append(personInfo.getPersonId()).append("\",")
//                    .append("\"operatorType\":1,")
//                    .append("\"personType\":2,")
//                    .append("\"name\":\"").append(personInfo.getPersonName()).append("\",")
//                    .append("\"cards\":[{")
//                    .append("\"card\":\"").append(personInfo.getCertificateNo()).append("\",")
//                    .append("\"status\":0,")
//                    .append("\"cardType\":1").append("}],")
//                    .append("\"face\":{")
//                    .append("\"data\":{\"")
//                    .append(faceId).append("\":\"").append(personInfo.getFaceUrl()).append("\"")
//                    .append("}}},");
//        }
//        String personDatas = HikConstants.SQUARE_BRACKET_LEFT + pis.substring(0, pis.length() - 1) + HikConstants.SQUARE_BRACKET_RIGHT;
//        params = params.replace("PERSON_INFOS", personDatas);
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, params, null, null, ContentType.APPLICATION_JSON.getMimeType(), null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        return success(jsonObject);
        return null;
    }

    /**
     * 下发人脸至设备第三步
     * 开始下载任务
     *
     * @param taskId 下载任务唯一标识
     * @return
     */
    public static Boolean startTask(String taskId) {
//        log.info("开始下载任务 taskId==>{}",taskId);
//        String uri = "/api/acps/v1/authDownload/task/start";
//        Map<String, String> path =setUpPath(uri);
//        String params = "{\"taskId\": \""+taskId+"\"}";
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, params, null, null, ContentType.APPLICATION_JSON.getMimeType(), null);
        //JSONObject jsonObject = JsonUtil.parseObject(result);
        //return success(jsonObject);
        return null;
    }

    /**
     * 下发人脸至设备第四步
     * 查询下载任务进度
     *
     * @param taskId 下载任务唯一标识
     * @return
     */
    public static JSONObject progressTask(String taskId) {
//        log.info("任务进度查询 taskId==>{}",taskId);
//        String uri = "/api/acps/v1/authDownload/task/progress";
//        Map<String, String> path =setUpPath(uri);
//        String params = "{\"taskId\": \""+taskId+"\"}";
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, params, null, null, ContentType.APPLICATION_JSON.getMimeType(), null);
//        JSONObject jsonObject = JsonUtil.parseObject(result);
//        if(success(jsonObject)){
//            return jsonObject.getJSONObject("data");
//        }
        return null;
    }

    private static String getImageUrl(String pic, String svrIndexCode, String uri) throws Exception {
//        if(StringUtils.isEmpty(pic)){
//            log.error("图片相对地址为空");
//            return "";
//        }
//        if(StringUtils.isEmpty(svrIndexCode)){
//            log.error("图片服务编号为空");
//            return "";
//        }
//        int index = pic.indexOf("/pic?");
//        if(index == -1){
//            log.error("检测图片{}错误",pic);
//            return "";
//        }
//        Map<String, String> path = HikUtils.setUpPath(uri);
//        String params = "{" +
//                "    \"serverIndexCode\": \""+svrIndexCode+"\"," +
//                "    \"svrIndexCode\": \""+svrIndexCode+"\"," +
//                "    \"picUri\": \""+pic.substring(index)+"\"" +
//                "}";
//        String httpSchema = (String)path.keySet().toArray()[0];
//        if(null == httpSchema || StringUtils.isEmpty(httpSchema)){
//            log.error("http和https参数错误httpSchema: {}", httpSchema);
//            return null;
//        }
//        Map<String, String> headers = Maps.newHashMap();
//        headers.put("Accept", "*/*");
//        headers.put("Content-Type", "application/json");
//
//        Request request = new Request(Method.POST_STRING, httpSchema + ArtemisConfig.host, (String)path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, Constants.DEFAULT_TIMEOUT);
//        request.setHeaders(headers);
//        request.setStringBody(params);
//        Response response = Client.execute(request);
//        return response.getHeader("Location");
        return null;
    }


}