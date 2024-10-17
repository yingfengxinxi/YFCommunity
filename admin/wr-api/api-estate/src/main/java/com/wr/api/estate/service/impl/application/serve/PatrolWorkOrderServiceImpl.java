package com.wr.api.estate.service.impl.application.serve;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.estate.mapper.LoginMapper;
import com.wr.api.estate.mapper.application.FacilitieEquipmentMapper;
import com.wr.api.estate.mapper.application.PatrolWorkOrderMapper;
import com.wr.api.estate.service.LoginService;
import com.wr.api.estate.service.application.serve.ParkOrderService;
import com.wr.api.estate.service.application.serve.PatrolWorkOrderService;
import com.wr.remote.domain.FacilitieEquipment;
import com.wr.remote.domain.PatrolWorkOrder;
import com.wr.remote.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/3/2 14:50
 */
@Service
public class PatrolWorkOrderServiceImpl extends ServiceImpl<PatrolWorkOrderMapper, PatrolWorkOrder> implements PatrolWorkOrderService {

    @Autowired
    private FacilitieEquipmentMapper facilitieEquipmentMapper;


    @Autowired
    private LoginMapper loginMapper;

    /**
     * @param patrolWorkOrder
     * @return
     */
    @Override
    public List<PatrolWorkOrder> getList(PatrolWorkOrder patrolWorkOrder) {

        return baseMapper.getList(patrolWorkOrder);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public PatrolWorkOrder get(String id) {
        PatrolWorkOrder patrolWorkOrder = baseMapper.selectById(id);
        if (StringUtils.isNotEmpty(patrolWorkOrder.getName())) {
            SysUser user = loginMapper.getByUserIdUser(Long.valueOf(patrolWorkOrder.getName()));
            patrolWorkOrder.setName(user.getNickName());
            patrolWorkOrder.setPhone(user.getPhonenumber());
        }

        String facilitieEquipmentId = patrolWorkOrder.getFacilitieEquipmentId();
        FacilitieEquipment facilitieEquipment = facilitieEquipmentMapper.selectById(facilitieEquipmentId);
        patrolWorkOrder.setFacilitieEquipmentNo(facilitieEquipment.getProductNumber());
        patrolWorkOrder.setPatroltAddress(facilitieEquipment.getInstallationLocation());

        return patrolWorkOrder;
    }

    /**
     * @param patrolWorkOrder
     */
    @Override
    public void savePatrolWorkOrder(PatrolWorkOrder patrolWorkOrder) {
        String facilitieEquipmentId = patrolWorkOrder.getFacilitieEquipmentId();
        FacilitieEquipment facilitieEquipment = new FacilitieEquipment();
        facilitieEquipment.setId(facilitieEquipmentId);
        facilitieEquipment.setEquipmentStatus(patrolWorkOrder.getEquipmentStatus());
        facilitieEquipment.setFinalInspectionTime(new Date());
        facilitieEquipmentMapper.updateById(facilitieEquipment);

        patrolWorkOrder.setPatrolStatus("1");
        patrolWorkOrder.setUpdateTime(new Date());
        patrolWorkOrder.setDelFlag("0");
        baseMapper.updateById(patrolWorkOrder);
    }

    /**
     * @param patrolWorkOrder
     */
    @Override
    public void updatePatrolContent(PatrolWorkOrder patrolWorkOrder) {
        PatrolWorkOrder workOrder = baseMapper.selectById(patrolWorkOrder.getId());
        workOrder.setPatrolContent(patrolWorkOrder.getPatrolContent());
        baseMapper.updateById(workOrder);
    }
}
