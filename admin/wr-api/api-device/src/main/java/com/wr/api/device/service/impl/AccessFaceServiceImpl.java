package com.wr.api.device.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;
import com.wr.api.device.entity.vo.AccessVO;
import com.wr.api.device.entity.vo.AccountVO;
import com.wr.api.device.mapper.AccessMapper;
import com.wr.api.device.mapper.EventAcsMapper;
import com.wr.api.device.mapper.TableMapper;
import com.wr.api.device.mapper.UserMapper;
import com.wr.api.device.service.AccessService;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.HealthyConstants;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.model.Identify;
import com.wr.common.customize.util.Base64File;
import com.wr.common.customize.util.IdentifyUtils;
import com.wr.common.customize.util.ShieldSyncApp;
import com.wr.common.customize.util.TableUtils;
import com.wr.remote.domain.SysFile;
import com.wr.remote.estate.log.UserLog;
import com.wr.remote.system.RemoteFileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * @Author: RainCity
 * @Date: 2021-07-20 17:39:02
 * @Desc: 人脸识别模式
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class AccessFaceServiceImpl implements AccessService {
    private static final Logger log = LoggerFactory.getLogger(AccessFaceServiceImpl.class);

    private final AccessMapper accessMapper;
    private final UserMapper userMapper;
    private final RemoteFileService remoteFileService;
    private final TableMapper tableMapper;
    private final EventAcsMapper eventAcsMapper;

    @Override
    public Boolean support(Integer authType,String authMsg) {

        return HealthyConstants.MODE_FACE.equals(authType);
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult execute(Identify identify) {
        log.info("********** 人脸识别模式==> *************");
        /*根据IP获取门禁信息*/
        AccessVO access = accessMapper.selectByDevIp(identify.getDevIp());
        if(null == access){
            log.error("未查询到相关设备：{}",identify.getDevIp());
            return IdentifyUtils.fail(identify, "未查询到相关设备");
        }
        log.info("设备信息：{}",access);
        /*recordLog(identify.getName(), "", "", "1", identify.getPicData(), access);
        return IdentifyUtils.greenResult(identify, "");*/
        ShieldSyncApp app = new ShieldSyncApp();
        ApiResponse apiResponse = app.checkHealthyByIdCardNo("",
                identify.getDeviceIndexCode(), HealthyConstants.CHECK_USR_NAME,
                HealthyConstants.CITY_CODE, "", identify.getAuthMsg(),
                HealthyConstants.SITE_ID, identify.getName());
        JSONObject data = IdentifyUtils.healthyResultPreHandler(apiResponse.getBody());
        if(null == data){
            return IdentifyUtils.fail(identify,"未查询到数据");
        }
        boolean dataFlag = data.getBoolean("flag");
        if(!dataFlag){
            return IdentifyUtils.fail(identify, data.getString("errMsg"));
        }
        JSONObject idCardNoData = data.getJSONObject("data");
        //被核验人姓名
        String name = idCardNoData.getString("name");
        //被核验人手机号码
        String userPhone = idCardNoData.getString("userPhone");
        //被核验人证件号码
        String idCardNo = idCardNoData.getString("idCardNo");
        //当前安康码颜色，1绿色，2黄色，3红色
        String healthLevel = idCardNoData.getString("healthLevel");

        log.info("idCardNoData==>idCardNo:{},healthLevel:{},name:{}",idCardNo,healthLevel,name);
        recordLog(name, idCardNo, userPhone, healthLevel, identify.getPicData(), access);
        //绿码
        if("1".equals(healthLevel)){
            identify.setName(name);
            return IdentifyUtils.greenResult(identify, idCardNo);
        }
        //黄码
        if("2".equals(healthLevel)){
            return IdentifyUtils.yellowResult(identify);
        }
        //红码
        if("3".equals(healthLevel)){
            return IdentifyUtils.redResult(identify);
        }
        return IdentifyUtils.fail(identify, "人脸识别错误");
    }

    private void recordLog(String name,String idCardNo,String userPhone,String healthLevel,String picData,AccessVO access){
        UserLog userLog = new UserLog();
        userLog.setCommunityId(access.getCommunityId());
        userLog.setCommunityName(access.getCommunityName());
        userLog.setUserPhone(userPhone);
        userLog.setUserName(name);
        userLog.setIdCard(idCardNo);
        String url = "";
        if(StringUtils.isNotEmpty(picData)){
            String prefix = "data:image/jpg;base64";
            MultipartFile multipartFile = Base64File.base64ToMultipart(prefix,picData);
            R<SysFile> fileResult = remoteFileService.upload(multipartFile);
            if (StringUtils.isNotNull(fileResult) && StringUtils.isNotNull(fileResult.getData())) {
                url = fileResult.getData().getUrl();
            }
        }
        userLog.setPicUrl(url);
        userLog.setDeviceName(access.getDeviceName());
        userLog.setIndexCode(access.getIndexCode());
        userLog.setIoType("1");
        AccountVO account = userMapper.getAccountByCodeNo(idCardNo);
        userLog.setUserType("0");
        userLog.setRoomName("");
        Optional.ofNullable(account).ifPresent(vo ->{
            userLog.setUserType(vo.getUserType());
            String roomName = vo.getBuildingName() + vo.getUnitName() + vo.getRoomName();
            userLog.setRoomName(roomName);
        });
        userLog.setHealthLevel(healthLevel);
        //检查表存不存在
        String tableName = TableUtils.getTableName(TableConstants.PREFIX_USER_LOG,access.getCommunityId());
        String newName = TableConstants.LOG_DB_NAME+"."+tableName;
        Integer num = tableMapper.existTable(tableName);
        if (num == 0) {
            tableMapper.createUserLogTable(newName);
        }
        userLog.setTableName(newName);
        eventAcsMapper.insertUserLog(userLog);
    }


}
