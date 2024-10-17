package com.wr.estate.service.impl.log;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wr.common.core.constant.HttpStatus;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.util.HikUtils;
import com.wr.estate.mapper.log.DoorMapper;
import com.wr.estate.service.log.DoorService;
import com.wr.remote.estate.door.VisEventAccess;
import com.wr.common.customize.util.CommunityHik;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2022/10/13 19:49
 * @Version 1.5
 */

@Service
public class DoorServiceImpI implements DoorService {
    @Autowired
    private DoorMapper doorMapper;

    /**
     * 查出单元门禁设备出入记录列表
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public TableDataInfo getDataTable(VisEventAccess visEvent) {
        //初始化分页数据
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(Lists.newArrayList());
        rspData.setTotal(0);
        if(null == visEvent.getCommunityId() || 0 == visEvent.getCommunityId()){
            return rspData;
        }

        if(null != visEvent.getDateRange()){
            visEvent.setStartTime(visEvent.getDateRange().get(0));
            visEvent.setEndTime(visEvent.getDateRange().get(1));
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //获取时间查询条件
            visEvent.setStartTime(changeStartTime(sdf, visEvent.getBeginDate()));
            visEvent.setEndTime(changeEndTime(sdf, visEvent.getEndDate()));
        }

        //查询小区--> 获取海康配置
        CommunityHik hik = doorMapper.selectCommunityHik(visEvent.getCommunityId());

        if(null == hik){
            rspData.setMsg("请检查小区海康配置后重试！");
            return rspData;
        }
        //初始化海康设备
        HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
        //查出设备相关数据
        JSONObject data = HikUtils.accessEvent(visEvent.getPageNum(), visEvent.getPageSize(),visEvent.getStartTime(),visEvent.getEndTime());
        //如果有数据，对数据中的设备信息进行处理
        rspData.setRows(data.getJSONArray("list"));
        rspData.setTotal(data.getLongValue("total"));
        return rspData;
    }

    private static Date changeStartTime(SimpleDateFormat sdf, String beginDate){
        Calendar cal;
        try {
            cal = Calendar.getInstance();
            if(StringUtils.isEmpty(beginDate)){
                cal.add(Calendar.DATE,-7);
            }else {
                cal.setTime(sdf.parse(beginDate));
            }
            cal.set(Calendar.HOUR_OF_DAY,0);
            cal.set(Calendar.MINUTE,0);
            cal.set(Calendar.SECOND,0);
            return cal.getTime();
        } catch (ParseException e) {
            cal = Calendar.getInstance();
            cal.add(Calendar.DATE,-7);
            cal.set(Calendar.HOUR_OF_DAY,0);
            cal.set(Calendar.MINUTE,0);
            cal.set(Calendar.SECOND,0);
            return cal.getTime();
        }
    }

    private static Date changeEndTime(SimpleDateFormat sdf, String endDate){
        Calendar cal;
        try {
            cal = Calendar.getInstance();
            if(StringUtils.isNotEmpty(endDate)){
                cal.setTime(sdf.parse(endDate));
            }
            cal.set(Calendar.HOUR_OF_DAY,23);
            cal.set(Calendar.MINUTE,59);
            cal.set(Calendar.SECOND,59);
            return cal.getTime();
        } catch (ParseException e) {
            cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY,23);
            cal.set(Calendar.MINUTE,59);
            cal.set(Calendar.SECOND,59);
            return cal.getTime();
        }
    }

    private static Date convertDate(Date oldDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate=null;
        try {
            String s = sdf.format(oldDate);
            newDate=sdf.parse(s);
        }catch (Exception e){
            e.printStackTrace();
        }
        return newDate;
    }
}
