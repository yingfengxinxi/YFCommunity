package com.wr.estate.service.manage.contract;


import com.wr.remote.estate.manage.vo.contract.LeaseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/12 14:46
 * @Desc: 租赁合同管理的controller
 */
public interface LeaseService {

    /**
     * 查询列表信息
     * @param leaseVo
     * @return
     */
    List<LeaseVo> getLeaseList(LeaseVo leaseVo);

    /**
     * 插入租赁合同
     * @param leaseVo
     * @return
     */
    Integer leaseAdd(LeaseVo leaseVo);

    int deleteLeaseByIds(Long[] leaseId);

    LeaseVo selectOneLeaseById(Long contractId);

    /**
     * 查询租客是否已存在
     * @param cardNo 身份证号
     * @return
     */
    boolean checkLeaseExist(String cardNo);


    boolean checkLeaseExistByPhone(String tenantPhone);


    /**
     *
     * @param communityId
     * @param roomId
     * @return
     */
    Integer getCkeckRoom(Long communityId,Long roomId);


}
