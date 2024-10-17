package com.wr.estate.mapper.manage.contract;


import com.wr.remote.estate.manage.contract.Account;
import com.wr.remote.estate.manage.vo.contract.LeaseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/12 14:46
 * @Desc: 租赁合同管理的controller
 */
@Mapper
public interface LeaseMapper {
    /**
     * 查询租赁列表信息
     *
     * @param leaseVo
     * @return
     */
    List<LeaseVo> getLeaseList(LeaseVo leaseVo);

    Integer leaseAdd(LeaseVo leaseVo);

    int deleteLeaseByIds(Long[] leaseId);

    LeaseVo selectOneLeaseById(@Param("contractId") Long contractId);

    /**
     * 查询租客是否已存在
     *
     * @param cardNo 身份证号
     * @return
     */
    Integer checkLeaseExist(@Param("cardNo") String cardNo);

    /**
     * 查询租客手机号是否已经存在
     *
     * @param tenantPhone
     * @return
     */
    Integer checkLeaseExistByPhone(@Param("tenantPhone") String tenantPhone);

    /**
     * 检测当前租客是否已经注册
     *
     * @param tenantPhone
     * @return
     */
    Account checkTenantIsRegisterByPhone(String tenantPhone);

    /**
     * 租客注册
     *
     * @param account
     */
    void addTenantAccount(Account account);

    /**
     * @param communityId
     * @param roomId
     * @return
     */
    Integer getCkeckRoom(@Param("communityId") Long communityId, @Param("roomId") Long roomId);

}
