package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.dto.VenueDTO;
import com.wr.api.owner.entity.serve.StadiumPoint;
import com.wr.api.owner.entity.vo.serve.StadiumPointVo;
import com.wr.api.owner.entity.vo.serve.StadiumRuleVo;
import com.wr.api.owner.entity.vo.serve.StadiumVo;
import com.wr.api.owner.entity.vo.serve.TypeActVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-15 11:19:29
 * @Desc:
 */
public interface StadiumMapper {

    /**
     * 查询本小区的活动场馆
     * @param stadiumVo
     * @return
     */
    List<VenueDTO> stadiumList(StadiumVo stadiumVo);


    /**
     * 获取本场馆的预约信息
     * @param venueId
     * @param createTime
     * @return
     */
    List<StadiumPointVo> getAppointyId(@Param("venueId") Long venueId,@Param("createTime") String createTime,@Param("communityId")Long communityId);

    /**
     * 查询场馆详细信息
     * @param venueId
     * @return
     */
    List<StadiumVo> getStadiumInfo(Long venueId);


    /**
     * 预约场馆
     * @param stadiumPoint
     * @return
     */
    int insertStadium(StadiumPoint stadiumPoint);

    /**
     * 查询是否有场馆在预约时间中使用
     * @param starttime
     * @param endtime
     * @param venueId
     * @return
     */
    int getcount(@Param("starttime") String starttime, @Param("endtime") String endtime,@Param("venueId")Long venueId);

    /**
     * 查询我得预约
     * @param cardNo
     * @param appointId
     * @return
     */
    List<VenueDTO> getAppointMine(StadiumVo stadiumVo);

    /**
     * 对于预约申请进行修改
     * @param stadiumPoint
     * @return
     */
    int updateEndStatus(StadiumPoint stadiumPoint);

    /**
     * 查询本小区活动类型
     * @param communityId
     * @return
     */
    List<TypeActVo> getCommunityAct(Long communityId);

    /**
     * 根据小区查询物业id并查询场馆规则
     * @param communityId
     * @return
     */
    List<StadiumRuleVo> getVenueRule(Long communityId);

    /**
     * 根据身份证、小区id、场馆id查询我得预约详情
     * @param communityId
     * @param venueId
     * @param cardNo
     * @return
     */
    List<StadiumPointVo> getMyPoint(@Param("communityId") Long communityId,
                                    @Param("venueId")Long venueId,
                                    @Param("cardNo") String cardNo,
                                    @Param("appointId") Long appointId);
}
