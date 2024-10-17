package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.dto.VenueDTO;
import com.wr.api.owner.entity.serve.StadiumPoint;
import com.wr.api.owner.entity.vo.serve.StadiumPointVo;
import com.wr.api.owner.entity.vo.serve.StadiumRuleVo;
import com.wr.api.owner.entity.vo.serve.StadiumVo;
import com.wr.api.owner.entity.vo.serve.TypeActVo;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-15 11:19:50
 * @Desc:
 */
public interface StadiumService {
    /**
     * 查询本小区的活动场馆
     * @param stadiumVo
     * @return
     */
    List<VenueDTO> getStadiumList(StadiumVo stadiumVo);

    /**
     * 查询本小区的活动场馆
     * @param stadiumVo
     * @return
     */
    List<StadiumVo> getStadiumInfo(StadiumVo stadiumVo);

    /**
     * 预约场馆
     * @param stadiumPoint
     * @return
     */
    AjaxResult insertAppoint(StadiumPoint stadiumPoint);

    /**
     * 查询我得预约
     * @param stadiumVo
     * @return
     */
    List<VenueDTO> getMyAppointInfo(StadiumVo stadiumVo);

    /**
     * 对于预约申请进行修改
     * @param stadiumPoint
     * @return
     */
    AjaxResult updateStatus(StadiumPoint stadiumPoint);

    /**
     * 查询本小区活动类型
     * @param communityId
     * @return
     */
    List<TypeActVo> getTypeName(Long communityId);

    /**
     * 根据小区查询物业id并查询场馆规则
     * @param communityId
     * @return
     */
    List<StadiumRuleVo> getRule(Long communityId);

    /**
     * 查询我得预约详细信息
     * @param communityId
     * @param venueId
     * @param cardNo
     * @return
     */
    List<StadiumPointVo> getMyInfo(Long communityId, Long venueId, String cardNo, Long appointId);
}
