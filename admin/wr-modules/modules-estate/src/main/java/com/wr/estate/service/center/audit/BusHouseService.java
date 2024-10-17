package com.wr.estate.service.center.audit;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.estate.entity.BusOwner;
import com.wr.estate.entity.vo.OwneAuditVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-14 19:54:41
 * @Desc: 房产审核
 */
public interface BusHouseService {
    /**
     * 查询房产审核数据集合
     * @param busOwner
     * @return
     */
    List<OwneAuditVo> listOwnerAuditQure(BusOwner busOwner);


    /**
     * 批量删除房产审批信息
     *
     * @param houseIds 需要删除的参数ID
     */
    void deleteConfigByIds(Long[] houseIds);


    /**
     * 查询单个房产审核信息
     * @param houseId
     * @return
     */
    OwneAuditVo  selectOneOwneAuditVo(Long houseId);

    /**
     * 审核通过或驳回
     * @return
     */
    AjaxResult passAndRejection(OwneAuditVo auditVo);

}
