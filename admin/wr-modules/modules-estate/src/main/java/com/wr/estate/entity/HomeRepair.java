package com.wr.estate.entity;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: SJiang
 * @Date: 2022-11-04 20:07:07
 * @Desc: 居家维修类型实体，表：bus_home_repair
 */
@Data
public class HomeRepair extends BaseEntity {
    private static final long serialVersionUID = 3483173307082871684L;

    /** 主键id */
    private Long repairId;
    /** 物业id */
    private Long estateId;
    /** 名称 */
    private String repairName;
    /** 定金 */
    private BigDecimal deposit;
    /** 状态：0=启用，1=停用 */
    private String repairStatus;

    @Override
    public String toString() {
        return "HomeRepair{" +
                "repairId=" + repairId +
                ", estateId=" + estateId +
                ", repairName='" + repairName + '\'' +
                ", deposit=" + deposit +
                ", repairStatus='" + repairStatus + '\'' +
                '}';
    }
}
