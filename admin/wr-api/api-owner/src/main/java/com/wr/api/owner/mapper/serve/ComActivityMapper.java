package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.dto.ComAcDTO;
import com.wr.api.owner.entity.vo.serve.ComAcVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: xuShu
 * @Date: 2022-11-16 09:50:17
 * @Desc:
 */
public interface ComActivityMapper {
    /**
     * 查询我是否参加了本小区活动
     * @param ownerId
     * @return
     */
    List<ComAcVo> getActivityOwn(Long ownerId);

    /**
     * 查询我参加的活动
     * @param ownerId
     * @param communityId
     * @return
     */
    List<ComAcDTO>  getMyActivity(@Param("ownerId") Long ownerId, @Param("communityId") Long communityId);

    /**
     * 查询我参加的活动详情
     * @param activityId
     * @return
     */
    ComAcVo getActivityInfo(Long activityId);

    /**
     * 查询所需要的物资
     * @param activityId
     * @return
     */
    List<Map<String,Object>> getSupplyName(Long activityId);

    /**
     * 签到
     * @param comAcVo
     * @return
     */
    int newSign(ComAcVo comAcVo);

    int getStartTime(@Param("activityId") Long activityId,@Param("signUpTime") Date signUpTime);
}
