package com.wr.estate.mapper.center.carManage;

import com.wr.estate.entity.vo.UserIdVehicleNoVo;
import com.wr.remote.domain.vo.CarportVo;
import com.wr.remote.estate.center.BusCarport;
import com.wr.remote.estate.center.vo.BusCarportLogVo;
import com.wr.remote.estate.center.vo.BusCarportVo;
import com.wr.remote.estate.center.vo.SpaceVehicleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 车位管理
 *
 * @Author: lvzy
 * @Date: 2022-10-11 11:56:45
 * @Desc: 车位管理
 */
@Mapper
public interface BusCarportMapper {

    /**
     * 分页查询
     *
     * @param carportVo
     * @return
     */
    List<BusCarportVo> listPage(BusCarportVo carportVo);

    /**
     * 详情查看
     *
     * @param carportId
     * @return
     */
    BusCarportVo detail(Long carportId);

    /**
     *
     * @param carportId
     * @return
     */
    Long getByCarportIdAccountId(@Param("carportId") Long carportId);

    /**
     * 详情下列表
     *
     * @param carportId
     * @return
     */
    List<BusCarportLogVo> detailList(Long carportId);

    /**
     * @param communityId
     * @return
     */
    List<CarportVo> getCommunityCarportList(@Param("communityId") Long communityId);

    /**
     * 批量删除
     *
     * @param id
     * @return
     */
    Integer removes(Long id);

    /**
     * 转让 -> 新增车位记录表数据
     *
     * @param carportVo
     * @return
     */
    Integer cedeSubmit(BusCarportLogVo carportVo);

    /**
     * 新增
     *
     * @param carportVo
     * @return
     */
    Integer save(BusCarportVo carportVo);

    /**
     * 修改下详情
     *
     * @param carportVo
     * @return
     */
    BusCarportVo selectUpdateDetail(BusCarportVo carportVo);

    /**
     * 修改车位管理
     *
     * @param carportVo
     * @return
     */
    Integer update(BusCarportVo carportVo);

    void updateByCarportId(BusCarportVo carportVo);

    /**
     * 修改车位状态
     *
     * @param carportVo
     * @return
     */
    Integer changeCarportStatus(BusCarportVo carportVo);

    /**
     * 查找车位是否存在
     *
     * @param carportNo
     * @return
     */
    Integer selectCarportNo(@Param("carportNo") String carportNo, @Param("communityId") Long communityId, @Param("carportId") Long carportId);

    /**
     * carportVo
     *
     * @param carportVo
     * @return
     */
    Integer updateCede(BusCarportLogVo carportVo);

    /**
     * 根据业主ID查 产权人id，表bus_account
     *
     * @param ownerId
     * @return
     */
    Long selectCarportAccount(Long ownerId);

    /**
     * 根据车辆ID查 车牌号
     *
     * @param ownerCarValueId
     * @return
     */
    String selectPlateNoById(String ownerCarValueId);

    /**
     * @param carportNo
     * @param communityId
     * @return
     */
    BusCarport getByCarportNoCarportInfo(@Param("carportNo") String carportNo, @Param("communityId") Long communityId);

    /**
     * @param accountId
     * @param vehicleId
     * @param carportNo
     * @param communityId
     */
    void updateByCarportNoAccount(@Param("accountId") Long accountId, @Param("vehicleId") Long vehicleId, @Param("carportNo") String carportNo, @Param("communityId") Long communityId);

    /**
     * @param accountId
     * @param communityId
     * @param vehicleId
     * @return
     */
    SpaceVehicleVo getVehicleIdCarport(@Param("accountId") Long accountId, @Param("communityId") Long communityId, @Param("vehicleId") Long vehicleId);

    /**
     * @param communityId
     * @param carportNo
     * @return
     */
    Integer getCarportNoCount(@Param("communityId") Long communityId, @Param("carportNo") String carportNo);


    /**
     * @param accountId
     * @param carportNo
     */
    BusCarportVo getByAccountIdCarportInfo(@Param("accountId") Long accountId, @Param("carportNo") String carportNo);

    /**
     * @param ownerId
     * @return
     */
    List<CarportVo> getOwnerIdCarportList(@Param("ownerId") Long ownerId, @Param("communityId") Long communityId);

    /**
     * @param carportNo
     * @return
     */
    Long getCarportNoCarportId(@Param("carportNo") String carportNo);

    /**
     *
     * @param carportNo
     * @return
     */
    BusCarportVo getCarportNoCarportNature(@Param("carportNo") String carportNo);

    /**
     *
     * @param carportId
     * @return
     */
    String getByCarportIdRentalType(@Param("carportId") Long carportId);

    /**
     *
     * @param carportId
     * @return
     */
    Long getByCarportIdNo(@Param("carportId") Long carportId);
}