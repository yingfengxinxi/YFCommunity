package com.wr.estate.service.center;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.estate.entity.vo.CarportImportVo;
import com.wr.estate.entity.vo.UserIdVehicleNoVo;
import com.wr.remote.domain.vo.CarportVo;
import com.wr.remote.estate.center.vo.BusCarportLogVo;
import com.wr.remote.estate.center.vo.BusCarportVo;
import com.wr.remote.estate.center.vo.BusVehicleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 车位管理
 *
 * @Author: lvzy
 * @Date: 2022-10-14 13:55:31
 * @Desc: 车位管理
 */
public interface CarportService {

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
     *
     * @param carportId
     * @return
     */
    Long getByCarportIdAccountId(Long carportId);

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
     * 新增或修改
     *
     * @param carportVo
     * @return
     */
    AjaxResult saveOrUpdate(BusCarportVo carportVo);

    public void update(BusCarportVo carportVo);

    /**
     * 修改下详情
     *
     * @param carportVo
     * @return
     */
    BusCarportVo selectUpdateDetail(BusCarportVo carportVo);

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

    /**
     * @param accountId
     * @param carportNo
     */
    BusCarportVo getByAccountIdCarportInfo(Long accountId, String carportNo);

    void updateByCarportId(BusCarportVo carportVo);

    /**
     *
     * @param ownerId
     * @return
     */
    List<CarportVo> getOwnerIdCarportList(Long ownerId,Long communityId);


    /**
     *
     * @param carportNo
     * @return
     */
    Long getCarportNoCarportId(String carportNo);

    /**
     *
     * @param carportNo
     * @return
     */
    String getByCarportIdRentalType(Long carportId);

    /**
     *
     * @param carportNo
     * @return
     */
    BusCarportVo getCarportNoCarportNature(String carportNo);
}
