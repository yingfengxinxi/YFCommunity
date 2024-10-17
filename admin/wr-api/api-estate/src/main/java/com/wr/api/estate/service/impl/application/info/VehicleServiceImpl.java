package com.wr.api.estate.service.impl.application.info;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.wr.api.estate.entity.vo.application.info.CarportVo;
import com.wr.api.estate.entity.vo.application.info.VehicleVo;
import com.wr.api.estate.mapper.application.info.VehicleMapper;
import com.wr.api.estate.service.application.info.VehicleService;
import com.wr.api.estate.service.application.serve.CarportNoService;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.CarportNo;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 14:06:29
 * @Desc: 业务处理层
 */
@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleMapper vehicleMapper;

    @Autowired
    private CarportNoService carportNoService;

    /**
     * 列表
     *
     * @param vehicleVo
     * @return
     */
    @Override
    public List<VehicleVo> getList(VehicleVo vehicleVo) {
        return vehicleMapper.getList(vehicleVo);
    }

    /**
     * 详情
     *
     * @param vehicleVo
     * @return
     */
    @Override
    public AjaxResult getDetail(VehicleVo vehicleVo) {
        AjaxResult ajaxResult = AjaxResult.success();

        //车主信息
        VehicleVo driverInfo = vehicleMapper.getDriverInfo(vehicleVo);
        //车辆信息
        List<VehicleVo> vehicleList = this.getVehicleDetail(null, null, vehicleVo.getVehicleId());
        List<String> plateNoList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(vehicleList)) {
            plateNoList = vehicleList.stream().map(VehicleVo::getPlateNo).distinct().collect(Collectors.toList());
        }
        ajaxResult.put("plateNoList", plateNoList);
        ajaxResult.put("vehicleList", vehicleList);
        ajaxResult.put("driverInfo", driverInfo);

        return ajaxResult;
    }

    /**
     * 车辆总数
     *
     * @param communityId
     * @return
     */
    @Override
    public int getVehicleCount(Long communityId) {
        return vehicleMapper.getVehicleCount(communityId);
    }

    /**
     * @param communityId
     * @param accountId
     * @param vehicleId
     * @return
     */
    @Override
    public List<VehicleVo> getVehicleDetail(Long communityId, Long accountId, Long vehicleId) {
        List<VehicleVo> vehicleList = vehicleMapper.getVehicleDetail(communityId, accountId, vehicleId);
        if (CollectionUtils.isNotEmpty(vehicleList)) {
            for (VehicleVo vehicleVo : vehicleList) {
                CarportVo carportVo = vehicleMapper.getVehicleIdCarportInfo(vehicleVo.getVehicleId());
                if (carportVo != null) {
                    String carportNo = carportVo.getCarportNo();
                    CarportNo no = carportNoService.get(Long.valueOf(carportNo));
                    carportVo.setCarportName(no.getCarportNo());
                    vehicleVo.setCarportVo(carportVo);
                }
            }
        }
        return vehicleList;
    }
}
