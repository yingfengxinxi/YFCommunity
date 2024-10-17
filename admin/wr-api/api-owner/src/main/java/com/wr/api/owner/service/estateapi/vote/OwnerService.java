package com.wr.api.owner.service.estateapi.vote;


import com.wr.remote.estate.manage.vo.contract.OwnerVo;
import com.wr.remote.estate.manage.vo.contract.SendOwnerVo;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/12 14:46
 * @Desc: 业主
 */
public interface OwnerService {
    /**
     * 获取owner列表
     * @param ownerVo
     * @return
     */
    List<OwnerVo> getOwnerVo(OwnerVo ownerVo);

    /**
     *
     * @return
     */
    List<SendOwnerVo> getAllOwnerInfo();

    /**
     *
     * @return
     */
    List<SendOwnerVo> getOwnerEstateAllList();

    /**
     *
     * @param ownerId
     * @return
     */
    Long getByOwnerIdAccountId(Long ownerId);

    /**
     *
     * @param ownerPhone
     * @return
     */
    Long getByOwnerPhoneAccountId(String ownerPhone);
}
