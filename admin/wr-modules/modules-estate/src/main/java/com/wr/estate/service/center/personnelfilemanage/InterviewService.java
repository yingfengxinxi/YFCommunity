package com.wr.estate.service.center.personnelfilemanage;

import com.wr.remote.domain.vo.InterviewVo;
import com.wr.remote.domain.vo.VisitorVo;
import com.wr.remote.estate.center.vo.VisitorFileVo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lvzy
 * @Date 2024/3/14 11:21
 */
public interface InterviewService {

    /**
     * 查询
     * @param interviewVo
     * @return
     */
    List<InterviewVo> query(InterviewVo interviewVo);


    /**
     *
     * @param visitorId
     * @return
     */
    InterviewVo getVisitorById(Long visitorId);

    /**
     *
     * @param dataList
     * @param communityId
     * @return
     */
    String importData(List<InterviewVo> dataList, Long communityId);
}
