package com.wr.api.estate.service.impl.application.serve;

import com.wr.api.estate.entity.application.serve.RescueUser;
import com.wr.api.estate.entity.vo.application.serve.RescueVO;
import com.wr.api.estate.mapper.application.serve.RescueMapper;
import com.wr.api.estate.service.application.serve.RescueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 13:48:47
 * @Desc: 社会援助业务层
 */
@Service
public class RescueServiceImpl implements RescueService {

    @Autowired
    private RescueMapper rescueMapper;

    @Override
    public List<RescueVO> selectRescueList(RescueVO rescueVO) {
        return rescueMapper.selectRescueList(rescueVO);
    }

    @Override
    public RescueVO getRescueDetail(Long rescueId) {
        RescueVO rescueDetail = rescueMapper.getRescueDetail(rescueId);
        //查询发布人信息
        RescueVO releaseInfo = rescueMapper.getReleaseInfo(rescueDetail.getReleaseId(),rescueDetail.getReleaseType());
        if (releaseInfo != null) {
            rescueDetail.setPublisherName(releaseInfo.getPublisherName());
            rescueDetail.setPublisherPhone(releaseInfo.getPublisherPhone());
            rescueDetail.setGender(releaseInfo.getGender());
            rescueDetail.setPublisherIdCard(releaseInfo.getPublisherIdCard());
            rescueDetail.setPublisherAge(releaseInfo.getPublisherAge());
        }
        //查询志愿者信息
        List<RescueUser> rescueList = rescueMapper.selectRescueUserList(rescueId);
        rescueDetail.setRescueUserList(rescueList);
        return rescueDetail;
    }
}
