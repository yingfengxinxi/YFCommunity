package com.wr.api.owner.service.estateapi;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.api.owner.entity.estateapi.CarportImportVo;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.vo.CarportVo;
import com.wr.remote.estate.center.BusCarport;
import com.wr.remote.estate.center.vo.BusCarportLogVo;
import com.wr.remote.estate.center.vo.BusCarportVo;
import com.wr.remote.estate.center.vo.BusVehicleVo;

import java.util.List;

/**
 * 车位管理
 *
 * @Author: lvzy
 * @Date: 2022-10-14 13:55:31
 * @Desc: 车位管理
 */
public interface CarportService extends IService<BusCarport> {

    /**
     * 分页查询
     *
     * @param carportVo
     * @return
     */
    List<BusCarportVo> listPage(BusCarportVo carportVo);

    /**
     * 详情内容
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
    List<CarportVo> getCommunityCarportList(Long communityId);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    Integer removes(Long[] ids);

    /**
     * 转让 -> 新增车位记录表数据
     *
     * @param carportVo
     * @return
     */
    Integer cedeSubmit(BusCarportLogVo carportVo);

    /**
     * 新增或修改
     *
     * @param carportVo
     * @return
     */
    AjaxResult saveOrUpdate(BusCarportVo carportVo);

    /**
     * 修改下详情
     *
     * @param carportVo
     * @return
     */
    BusCarportVo updateDetail(BusCarportVo carportVo);

    /**
     * 修改车位状态
     *
     * @param carportVo
     * @return
     */
    Integer changeCarportStatus(BusCarportVo carportVo);

    /**
     * @param dataList
     * @param communityId
     * @return
     */
    String importData(List<CarportImportVo> dataList, Long communityId);

    AjaxResult saveCar(BusVehicleVo vehicleVo);

    List<BusCarportVo> selectBusCarportNoRental(BusCarportVo carportVo);

    void updateCarportId(Long carportId,Long communityId,Long roomId);

    /**
    * @Author SUNk
    * @Description 续费
    * @Date 16:59 2024/4/9
    * @Param carport
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    AjaxResult renewalBusCarport(BusCarportVo carport);

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
