package com.wr.api.owner.mapper.estateapi;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface BusCarportMapper extends BaseMapper<BusCarport> {

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

    void updateCarportId(
            @Param("carportId") Long carportId,
            @Param("communityId") Long communityId,
            @Param("roomId") Long roomId
    );

    /**
     * @return java.util.List<com.wr.remote.estate.center.vo.BusCarportVo>
     * @Author SUNk
     * @Description 查询未出租的车位管理
     * @Date 18:20 2024/3/27
     * @Param [carportVo]
     **/
    List<BusCarportVo> selectBusCarportNoRental(BusCarportVo carportVo);

    /**
     * 修改车位管理
     *
     * @param carport
     * @return
     */
    Integer updateByBusCarport(BusCarportVo carport);

    /**
     * 详情查看
     *
     * @param carportId
     * @return
     */
    BusCarportVo detailBusCarport(Long carportId);

    /**
     * 清理车位管理表
     *
     * @param carportId
     * @return
     */
    void clearBusCarport(String carportId);
}