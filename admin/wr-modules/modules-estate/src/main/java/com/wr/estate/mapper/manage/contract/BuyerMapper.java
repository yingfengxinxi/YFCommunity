package com.wr.estate.mapper.manage.contract;


import com.wr.remote.estate.manage.Staff;
import com.wr.remote.estate.manage.contract.Estate;
import com.wr.remote.estate.manage.vo.contract.BuyerVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/17 14:46
 * @Desc: 采购合同
 */
@Mapper
public interface BuyerMapper {

    List<BuyerVo> getBuyerList(BuyerVo buyerVo);

    Integer add(BuyerVo buyerVo);

    List<Estate> getEstateList(Long estateId);


    int deleteBuyerByIds(Long[] procurementId);

    BuyerVo getBuyerById(Long procurementId);

    /**
     * @Description:
     * @Param:  根据物业id查询采购人员id
     * @Return: java.lang.Long
     * @Author: chenlin@itonghui.org
     * @Date 2022/11/21 16:31
     */
    List<Long> getProcurementId(Long estateId);

    List<Staff> getStaffListById();

    List<Staff> getStaffList(Long estateId);
}
