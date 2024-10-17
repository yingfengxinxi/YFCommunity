package com.wr.api.owner.entity.home.location;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 居家维修类型表
 * @Author: lvzy
 * @Date: 2022-12-01 14:40:54
 * @Desc:居家维修类型表
 */
@Data
public class ApiHomeRepair implements Serializable {
    /**
    * 主键
    */
    private Long repairId;

    /**
    * 物业id，表xccj_estate_sys.bus_estate
    */
    private Long estateId;

    /**
    * 名称
    */
    private String repairName;

    /**
    * 定金
    */
    private BigDecimal deposit;

    /**
    * 状态：0=启用，1=停用
    */
    private String repairStatus;

    /**
    * 说明
    */
    private String remark;

    /**
    * 创建人
    */
    private String createBy;

    /**
    * 创建时间（发布时间）
    */
    private Date createTime;

    /**
    * 修改人
    */
    private String updateBy;

    /**
    * 修改时间
    */
    private Date updateTime;

    /**
    * 删除标记：0=未删除，1=已删除
    */
    private String delFlag;

    private static final long serialVersionUID = 1L;
}