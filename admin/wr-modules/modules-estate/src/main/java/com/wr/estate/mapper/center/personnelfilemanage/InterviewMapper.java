package com.wr.estate.mapper.center.personnelfilemanage;

import com.wr.remote.domain.vo.InterviewVo;
import com.wr.remote.domain.vo.VisitorVo;
import com.wr.remote.estate.center.vo.VisitorFileVo;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lvzy
 * @Date 2024/3/14 11:22
 */
public interface InterviewMapper {

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
    InterviewVo getVisitorById(@Param("visitorId") Long visitorId);

    /**
     *
     * @param interviewVo
     */
    void insert(InterviewVo interviewVo);

}
