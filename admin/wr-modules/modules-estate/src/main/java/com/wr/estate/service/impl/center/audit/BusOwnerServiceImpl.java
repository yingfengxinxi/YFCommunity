package com.wr.estate.service.impl.center.audit;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.common.core.utils.DesensitizationUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.BusOwner;
import com.wr.estate.entity.vo.OwneAuditVo;
import com.wr.estate.mapper.center.RoomMapper;
import com.wr.estate.mapper.center.audit.BusHouseMapper;
import com.wr.estate.mapper.center.audit.BusOwnerMapper;
import com.wr.estate.mapper.center.personnelfilemanage.OwnerFileMapper;
import com.wr.estate.service.center.audit.BusOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-13 10:31:24
 * @Desc: 业主审核实现层
 */
@Service
public class BusOwnerServiceImpl implements BusOwnerService {

    @Resource
    private BusOwnerMapper busOwnerMapper;
    @Autowired
    private OwnerFileMapper ownerFileMapper;

    @Autowired
    private BusHouseMapper houseMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<OwneAuditVo> listOwnerAuditQure(BusOwner busOwner) {
        List<OwneAuditVo> listOwnerAudit = busOwnerMapper.getListOwnerAudit(busOwner);
        if (CollectionUtils.isNotEmpty(listOwnerAudit)) {
            listOwnerAudit.stream().forEach(owneAuditVo -> {
                owneAuditVo.setOwnerName(DesensitizationUtils.name(owneAuditVo.getOwnerName()));
                owneAuditVo.setOwnerPhone(DesensitizationUtils.phone(owneAuditVo.getOwnerPhone()));
                if (StringUtils.equals(owneAuditVo.getCardType(), "I")) {
                    owneAuditVo.setCardNo(DesensitizationUtils.phone(owneAuditVo.getCardNo()));
                }
            });
        }
        return listOwnerAudit;
    }

    @Override
    public void deleteConfigByIds(Long[] ownerIds) {

        for (Long ownerId : ownerIds) {
            houseMapper.deleteByOwnerIdHouse(ownerId);
            busOwnerMapper.deleteByPrimaryKeys(ownerId, SecurityUtils.getUsername());
        }
    }

    @Override
    public OwneAuditVo selectOneOwneAuditVo(Long ownerId, Long roomId) {
        OwneAuditVo owneAuditVo = busOwnerMapper.selectoneOwneAuditVo(ownerId, roomId);
        if(owneAuditVo!=null){
            owneAuditVo.setOwnerName(DesensitizationUtils.name(owneAuditVo.getOwnerName()));
            owneAuditVo.setOwnerPhone(DesensitizationUtils.phone(owneAuditVo.getOwnerPhone()));
            if(StringUtils.equals(owneAuditVo.getCardType(),"I")){
                owneAuditVo.setCardNo(DesensitizationUtils.cardNo(owneAuditVo.getCardNo()));
            }
        }
        return owneAuditVo;
    }

    @Override
    @Transactional
    public void passAndRejection(Long roomId, Long ownerId, String auditStatus, String rejectReason) {
        //判断当前业主是否已经审核通过
        Integer ownerCount = busOwnerMapper.getByOwnerIdCount(ownerId);
        if (ownerCount <= 0) {
            //修改业主审核状态
            busOwnerMapper.updataByownerIdAndAuditStatus(ownerId, auditStatus, rejectReason, SecurityUtils.getUsername());
        }
        //修改房屋状态
        houseMapper.updataByOwnerIdAuditStatus(roomId, ownerId, auditStatus, rejectReason, SecurityUtils.getUsername());
        if ("1".equals(auditStatus)) {
            BusOwner busOwner = busOwnerMapper.selectByPrimaryKey(ownerId);
            String ownerRelation = busOwner.getOwnerRelation();
            if (ownerRelation.equals("0")) {
                //修改房屋业主id
                roomMapper.updateByRoomIdOwnerId(ownerId, roomId);
            }

            //修改账号状态
            busOwnerMapper.updateaccountType(busOwner.getAccountId(), SecurityUtils.getUsername());
            // 房间居住数+1
            Integer roomIdCount = houseMapper.getRoomIdCount(roomId);
            //修改房间人数
            ownerFileMapper.updateByRoomIdLiveNum(roomIdCount, roomId);
        }
    }
}
