package com.wr.api.device.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wr.api.device.entity.vo.AccessVO;
import com.wr.api.device.entity.vo.AccountVO;
import com.wr.api.device.mapper.AccessMapper;
import com.wr.api.device.mapper.EventAcsMapper;
import com.wr.api.device.mapper.TableMapper;
import com.wr.api.device.mapper.UserMapper;
import com.wr.api.device.service.AcsOpenService;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.JsonUtil;
import com.wr.common.customize.util.TableUtils;
import com.wr.remote.estate.log.UserLog;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @Author: RainCity
 * @Date: 2022-02-11 19:02:34
 * @Desc: 门机合法卡比对事件数据记录
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AcsOpenServiceImpl implements AcsOpenService {
    private static final Logger log = LoggerFactory.getLogger(AcsOpenServiceImpl.class);

    private final AccessMapper accessMapper;
    private final UserMapper userMapper;
    private final TableMapper tableMapper;
    private final EventAcsMapper eventAcsMapper;

    @Override
    @Transactional(readOnly = false)
    public void acsDeviceOpen(String req) {
        JSONObject reqJo = JsonUtil.parseObject(req);
        if(null == reqJo){
            log.warn("请求参数为空，不记录数据");
            return;
        }
        //事件消息
        JSONObject params = reqJo.getJSONObject("params");
        if(null == params){
            log.warn("请求参数为空，不记录数据");
            return;
        }
        JSONArray events = params.getJSONArray("events");
        if(events.isEmpty()){
            log.warn("事件消息为空，不记录数据");
            return;
        }
        List<UserLog> userLogs = Lists.newArrayList();
        for(int i=0; i < events.size(); i++){
            int eventType = events.getJSONObject(i).getInteger("eventType");
            if(198914 != eventType){
                log.info("不记录的事件：{}",eventType);
                return;
            }
            UserLog userLog = new UserLog();
            userLog.setIoType("1");
            userLog.setHealthLevel("0");

            String srcIndex = events.getJSONObject(i).getString("srcIndex");
            userLog.setIndexCode(srcIndex);
            userLog.setDeviceName(events.getJSONObject(i).getString("srcName"));
            String srcType = events.getJSONObject(i).getString("srcType");
            AccessVO access = accessMapper.selectByIndexCode(srcIndex);
            if("door".equals(srcType)){
                access = accessMapper.selectDoorByIndexCode(srcIndex);
            }
            if(null == access){
                log.error("未查询到相关设备：{}",srcIndex);
                return;
            }
            userLog.setCommunityId(access.getCommunityId());
            userLog.setCommunityName(access.getCommunityName());
            //事件详情
            JSONObject data = events.getJSONObject(i).getJSONObject("data");
            String pictureUrl = data.getString("ExtEventPictureURL");
            if(StringUtils.isNotEmpty(pictureUrl)){
                userLog.setPicUrl(pictureUrl);
            }
            JSONObject cardInfo = data.getJSONObject("ExtEventIdentityCardInfo");
            if(null != cardInfo){
                String idNum = cardInfo.getString("IdNum");
                AccountVO account = userMapper.getAccountByCodeNo(idNum);
                userLog.setIdCard(idNum);
                userLog.setUserName("");
                userLog.setUserType("0");
                userLog.setRoomName("");
                Optional.ofNullable(account).ifPresent(vo ->{
                    userLog.setUserName(vo.getUserName());
                    userLog.setUserPhone(vo.getUserPhone());
                    userLog.setUserType(vo.getUserType());
                    String roomName = vo.getBuildingName() + vo.getUnitName() + vo.getRoomName();
                    userLog.setRoomName(roomName);
                });
                userLogs.add(userLog);
            }
            String userTable = TableUtils.getTableName(TableConstants.PREFIX_USER_LOG,access.getCommunityId());
            if(!userLogs.isEmpty()){
                int exist = tableMapper.existTable(userTable);
                String tableName = TableConstants.LOG_DB_NAME+"."+userTable;
                if(exist !=1){
                    tableMapper.createUserLogTable(tableName);
                }
                eventAcsMapper.batchInsertUserLog(tableName,userLogs);
            }
        }
    }
}
