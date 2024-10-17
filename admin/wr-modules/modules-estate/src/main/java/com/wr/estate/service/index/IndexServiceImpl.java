package com.wr.estate.service.index;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.TableUtils;
import com.wr.common.security.utils.DictUtils;
import com.wr.estate.entity.vo.OrderNumVO;
import com.wr.estate.entity.vo.SendSmsRecordVo;
import com.wr.remote.domain.vo.GetFoundDataVo;
import com.wr.estate.mapper.TableMapper;
import com.wr.estate.mapper.complaintSuggestion.ComplaintSuggestionMapper;
import com.wr.estate.mapper.index.IndexMapper;
import com.wr.estate.mapper.send.SendSmsRecordMapper;
import com.wr.estate.service.impl.index.IndexService;
import com.wr.remote.domain.Community;
import com.wr.remote.domain.ComplaintSuggestion;
import com.wr.remote.domain.SysDictData;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author DIANWEI
 * 2022-12-01
 * 首页 service 实现类
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private IndexMapper indexMapper;

    @Autowired
    private TableMapper tableMapper;

    @Autowired
    private SendSmsRecordMapper sendSmsRecordMapper;

    @Autowired
    private ComplaintSuggestionMapper complaintSuggestionMapper;

    @Override
    public Map<String, Object> getAuditNum(Long estateId) {
        Map<String, Object> auditNum = indexMapper.getAuditNum(estateId);

        //待发送消息
        SendSmsRecordVo sendSmsRecordVo = new SendSmsRecordVo();
        sendSmsRecordVo.setEstateId(estateId);
        sendSmsRecordVo.setStatus("0");
        int size1 = sendSmsRecordMapper.getSendSmsEstateList(sendSmsRecordVo).size();
        int size2 = sendSmsRecordMapper.getSendSmsOwnerList(sendSmsRecordVo).size();
        auditNum.put("sendNum", size1 + size2);
        //投诉建议未回复内容
        LambdaQueryWrapper<ComplaintSuggestion> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ComplaintSuggestion::getEstateId, estateId);
        queryWrapper.apply(" (property_response='' or property_response is null)");
        Long complaintSuggestionNum = complaintSuggestionMapper.selectCount(queryWrapper);
        auditNum.put("complaintSuggestionNum", complaintSuggestionNum);
        return auditNum;
    }

    @Override
    public List<Community> getCommunityIdList(Long estateId) {
        return indexMapper.getCommunityIdList(estateId);
    }

    @Override
    public Map<String, List<BigDecimal>> getLogList(Long communityId) {
        Map<String, List<BigDecimal>> map = new HashMap<>(2);
        //人行
        List<BigDecimal> userNumList = new ArrayList<>();
        //车行
        List<BigDecimal> vehicleNumList = new ArrayList<>();
        //开始时间
        String[] startTime = new String[]{"00:00", "02:00", "04:00", "06:00", "08:00", "10:00", "12:00"
                , "14:00", "16:00", "18:00", "20:00", "22:00"};
        //结束时间
        String[] endTime = new String[]{"01:59", "03:59", "05:59", "07:59", "09:59", "11:59", "13:59"
                , "15:59", "17:59", "19:59", "21:59", "23:59"};
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(date);
        // 当前年月日
        for (int i = 0; i < startTime.length; i++) {
            startTime[i] = time + " " + startTime[i];
            endTime[i] = time + " " + endTime[i];
        }
        List<SysDictData> eventTypeDictList = DictUtils.getDictCache("event_type");
        for (int i = 0; i < startTime.length; i++) {
            List<String> eventTypeList = eventTypeDictList.stream().map(SysDictData -> SysDictData.getDictValue()).collect(Collectors.toList());
            //每日行人出入数据
            BigDecimal userNumBig = indexMapper.getUserNumList(communityId, eventTypeList, startTime[i], endTime[i]);
            //每日车辆出入数据
            BigDecimal vehicleNumBig = indexMapper.getVehicleNumList(communityId, startTime[i], endTime[i]);

            userNumList.add(userNumBig);
            vehicleNumList.add(vehicleNumBig);
        }
        map.put("userNumList", userNumList);
        map.put("vehicleNumList", vehicleNumList);
        return map;
    }

    @Override
    public Map<String, List<BigDecimal>> getEnergyData(Long meterType, Long communityId) {
        Map<String, List<BigDecimal>> map = new HashMap<>(2);
        //今年
        List<BigDecimal> thisYear = new ArrayList<>();
        //上一年
        List<BigDecimal> lastYear = new ArrayList<>();
        //开始时间
        String[] meterTime = new String[]{"01", "02", "03", "04", "05", "06", "07"
                , "08", "09", "10", "11", "12"};
        String[] meterTimeLast = new String[]{"01", "02", "03", "04", "05", "06", "07"
                , "08", "09", "10", "11", "12"};
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        String time = df.format(date);
        // 当前年
        for (int i = 0; i < meterTime.length; i++) {
            meterTime[i] = time + "-" + meterTime[i];
            BigDecimal thisConsume = new BigDecimal(0);
            thisConsume = thisConsume.add(indexMapper.getEnergyData(meterType, communityId, meterTime[i]));
            thisYear.add(thisConsume);
        }
        //上一年
        for (int i = 0; i < meterTimeLast.length; i++) {
            meterTimeLast[i] = Integer.parseInt(time) - 1 + "-" + meterTimeLast[i];
            BigDecimal thisConsume = new BigDecimal(0);
            thisConsume = thisConsume.add(indexMapper.getEnergyData(meterType, communityId, meterTimeLast[i]));
            lastYear.add(thisConsume);
        }
        map.put("thisYear", thisYear);
        map.put("lastYear", lastYear);
        return map;
    }

    @Override
    public Map<String, Object> getRoomStatusData(Long communityId) {
        return indexMapper.getRoomStatusData(communityId);
    }

    @Override
    public List<OrderNumVO> getOrderList(Long estateId) {
        return indexMapper.getOrderList(estateId);
    }

    @Override
    public List<Map<String, Object>> getNoticeList(Long estateId) {
        return indexMapper.getNoticeList(estateId);
    }

    @Override
    public AjaxResult getFoundData(Long communityId) {

        AjaxResult ajaxResult = new AjaxResult();


        List<GetFoundDataVo> foundDataVoList = Lists.newArrayList();
        List<SysDictData> dictCache = DictUtils.getDictCache("pay_type");
        List<GetFoundDataVo> foundDataList = indexMapper.getFoundData(communityId);
        final BigDecimal[] totalAmount = {new BigDecimal("0.00")};
        dictCache.stream().forEach(sysDictData -> {
            GetFoundDataVo getFoundDataVo = new GetFoundDataVo();
            if (CollectionUtils.isNotEmpty(foundDataList)) {
                List<GetFoundDataVo> collect = foundDataList.stream().filter(gfv -> gfv.getName().equals(sysDictData.getDictValue())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect)) {
                    getFoundDataVo.setValue(collect.get(0).getValue());
                    totalAmount[0] = totalAmount[0].add(getFoundDataVo.getValue());
                } else {
                    getFoundDataVo.setValue(new BigDecimal("0.00"));
                }
            } else {
                getFoundDataVo.setValue(new BigDecimal("0.00"));
            }
            getFoundDataVo.setName(sysDictData.getDictLabel());
            foundDataVoList.add(getFoundDataVo);
        });

        ajaxResult.put("data", foundDataVoList);
        ajaxResult.put("totalAmount", totalAmount[0]);
        return ajaxResult;
    }


}
