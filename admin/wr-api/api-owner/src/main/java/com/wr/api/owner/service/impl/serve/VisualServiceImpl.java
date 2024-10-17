package com.wr.api.owner.service.impl.serve;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.api.owner.mapper.estateapi.pay.CommunityMapper;
import com.wr.api.owner.mapper.serve.VisualDeviceControlMapper;
import com.wr.api.owner.mapper.serve.VisualMapper;
import com.wr.api.owner.service.serve.VisualService;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.util.CommunityHik;
import com.wr.common.customize.util.HaiKUtils;
import com.wr.remote.aiot.Visual;
import com.wr.remote.aiot.VisualDeviceControl;
import com.wr.remote.aiot.VisualSecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/15 12:01
 */
@Service
public class VisualServiceImpl implements VisualService {

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private VisualDeviceControlMapper visualDeviceControlMapper;

    @Autowired
    private VisualMapper visualMapper;

    /**
     *
     * @param communityId
     * @param buildingId
     * @param unitId
     * @return
     */
    @Override
    public List<Visual> getList(Long communityId, Long buildingId, Long unitId) {

        return visualMapper.getList(communityId, buildingId, unitId);
    }

    /**
     * @param vdc
     * @return
     * @throws Exception
     */
    @Override
    public AjaxResult visualDeviceControl(VisualDeviceControl vdc) throws Exception {
        CommunityHik hik = communityMapper.selectHikByCommunityId(vdc.getCommunityId());
        if (null == hik) {

            return AjaxResult.error("请检查小区海康配置后重试!");
        }
        Integer controlValue = 2;
        JSONObject jsonObject = HaiKUtils.visualDeviceControl(hik, vdc.getDeviceIndexCode(), controlValue);
        if (jsonObject != null) {
            VisualDeviceControl visualDeviceControl = JSONObject.parseObject(jsonObject.toJSONString(), VisualDeviceControl.class);
            visualDeviceControl.setCommunityId(vdc.getCommunityId());
            visualDeviceControl.setBuildingId(vdc.getBuildingId());
            visualDeviceControl.setUnitId(vdc.getUnitId());
            visualDeviceControl.setRoomId(vdc.getRoomId());
            visualDeviceControl.setOwnerId(vdc.getOwnerId());
            visualDeviceControl.setCreateTime(new Date());
            visualDeviceControlMapper.insert(visualDeviceControl);
            //-1:开门失败 1:开门成功 2:参数非法3:Das地址为空 4:设备无权限
            if (visualDeviceControl.getControlResultCode() == -1) {
                return AjaxResult.success("开门失败");
            }
            if (visualDeviceControl.getControlResultCode() == 1) {
                return AjaxResult.success("开门成功");
            }
            if (visualDeviceControl.getControlResultCode() == 2) {
                return AjaxResult.success("参数非法");
            }
            if (visualDeviceControl.getControlResultCode() == 3) {
                return AjaxResult.success("Das地址为空");
            }
            if (visualDeviceControl.getControlResultCode() == 4) {
                return AjaxResult.success("设备无权限");
            }
        }

        return AjaxResult.error("开门失败");


    }
}
