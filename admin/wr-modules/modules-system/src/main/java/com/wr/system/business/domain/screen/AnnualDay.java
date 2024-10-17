package com.wr.system.business.domain.screen;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: LiuBei
 * @Date: 2022-01-05 16:18:38
 * @Desc:
 */
public class AnnualDay implements Serializable {
    private static final long serialVersionUID = 1205577622697141949L;
    List<AnnualMaintenanceEquipmentVo> maintenanceEquipmentVos;

    public List<AnnualMaintenanceEquipmentVo> getMaintenanceEquipmentVos() {
        return maintenanceEquipmentVos;
    }

    public void setMaintenanceEquipmentVos(List<AnnualMaintenanceEquipmentVo> maintenanceEquipmentVos) {
        this.maintenanceEquipmentVos = maintenanceEquipmentVos;
    }

    @Override
    public String toString() {
        return "AnnualDay{" +
                "maintenanceEquipmentVos=" + maintenanceEquipmentVos +
                '}';
    }
}
