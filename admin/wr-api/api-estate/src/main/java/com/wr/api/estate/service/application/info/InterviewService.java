package com.wr.api.estate.service.application.info;

import com.wr.remote.domain.vo.InterviewVo;
import com.wr.remote.domain.vo.VisitorVo;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/14 11:00
 */
public interface InterviewService {

    /**
     * @param interviewVo
     * @return
     */
    List<InterviewVo> getList(InterviewVo interviewVo);

    InterviewVo getVisitorById(String visitorId);

    /**
     * @param communityId
     * @return
     */
    Integer getVisitCount(Long communityId);
}
