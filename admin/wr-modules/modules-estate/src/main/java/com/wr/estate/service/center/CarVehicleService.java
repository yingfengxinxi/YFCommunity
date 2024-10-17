package com.wr.estate.service.center;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.estate.center.vo.BusVehicleVo;
import com.wr.remote.estate.center.vo.SelectVehicleVo;
import com.wr.remote.estate.center.vo.SpaceVehicleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 车辆管理
 * @Author: lvzy
 * @Date: 2022-10-12 10:00:30
 * @Desc: 车辆管理
 */
public interface CarVehicleService {

    /**
     * 分页查询
     * @param vehicleVo 车辆管理扩展
     * @return
     */
    List<BusVehicleVo> listPage(BusVehicleVo vehicleVo);

    /**
     * 详情查看
     * @param vehicleId 车辆表ID
     * @return
     */
    BusVehicleVo detail(Long vehicleId);

    /**
     * 逻辑删除
     * @param vehicleId
     * @return
     */
    Integer remove(Long vehicleId);

    /**
     * 查找业户手机号
     * @param ownerId
     * @return
     */
    BusVehicleVo selectOwnerName(Long ownerId);

    /**
     * 查找业主车辆
     * @param ownerId
     * @return
     */
    List<BusVehicleVo> selectOwnerCar(Long ownerId);

    /**
     * 新增车辆信息
     * @param vehicleVo
     * @return
     */
    AjaxResult saveOrUpdate(BusVehicleVo vehicleVo);

    /**
     * 编辑页面详情展示
     * @param vehicleId
     * @return
     */
    BusVehicleVo updateDetail(Long vehicleId);
    public AjaxResult saveCar(BusVehicleVo vehicleVo);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Integer removes(Long[] ids);

    /**
     *
     * @param dataList
     * @param communityId
     * @return
     */
    String importData(List<BusVehicleVo> dataList,Long communityId);

    /**
     * @param communityId
     * @param ownerId
     * @return
     */
    List<SelectVehicleVo> getByOwnerIdVehicleList(Long communityId, Long ownerId);


    /**
     *
     * @param communityId
     * @param accountId
     * @return
     */
    List<SelectVehicleVo> getByAccountIdVehicleList(Long communityId,Long accountId);
}
