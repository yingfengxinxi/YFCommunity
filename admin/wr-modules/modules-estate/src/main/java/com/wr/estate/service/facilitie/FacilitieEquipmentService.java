package com.wr.estate.service.facilitie;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.FacilitieEquipment;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/26 21:16
 */
public interface FacilitieEquipmentService extends IService<FacilitieEquipment> {

    /**
     *
     * @param facilitieEquipment
     * @return
     */
    public String getQrCode(FacilitieEquipment facilitieEquipment);

    /**
     *
     * @param dataList
     * @param communityId
     * @return
     */
    String importData(List<FacilitieEquipment> dataList, Long communityId);
}
