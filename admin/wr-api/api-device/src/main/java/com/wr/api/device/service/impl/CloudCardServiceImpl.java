package com.wr.api.device.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;
import com.wr.api.device.entity.vo.AccountVO;
import com.wr.api.device.mapper.*;
import com.wr.api.device.service.CloudService;
import com.wr.common.customize.constant.CloudConstants;
import com.wr.common.customize.constant.HealthyConstants;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.*;
import com.wr.remote.aiot.CloudAccess;
import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.estate.log.UserLog;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: RainCity
 * @Date: 2021-09-24 15:58:46
 * @Desc: 云+ 社保卡进门
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class CloudCardServiceImpl implements CloudService {
    private static final Logger log = LoggerFactory.getLogger(CloudCardServiceImpl.class);

    private final CloudMapper cloudMapper;
    private final CommunityMapper communityMapper;
    private final UserMapper userMapper;
    private final TableMapper tableMapper;
    private final EventAcsMapper eventAcsMapper;


    @Override
    public Boolean support(String card) {

        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public String execute(JSONObject cloudData) {
        log.info("********** 社保卡进门==> *************");
        //读头,0=进（A），1出(B)
        String reader = cloudData.getString("Reader");
        String serial = cloudData.getString("Serial");
        String ip = cloudData.getString("IP");
        String card = cloudData.getString("Card");
        String hex = CloudUtils.strToHex(card);
        boolean result = CloudUtils.dataCheck(hex);
        if(!result){
            log.error("识别错误");
            return String.format(CloudConstants.RESPONSE_FAILED, "识别错误");
        }
        //标志位
        String flagByte = hex.substring(16,18);
        JSONObject data;
        if(CloudConstants.SOCIAL_CARD.equals(flagByte)){
            String idCardNo = CloudUtils.decodeIdCardNo(hex);
            String userName = CloudUtils.decodeUserName(hex);
            log.info("解析后的身份证号：{}，姓名：{}",idCardNo,userName);
            if(StringUtils.isEmpty(idCardNo) || StringUtils.isEmpty(userName)){
                return String.format(CloudConstants.RESPONSE_FAILED, "识别错误");
            }
            ShieldSyncApp app = new ShieldSyncApp();
            ApiResponse apiResponse = app.checkHealthyByIdCardNo("", serial, HealthyConstants.CHECK_USR_NAME,
                    HealthyConstants.CITY_CODE, "", idCardNo,
                    HealthyConstants.SITE_ID, userName);
            byte[] body = apiResponse.getBody();
            data = IdentifyUtils.healthyResultPreHandler(body);
        }else if(CloudConstants.SOCIAL_ELC_CARD.equals(flagByte)){
            int idIndex = hex.indexOf("FF04");
            if (idIndex == -1) {
                return String.format(CloudConstants.RESPONSE_FAILED, "识别错误");
            }
            String elcHex = hex.substring(idIndex+4,hex.length()-4);
            data = CloudUtils.elcCardInfo(CloudUtils.hexToString(elcHex),serial);
        }else{
            return String.format(CloudConstants.RESPONSE_FAILED, "不支持的识别模式");
        }
        if(null == data){
            return String.format(CloudConstants.RESPONSE_FAILED, "未查询到数据");
        }
        Boolean dataFlag = data.getBoolean("flag");
        if(null == dataFlag || !dataFlag){
            return String.format(CloudConstants.RESPONSE_FAILED, data.getString("errMsg"));
        }
        JSONObject idCardData = data.getJSONObject("data");
        //被核验人姓名
        String name = idCardData.getString("name");
        //被核验人证件号码
        String idCardNo = idCardData.getString("idCardNo");
        //当前安康码颜色，1绿色，2黄色，3红色
        String healthLevel = idCardData.getString("healthLevel");
        log.info("idCardNoData==>idCardNo:{},healthLevel:{},name:{}",idCardNo,healthLevel,name);

        //根据serial,reader查询社保卡设备信息及绑定的门禁点信息
        CloudAccess cloudDto = cloudMapper.selectByDevIp(ip);
        String deviceName = "";
        String doorIndexCode = "";
        Long communityId = 0L;
        if (null != cloudDto) {
            deviceName = cloudDto.getAccessName();
            if("0".equals(reader)){
                doorIndexCode = cloudMapper.selectDoorIndex(cloudDto.getReaderIn());
            }else {
                doorIndexCode = cloudMapper.selectDoorIndex(cloudDto.getReaderOut());
            }
            communityId = cloudDto.getCommunityId();
        }
        //门禁点未设置
        if(StringUtils.isEmpty(doorIndexCode)){
            log.error("未查询到设备所绑定门禁点信息===>");
            return String.format(CloudConstants.RESPONSE_FAILED, "未查询到设备所绑定门禁点信息");
        }
        //查询小区信息
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if(null == hik){
            log.error("请检查小区海康配置后重试===>");
            return String.format(CloudConstants.RESPONSE_FAILED, "海康服务未配置");
        }

        recordLog(name, idCardNo, healthLevel, deviceName, communityId);
        //绿码
        if("1".equals(healthLevel)){
            HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
            //调开门接口
            JSONObject conRes = HikUtils.doControlOpenDoor(doorIndexCode);
            if(null == conRes){
                log.error("开门失败，接口调用失败===>");
                return String.format(CloudConstants.RESPONSE_FAILED, "开门失败");
            }
            if(0 != conRes.getInteger("controlResultCode")){
                String msg = conRes.getString("controlResultDesc");
                log.error("开门失败===>{}",msg);
                return String.format(CloudConstants.RESPONSE_FAILED, msg);
            }
            return String.format(CloudConstants.RESPONSE_SUCCESS, "绿码，请通行");
        }
        //黄码
        if("2".equals(healthLevel)){
            return String.format(CloudConstants.RESPONSE_FAILED, "黄码，请复核");
        }
        //红码
        if("3".equals(healthLevel)){
            return String.format(CloudConstants.RESPONSE_FAILED, "红码，不准通行");
        }
        return String.format(CloudConstants.RESPONSE_FAILED, "社保卡识别错误");
    }

    private void recordLog(String name,String idCardNo,String healthLevel,String deviceName,Long communityId){
        AccountVO account = userMapper.getAccountByCodeNo(idCardNo);
        if(null == account){
            log.warn("未查询到用户信息：{}",idCardNo);
            return;
        }
        UserLog userLog = new UserLog();
        userLog.setCommunityId(communityId);
        userLog.setUserName(name);
        userLog.setIdCard(idCardNo);
        userLog.setHealthLevel(healthLevel);
        userLog.setIoType("5");
        userLog.setDeviceType("1");
        userLog.setDeviceName(deviceName);
        userLog.setUserType(account.getUserType());
        userLog.setCommunityName(account.getCommunityName());
        String roomName = account.getBuildingName() + account.getUnitName() + account.getRoomName();
        userLog.setRoomName(roomName);
        //检查表存不存在
        String tableName = TableUtils.getTableName(TableConstants.PREFIX_USER_LOG,communityId);
        String newName = TableConstants.LOG_DB_NAME+"."+tableName;
        Integer num = tableMapper.existTable(tableName);
        if (num == 0) {
            tableMapper.createUserLogTable(newName);
        }
        userLog.setTableName(newName);
        eventAcsMapper.insertUserLog(userLog);
    }

}
