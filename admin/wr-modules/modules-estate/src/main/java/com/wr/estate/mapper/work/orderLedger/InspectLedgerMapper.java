package com.wr.estate.mapper.work.orderLedger;

import com.wr.remote.estate.manage.Staff;
import com.wr.remote.estate.order.vo.ledgerVo.InspectLedVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-25 19:35:19
 * @Desc: 巡检台账
 */
@Mapper
public interface InspectLedgerMapper {
    /**
     * 分页查询
     * @param inspectLedVo
     * @return
     */
    List<InspectLedVo> listInspect(InspectLedVo inspectLedVo);

    /**
     * 详情查询
     * @param inspectId
     * @return
     */
    InspectLedVo getInspect(Long inspectId);

    /*协助人员列表*/
    List<InspectLedVo> getInsStaffList(Long inspectId);

    /**
     * 详情内容 -> 设备详情
     * @param inspectId
     * @return
     */
    List<InspectLedVo> detailList(Long inspectId);

    /*巡检类型名字列表*/
    List<InspectLedVo> getTourName(Long communityId);
}
