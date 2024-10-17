package com.wr.estate.service.work.disasterOrder;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.estate.base.BusDisaster;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-10-27 18:28:48
 * @Desc:
 */
public interface DisasterLedgerService {
    /**
     * 分页查询灾害报事信息
     * @param busDisaster
     * @return
     */
    List<BusDisaster> listDisasterLedger(BusDisaster busDisaster);

    /**
     * 添加灾害报事信息
     *
     * @param busDisaster
     * @return
     */
    AjaxResult insertBusDisaster(BusDisaster busDisaster);

    /**
     * 删除灾害报事信息
     * @param disasterId
     * @return
     */
    Integer deleteDisasterId(Long disasterId);

    /**
     * 修改灾害报事信息
     *
     * @param busDisaster
     * @return 修改结果
     */
    AjaxResult updateBusDisaster(BusDisaster busDisaster);

    List<BusDisaster> getOrderType(Long estateId);

//    /**
//     * 批量导入灾害报事信息
//     * @param dataList
//     * @param updateSupport
//     * @param operName
//     * @return
//     */
//    String importData(List<BusDisaster> dataList, boolean updateSupport, String operName);
}
