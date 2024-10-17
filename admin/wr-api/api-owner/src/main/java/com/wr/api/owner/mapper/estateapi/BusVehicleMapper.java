package com.wr.api.owner.mapper.estateapi;

import com.wr.remote.estate.center.vo.BusVehicleVo;
import com.wr.remote.estate.center.vo.SpaceVehicleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 车辆管理
 *
 * @Author: lvzy
 * @Date: 2022-10-11 19:39:54
 * @Desc: 车辆管理
 */
@Mapper
public interface BusVehicleMapper {

    /**
     * 分页查询
     *
     * @param vehicleVo
     * @return
     */
    List<BusVehicleVo> listPage(BusVehicleVo vehicleVo);

    /**
     * 详情查看 查询是业主还是租客
     *
     * @param vehicleId
     * @return
     */
    Integer accountTypeId(@Param("vehicleId") Long vehicleId);

    /**
     * 详情查看 业主
     *
     * @param vehicleId
     * @return
     */
    BusVehicleVo detailOwner(@Param("vehicleId") Long vehicleId);

    /**
     * 详情查看 租客
     *
     * @param vehicleId
     * @return
     */
    BusVehicleVo detailTenant(@Param("vehicleId") Long vehicleId);

    /**
     * 详情查看 访客
     *
     * @param vehicleId
     * @return
     */
    BusVehicleVo detailVistor(@Param("vehicleId") Long vehicleId);

    /**
     * 详情查看 访客 （装修工）
     *
     * @param vehicleId
     * @return
     */
    BusVehicleVo detailWorker(@Param("vehicleId") Long vehicleId);

    /**
     * 逻辑删除
     *
     * @param vehicleId
     * @return
     */
    Integer remove(@Param("vehicleId") Long vehicleId);

    /**
     * 查找业户手机号
     *
     * @param ownerId
     * @return
     */
    BusVehicleVo selectOwnerName(Long ownerId);

    /**
     * 查找业主车辆
     *
     * @param ownerId
     * @return
     */
    List<BusVehicleVo> selectOwnerCar(Long ownerId);

    Integer selectVehicleNo(@Param("plateNo") String plateNo, @Param("communityId") Long communityId, @Param("vehicleId") Long vehicleId);

    /**
     * 新增车辆信息
     *
     * @param vehicleVo
     * @return
     */
    Integer saveCar(BusVehicleVo vehicleVo);

    /**
     * @param accountId
     * @param communityId
     * @param plateNo
     * @return
     */
    Long getVehicleId(@Param("accountId") Long accountId, @Param("communityId") Long communityId, @Param("plateNo") String plateNo);


    /**
     * 修改车辆信息
     *
     * @param vehicleVo
     * @return
     */
    Integer updateCar(BusVehicleVo vehicleVo);

    /**
     * 根据车辆ID查找车主ID覆盖
     *
     * @param ownerCarValueId
     * @return
     */
    Long selectOwnerIdByCar(Long ownerCarValueId);

    /**
     * 根据手机号找 小区ID和车主ID
     *
     * @param loginPhone
     * @return
     */
    BusVehicleVo selectOwnerIdAndCommunityIdByVehicle(String loginPhone);

    /**
     * 根据车主ID查是否存在重复车牌号
     *
     * @param plateNo
     * @return
     */
    Integer selectPlateNoBoolean(@Param("plateNo") String plateNo);

    /**
     * @param accountId
     * @param communityId
     */
    void deleteByAccountId(@Param("accountId") Long accountId, @Param("communityId") Long communityId);

    /**
     * @param accountId
     * @param communityId
     * @return
     */
    List<SpaceVehicleVo> getAccountIdVehicleInfo(@Param("accountId") Long accountId, @Param("communityId") Long communityId);

    /**
     * @param communityId
     * @param plateNo
     * @return
     */
    SpaceVehicleVo getByPlateNoInfo(@Param("communityId") Long communityId, @Param("plateNo") String plateNo);
}