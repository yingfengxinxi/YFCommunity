package com.wr.estate.service.send.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.common.core.utils.DesensitizationUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.estate.entity.vo.SendSmsRecordVo;
import com.wr.estate.mapper.send.SendSmsRecordMapper;
import com.wr.estate.service.send.SendSmsRecordService;
import com.wr.remote.domain.SendSmsRecord;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/29 21:00
 */
@Service
public class SendSmsRecordServiceImpl extends ServiceImpl<SendSmsRecordMapper, SendSmsRecord> implements SendSmsRecordService {

    /**
     * @param sendSmsRecordVo
     * @return
     */
    @Override
    public List<SendSmsRecordVo> getSendSmsOwnerList(SendSmsRecordVo sendSmsRecordVo) {
        List<SendSmsRecordVo> sendSmsOwnerList = baseMapper.getSendSmsOwnerList(sendSmsRecordVo);
        if (CollectionUtils.isNotEmpty(sendSmsOwnerList)) {
            sendSmsOwnerList.forEach(ssrv -> {
                String name = ssrv.getName();
                if (StringUtils.isNotEmpty(name)) {
                    ssrv.setName(DesensitizationUtils.name(name));
                }
            });
        }
        return sendSmsOwnerList;
    }

    /**
     * @param sendSmsRecordVo
     * @return
     */
    @Override
    public List<SendSmsRecordVo> getSendSmsEstateList(SendSmsRecordVo sendSmsRecordVo) {
        List<SendSmsRecordVo> sendSmsEstateList = baseMapper.getSendSmsEstateList(sendSmsRecordVo);
        if (CollectionUtils.isNotEmpty(sendSmsEstateList)) {
            sendSmsEstateList.forEach(sse -> {
                String name = sse.getName();
                if (StringUtils.isNotEmpty(name)) {
                    sse.setName(DesensitizationUtils.name(name));
                }
            });
        }
        return sendSmsEstateList;
    }

    /**
     * @param festivalName
     * @return
     */
    @Override
    public Integer getByYearFestivalNameCount(String festivalName) {
        return baseMapper.getByYearFestivalNameCount(festivalName);
    }
}
