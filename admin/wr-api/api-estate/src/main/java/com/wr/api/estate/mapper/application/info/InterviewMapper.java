package com.wr.api.estate.mapper.application.info;

import com.wr.remote.domain.vo.InterviewVo;
import com.wr.remote.domain.vo.VisitorVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/14 11:02
 */
public interface InterviewMapper {


    /**
     * @param interviewVo
     * @return
     */
    List<InterviewVo> getList(InterviewVo interviewVo);

    /**
     *
     * @param visitorId
     * @return
     */
    InterviewVo getVisitorById(@Param("visitorId") String visitorId);

    /**
     *
     * @param communityId
     * @return
     */
    Integer getVisitCount(@Param("communityId") Long communityId);
}
