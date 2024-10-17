package com.wr.api.owner.mapper.estateapi.satisfaction;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.domain.SatisfactionSurvey;
import com.wr.remote.estate.center.Room;

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

    /**
     * 根据楼栋id查询所有房子
     *
     * @param buildingId 楼栋id
     * @return java.util.List<com.wr.remote.business.center.Room>
     * @author yx
     * @date 2022/10/13 14:33
     */
    List<Room> listByBuildingId(Long buildingId);

    /**
    * @Author SUNk
    * @Description 业主端查询列表
    * @Date 10:13 2024/3/17
    * @Param [satisfactionSurvey]
    * @return java.util.List<com.wr.remote.domain.SatisfactionSurvey>
    **/
    List<SatisfactionSurvey> findListSatisfaction(SatisfactionSurvey satisfactionSurvey);
}
