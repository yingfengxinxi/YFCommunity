package com.wr.remote.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: bajie
 * @create: 2022/11/3
 * @Description:
 * @FileName: PropertyFeeEntity
 * @History:
 */
@Data
public class PropertyFeeEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    //主键
    private String propertyFeeId;

    //物业id
    private Long estateId;
    /**
     * 小区id
     */
    @Excel(name = "小区id")
    private Long communityId;
    //楼栋id
    private String buildingId;
    //生成的订单号
    private String orderNo;


    @Excel(name = "小区名")
    private String communityName;

    /**
     * 收费状态 0=启用1=停用
     */
    @Excel(name = "收费状态")
    private String feeStatus;
    //房屋属性
    private String roomType;

    //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    //结束时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 收费类型 收费类型：0=按住宅面积收费，1=统一收费
     */
    @Excel(name = "收费类型")
    private String feeType;
    //字典值pay_method缴费方式:0=按月缴费1=按季缴费2=按半年缴费3=按年缴费
    private String payMethod;

    /**
     * 费用
     */
    @Excel(name = "费用")
    private BigDecimal feeAmount;

    //是否可以修改规则
    private String isUpdate;

}
