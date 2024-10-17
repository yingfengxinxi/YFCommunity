package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.vo.TreeSelectVo;
import com.wr.remote.domain.RentalVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-15 09:12:27
 * @Desc:
 */
public interface RentalMapper {
    /**
     * 获取所有在租的出租信息
     *
     * @param rentalVo
     * @return
     */
    List<RentalVo> getRentalList(RentalVo rentalVo);


    /**
     * 获取我得房屋出租信息
     *
     * @param rentalVo
     * @return
     */
    List<RentalVo> getMyRental(RentalVo rentalVo);

    /**
     * 我要出租
     *
     * @param rentalVo
     * @return
     */
    int insertMyRental(RentalVo rentalVo);

    /**
     * 查询浏览次数
     *
     * @param rentId
     * @return
     */
    int getReadNum(Long rentId);

    /**
     * 修改浏览次数
     *
     * @param readNum
     * @param rentId
     * @return
     */
    int updateReadNum(@Param("readNum") int readNum, @Param("rentId") Long rentId);

    /**
     * 获取房租详情
     *
     * @param rentId
     * @return
     */
    List<RentalVo> RentalInfo(Long rentId);

    /**
     * 查询房屋是否已经被出租
     *
     * @param roomNumber,roomNumber
     * @return
     */
    List<String> getMyAddress(@Param("roomNumber")String roomNumber,@Param("communityId")Long communityId);


    /**
     * 获取楼栋树
     *
     * @param
     * @return
     */
    List<TreeSelectVo> getCommunityTree(Long ownerId);

    /**
     * 获取楼栋树
     *
     * @param communityId
     * @return
     */
    List<TreeSelectVo> getBuildingTree(@Param("communityId") Long communityId, @Param("ownerId") Long ownerId);

    /**
     * 获取单元树
     *
     * @param buildingId
     * @return
     */
    List<TreeSelectVo> getUnitTree(@Param("buildingId") Long buildingId, @Param("ownerId") Long ownerId);

    /**
     * 获取房间树
     *
     * @param unitId
     * @return
     */
    List<TreeSelectVo> getRoomTree(@Param("unitId") Long unitId, @Param("ownerId") Long ownerId);

    /**
     * 查询面积
     *
     * @param communityId
     * @param roomName
     * @param buildingId
     * @param unitId
     * @return
     */
    BigDecimal getTotalArea(@Param("communityId") Long communityId, @Param("roomName") String roomName, @Param("buildingId") Long buildingId, @Param("unitId") Long unitId);

    /**
     * 出租下架、已出售
     * @param rentId
     * @param rentStatus 1=已出租，2=已下架
     * @return: java.lang.Integer
     * @date: 2023/4/20 16:55
     * @author: SJiang
     **/
    Integer outStack(@Param("rentId") Long rentId, @Param("rentStatus") String rentStatus);
}
