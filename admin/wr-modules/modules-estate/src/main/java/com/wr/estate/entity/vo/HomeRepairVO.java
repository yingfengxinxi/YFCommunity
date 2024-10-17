package com.wr.estate.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: SJiang
 * @Date: 2022-11-04 20:12:02
 * @Desc: 居家维修类型 VO
 */
@Data
public class HomeRepairVO implements Serializable {
    private static final long serialVersionUID = 3576282293047823367L;

    /**
     * 主键id
     */
    private Long repairId;
    /**
     * 名称
     */
    private String repairName;
    /**
     * 状态：0=启用，1=停用
     */
    private String repairStatus;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 定金
     */
    private BigDecimal deposit;
    /**
     * 说明
     */
    private String remark;

    @Override
    public String toString() {
        return "HomeRepairVO{" +
                "repairId=" + repairId +
                ", repairName='" + repairName + '\'' +
                ", repairStatus='" + repairStatus + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
