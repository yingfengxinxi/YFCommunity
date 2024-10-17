package com.wr.api.estate.service.impl.application.info;


import com.wr.api.estate.entity.vo.application.info.TenantVo;
import com.wr.api.estate.entity.vo.application.serve.OwnerInfoVo;
import com.wr.api.estate.entity.vo.application.serve.RentContractVo;
import com.wr.api.estate.mapper.application.info.OwnerMapper;
import com.wr.api.estate.mapper.application.info.RoomMapper;
import com.wr.api.estate.mapper.application.info.TenantMapper;
import com.wr.api.estate.service.application.info.TenantService;
import com.wr.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 14:06:29
 * @Desc: 业务处理层
 */
@Service
public class TenantServiceImpl implements TenantService {
    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private OwnerMapper ownerMapper;

    @Autowired
    private RoomMapper roomMapper;

    /**
     * 列表
     *
     * @param tenantVo
     * @return
     */
    @Override
    public List<TenantVo> getList(TenantVo tenantVo) {
        return tenantMapper.getList(tenantVo);
    }

    /**
     * 详情
     *
     * @param tenantVo
     * @return
     */
    @Override
    public AjaxResult getDetail(TenantVo tenantVo) {
        AjaxResult ajaxResult = AjaxResult.success();
        //租客信息
        TenantVo tenantDetail = tenantMapper.getDetail(tenantVo);
        //业主信息
        Long roomId = tenantDetail.getRoomId();
        Long ownerId = roomMapper.getOwner(roomId).getOwnerId();
        //业主信息
        OwnerInfoVo ownerDetail = ownerMapper.getOwnerDetail(ownerId);
        if (ownerDetail != null) {
            //查询交房时间
            Date deliveryTime = roomMapper.getByRoomIdDeliveryTime(roomId);
            if (deliveryTime != null) {
                ownerDetail.setDeliveryTime(deliveryTime);
            }
        }


        //业主
        ajaxResult.put("ownerDetail", ownerDetail);
        //租客
        ajaxResult.put("tenantDetail", tenantDetail);

        return ajaxResult;
    }

    @Override
    public int getTenantCount(Long communityId) {
        return tenantMapper.getTenantCount(communityId);
    }
}
