package com.wr.estate.mapper.satisfaction;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.domain.SatisfactionSurvey;
import com.wr.remote.domain.vo.SatisfactionSurveyOwnerVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/27 17:05
 */
public interface SatisfactionSurveyMapper extends BaseMapper<SatisfactionSurvey> {


    /**
     * @param satisfactionSurvey
     * @return
     */
    List<SatisfactionSurvey> getList(SatisfactionSurvey satisfactionSurvey);

}
