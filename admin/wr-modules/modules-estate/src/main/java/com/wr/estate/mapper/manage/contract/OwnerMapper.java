package com.wr.estate.mapper.manage.contract;


import com.wr.remote.estate.manage.vo.contract.OwnerVo;
import com.wr.remote.estate.manage.vo.contract.SendOwnerVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/12 14:46
 * @Desc: 业主
 */
@Mapper
public interface OwnerMapper {

    /**
     * 获取owner列表
     *
     * @param ownerVo
     * @return
     */
    List<OwnerVo> getOwnerVo(OwnerVo ownerVo);

    /**
     * @return
     */
    List<SendOwnerVo> getAllOwnerInfo();

    /**
     * @return
     */
    List<SendOwnerVo> getOwnerEstateAllList();

    /**
     *
     * @param ownerId
     * @param communityId
     * @return
     */
    Long getByOwnerIdAccountId(@Param("ownerId") Long ownerId,@Param("communityId") Long communityId);
    Long getByAccountIdOwnerId(@Param("accountId") Long accountId);

    /**
     *
     * @param ownerPhone
     * @return
     */
    Long getByOwnerPhoneAccountId(@Param("ownerPhone") String ownerPhone);
}
