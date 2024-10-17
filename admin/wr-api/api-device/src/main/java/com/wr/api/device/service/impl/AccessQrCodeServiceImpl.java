package com.wr.api.device.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;
import com.wr.api.device.entity.vo.AccessVO;
import com.wr.api.device.entity.vo.AccountVO;
import com.wr.api.device.mapper.AccessMapper;
import com.wr.api.device.mapper.TableMapper;
import com.wr.api.device.mapper.UserMapper;
import com.wr.api.device.mapper.VisitLogMapper;
import com.wr.api.device.service.AccessService;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.HealthyConstants;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.model.Identify;
import com.wr.common.customize.util.IdentifyUtils;
import com.wr.common.customize.util.JsonUtil;
import com.wr.common.customize.util.ShieldSyncApp;
import com.wr.common.customize.util.TableUtils;
import com.wr.remote.estate.log.VisitLog;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: RainCity
 * @Date: 2021-07-20 21:31:38
 * @Desc: 自定义二维码模式
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class AccessQrCodeServiceImpl implements AccessService {
    private static final Logger log = LoggerFactory.getLogger(AccessQrCodeServiceImpl.class);

    private final AccessMapper accessMapper;
    private final UserMapper userMapper;
    private final TableMapper tableMapper;
    private final VisitLogMapper visitLogMapper;

    @Override
    public Boolean support(Integer authType, String authMsg) {
        return HealthyConstants.MODE_HEALTHY.equals(authType)
                && !authMsg.startsWith(HealthyConstants.HEALTH_CODE_URL);
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult execute(Identify identify) {
        log.info("********** 二维码识别模式==> *************");
        /*根据IP获取门禁信息*/
        AccessVO access = accessMapper.selectByDevIp(identify.getDevIp());
        if(null == access){
            log.error("未查询到相关设备：{}",identify.getDevIp());
            return IdentifyUtils.fail(identify, "未查询到相关设备");
        }
        String authMsg = identify.getAuthMsg();
        JSONObject idName = JsonUtil.parseObject(authMsg);
        if(null == idName){
            return IdentifyUtils.fail(identify, "未识别人员信息");
        }
        String idCardNo = idName.getString("idCardNo");
        String userName = idName.getString("userName");
        String userPhone = idName.getString("userPhone");
        String ownerPhone = idName.getString("ownerPhone");
        Long roomId = idName.getLong("roomId");
        /*根据IP获取门禁信息*/
        ShieldSyncApp app = new ShieldSyncApp();
        ApiResponse apiResponse = app.checkHealthyByIdCardNo("", identify.getDeviceIndexCode(),
                HealthyConstants.CHECK_USR_NAME, HealthyConstants.CITY_CODE, "", idCardNo,
                HealthyConstants.SITE_ID, userName);
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
        //当前安康码颜色，1绿色，2黄色，3红色
        String healthLevel = idCardNoData.getString("healthLevel");
        log.info("idCardNoData==>idCardNo:{},healthLevel:{},name:{}",idCardNo,healthLevel,name);

        VisitLog visitLog = new VisitLog();
        visitLog.setUserName(userName);
        visitLog.setIdCard(idCardNo);
        visitLog.setUserPhone(userPhone);
        visitLog.setHealthLevel(healthLevel);
        AccountVO account = userMapper.getAccountByPhone(ownerPhone);
        if(null == account){
            return IdentifyUtils.fail(identify,"未查询到被访人数据");
        }
        visitLog.setOwnerName(account.getUserName());
        visitLog.setOwnerPhone(ownerPhone);
        visitLog.setCommunityName(account.getCommunityName());
        String roomName = account.getBuildingName() + account.getUnitName() + account.getRoomName();
        visitLog.setRoomName(roomName);
        log.info("设备信息:{}",access);
        visitLog.setDeviceName(access.getDeviceName());
        visitLog.setIndexCode(access.getIndexCode());
        String inoutType = access.getInoutType();
        //检查表存不存在
        String tableName = TableUtils.getTableName(TableConstants.PREFIX_VISITOR_LOG,access.getCommunityId());
        String newName = TableConstants.LOG_DB_NAME+"."+tableName;
        Integer num = tableMapper.existTable(tableName);
        if (num == 0) {
            tableMapper.createUserLogTable(newName);
        }
        visitLog.setCommunityId(access.getCommunityId());
        visitLog.setTableName(newName);
        //进
        if("0".equals(inoutType)){
            visitLogMapper.insertVisitLog(visitLog);
        }else {
            VisitLog temp = visitLogMapper.getLastTimeDate(newName,idCardNo);
            if(null == temp){
                visitLogMapper.insertVisitLog(visitLog);
            }else {
                visitLogMapper.updateVisitLogOutTime(newName,temp.getLogId());
            }
        }
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
        return IdentifyUtils.fail(identify, "二维码识别错误");
    }
    
}
