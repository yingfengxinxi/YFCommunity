package com.wr.remote.estate.financial;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: bajie
 * @create: 2022/11/2
 * @Description:
 * @FileName: ParkDuration
 * @History:
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("车位缴费规则")
@TableName(value = "xccj_community.bus_park_duration")
public class ParkDurationEntity extends BaseEntity {

    private String id;

    private Long estateId;
    private Long communityId;

    @TableField(exist = false)
    private String communityName;

    //租赁购买0=租1=买2=限时
    private String leasePurchase;

    //购买套餐0=包年1=包月(租车位时使用)
    private String purchasePackage;

    private Integer minHour;

    private Integer maxHour;

    //费用
    private BigDecimal hourFee;

    //删除
    @TableLogic
    private String delFlag;


}
