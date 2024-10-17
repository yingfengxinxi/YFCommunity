package com.wr.estate.service.work.disasterOrder;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.work.inspect.OrderRecord;
import com.wr.remote.work.vo.BusDisasterOrderVo;
import com.wr.remote.work.vo.OrderRecordVo;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-04 17:48:26
 * @Desc:
 */

public interface DisasterOrderService {
    /**
     * 查询灾害报事工单列表
     * @param busDisasterOrderVo
     * @return
     */
    List<BusDisasterOrderVo> getDisasterOrderList(BusDisasterOrderVo busDisasterOrderVo);

    /**
     * 灾害报事执行人详情
     * @param busDisasterOrderVo
     * @return
     */
    List<BusDisasterOrderVo> getInfoList(BusDisasterOrderVo busDisasterOrderVo);

    /**
     * 修改灾害报事工单
     * @param busDisasterOrderVo
     * @return
     */
    AjaxResult updateOrder(BusDisasterOrderVo busDisasterOrderVo);

    /**
     * 确定分配
     * @param busDisasterOrderVo
     * @return
     */
    AjaxResult shareUpdate(BusDisasterOrderVo busDisasterOrderVo);

    /**
     * 获取执行人
     * @param communityId
     * @return
     */
    List<BusDisasterOrderVo> getNameList(Long communityId);

    /**
     * 根据id删除灾害工单信息
     * @param orderId
     * @return
     */
    int deleteByOrder(Long orderId);

    /**
     * 获取执行情况详情
     * @param orderId
     * @return
     */

    List<OrderRecordVo> deatilListThree(Long orderId);

    public List<OrderRecord> detailListThreeGeneral(Long orderId,String orderType);

    /**
     * 审核通过并向灾害工单添加一条数据
     * @param busDisasterOrderVo
     */
    int insertOrder(BusDisasterOrderVo busDisasterOrderVo);
}
