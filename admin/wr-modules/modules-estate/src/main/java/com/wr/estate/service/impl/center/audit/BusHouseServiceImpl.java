package com.wr.estate.service.impl.center.audit;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.BusHouse;
import com.wr.estate.entity.BusOwner;
import com.wr.estate.entity.vo.OwneAuditVo;
import com.wr.estate.mapper.center.audit.BusHouseMapper;
import com.wr.estate.service.center.audit.BusHouseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-14 19:56:20
 * @Desc: 房产审核实现类
 */
@Service
public class BusHouseServiceImpl implements BusHouseService {
    @Resource
    private BusHouseMapper busHouseMapper;

    @Override
    public List<OwneAuditVo> listOwnerAuditQure(BusOwner busOwner) {
        return busHouseMapper.selectListOwnerAudit(busOwner);
    }

    @Override
    public void deleteConfigByIds(Long[] houseIds) {
        for (Long houseId : houseIds) {
            busHouseMapper.deleteByPrimaryKeys(houseId, SecurityUtils.getUsername());
        }
    }

    @Override
    public OwneAuditVo selectOneOwneAuditVo(Long houseId) {
        Long userId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        return busHouseMapper.selectoneOwneAuditVo(houseId, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult passAndRejection(OwneAuditVo auditVo) {
        Long houseId = auditVo.getHouseId();
        String auditStatus = auditVo.getAuditStatus();
        String rejectReason = auditVo.getRejectReason();
        Long roomId = auditVo.getRoomId();
        // 校验房产是否存在
        Integer count = busHouseMapper.selectHouseByRoomId(roomId);
        if (count > 0){
            return AjaxResult.error("审核失败，该房产已绑定业主！");
        }
        int i = busHouseMapper.updataByownerIdAndAuditStatus(houseId, auditStatus, rejectReason, SecurityUtils.getUsername());
//        if (i > 0 && "1".equals(auditStatus)) {
//            //BusHouse busHouse = busHouseMapper.selectByPrimaryKey(houseId);
//           // int statua = busHouseMapper.updateRoomcheckStatua(busHouse.getRoomId(), busHouse.getOwnerId());
//            return statua > 0 ?  : AjaxResult.error();
//        }
        return AjaxResult.success();
    }

}
