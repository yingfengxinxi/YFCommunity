package com.wr.estate.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/2/20 14:59
 */
@Data
public class TenantFileDto extends BaseEntity {

    //租客id
    private Long tenantId;
    //小区Id
    private Long communityId;
    private String communityName;
    //楼栋Id
    private Long buildingId;
    private String buildingName;
    //单元Id
    private Long unitId;
    private String unitName;
    //房号Id
    private Long roomId;
    private String roomName;
    //业主名称
    private String ownerId;
    private String ownerName;
    private String roomStatus;
    //业主手机号
    private String ownerPhone;
    //业主证件号
    private String ownerCardNo;
    //房屋坐落
    private String houseAddress;
    //房屋面积
    private String roomArea;
    //租客姓名
    private String tenantName;
    //租客手机号
    private String tenantPhone;
    //租客性别
    private String gender;
    //租客证件类型
    private String cardType;
    //租客证件号码
    private String cardNo;
    //租客正面身份证图片
    private String cardFont;
    //租客反面身份证图片
    private String cardBack;
    //租客户籍详细地址
    private String domicileAddress;
    //租赁开始时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date leaseStartTime;
    //租赁结束时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date leaseEndTime;
    //押金付款时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date depositPayTime;
    //租赁总价
    private BigDecimal totalMoney;
    //押金金额
    private BigDecimal deposit;
    //房产证
    private String licenseUrl;
    //租房合同
    private String rentContract;
    //其他
    private String other;
    //创建人
    private String by;
    //账号id
    private Long accountId;
    private String state;
}
