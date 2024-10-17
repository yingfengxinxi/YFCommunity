package com.wr.estate.service.manage.contract;


import com.wr.remote.estate.manage.Staff;
import com.wr.remote.estate.manage.contract.Estate;
import com.wr.remote.estate.manage.vo.contract.BuyerVo;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/17 14:46
 * @Desc: 采购合同
 */
public interface BuyerService {


    /**
     * 获取采购列表
     *
     * @param buyerVo
     * @return
     */
    List<BuyerVo> getBuyerList(BuyerVo buyerVo);

    Integer add(BuyerVo buyerVo);

    List<Estate> getEstateList();


    int deleteBuyerByIds(Long[] procurementId);

    BuyerVo getBuyerById(Long procurementId);

    List<Staff> getStaffList(Long estateId);
}
