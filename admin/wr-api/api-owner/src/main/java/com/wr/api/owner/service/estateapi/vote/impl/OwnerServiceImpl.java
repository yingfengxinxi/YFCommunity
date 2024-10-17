package com.wr.api.owner.service.estateapi.vote.impl;


import com.wr.api.owner.mapper.estateapi.vote.OwnerMapper;
import com.wr.api.owner.service.estateapi.vote.OwnerService;
import com.wr.remote.estate.manage.vo.contract.OwnerVo;
import com.wr.remote.estate.manage.vo.contract.SendOwnerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/12 14:46
 * @Desc: 业主
 */
@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerMapper ownerMapper;

    /**
     * 获取owner列表
     *
     * @param ownerVo
     * @return
     */
    @Override
    public List<OwnerVo> getOwnerVo(OwnerVo ownerVo) {
        return ownerMapper.getOwnerVo(ownerVo);
    }

    @Override
    public List<SendOwnerVo> getAllOwnerInfo() {
        return ownerMapper.getAllOwnerInfo();
    }

    /**
     * @return
     */
    @Override
    public List<SendOwnerVo> getOwnerEstateAllList() {
        return ownerMapper.getOwnerEstateAllList();
    }

    @Override
    public Long getByOwnerIdAccountId(Long ownerId) {
        return ownerMapper.getByOwnerIdAccountId(ownerId);
    }

    /**
     * @param ownerPhone
     * @return
     */
    @Override
    public Long getByOwnerPhoneAccountId(String ownerPhone) {
        return ownerMapper.getByOwnerPhoneAccountId(ownerPhone);
    }
}
