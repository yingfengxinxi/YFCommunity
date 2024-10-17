package com.wr.api.estate.service.impl.application.info;

import com.wr.api.estate.mapper.application.info.InterviewMapper;
import com.wr.api.estate.service.application.info.InterviewService;
import com.wr.remote.domain.vo.InterviewVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/14 11:01
 */
@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private InterviewMapper interviewMapper;

    @Override
    public List<InterviewVo> getList(InterviewVo interviewVo) {
        return interviewMapper.getList(interviewVo);
    }

    @Override
    public InterviewVo getVisitorById(String visitorId) {
        return interviewMapper.getVisitorById(visitorId);
    }

    @Override
    public Integer getVisitCount(Long communityId) {
        return interviewMapper.getVisitCount(communityId);
    }
}
