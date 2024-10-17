package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.vo.serve.ApiBuildingVo;

import java.util.List;

/**
 * 楼栋关联
 * @Author: lvzy
 * @Date: 2022-11-14 17:24:38
 * @Desc: 楼栋关联
 */
public interface BuildingMapper {

    /**
     * 根据小区查所有楼栋
     * @param communityId
     * @return
     */
    List<ApiBuildingVo> buildingList(Long communityId);

    /**
     * 根据楼栋查所有单元
     * @param buildingId
     * @return
     */
    List<ApiBuildingVo> unitList(Long buildingId);

    /**
     * 根据单元查所有房号
     * @param unitId
     * @return
     */
    List<ApiBuildingVo> roomList(Long unitId);

    /**
     * 根据门牌号查业主业主姓名和手机号
     * @param roomId
     * @return
     */
    List<ApiBuildingVo> ownerList(Long roomId);
}
