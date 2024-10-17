package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.vo.TreeSelectVo;
import com.wr.remote.domain.RentalVo;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-15 09:12:10
 * @Desc:
 */
public interface RentalService {
    /**
     * 获取所有在租的出租信息
     * @param rentalVo
     * @return
     */
    List<RentalVo> getRental(RentalVo rentalVo);



    /**
     * 获取我得房屋出租信息
     * @param rentalVo
     * @return
     */
    List<RentalVo> getMyRental(RentalVo rentalVo);

    /**
     * 我要出租
     * @param rentalVo
     * @return
     */
    AjaxResult insertRenTal(RentalVo rentalVo);

    /**
     * 获取详情
     * @param rentId
     * @return
     */
    List<RentalVo> getRentalInfo(Long rentId);

    /**
     * 查询小区
     * @return
     */
    List<TreeSelectVo> getCommunityTree(Long ownerId);

    /**
     * 查询楼栋树
     * @param id
     * @return
     */
    List<TreeSelectVo> getBuildingTree(Long communityId,Long ownerId);

    /**
     * 查询单元树
     * @param id
     * @return
     */
    List<TreeSelectVo> getUnitTree(Long buildingId,Long ownerId);

    /**
     * 查询房间树
     * @param id
     * @return
     */
    List<TreeSelectVo> getRoomTree(Long id,Long ownerId);

    /**
     * 出租下架、已出售
     * @param rentId
     * @param rentStatus 1=已出租，2=已下架
     * @return: java.lang.Integer
     * @date: 2023/4/20 16:55
     * @author: SJiang
     **/
    Integer outStack(Long rentId, String rentStatus);
}
