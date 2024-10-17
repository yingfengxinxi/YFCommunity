package com.wr.api.owner.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: SJiang
 * @Date:   2023-01-04 15:03:07
 * @Desc:   租房合同 DTO
 */
@Data
public class RentFileDTO implements Serializable {
    private static final long serialVersionUID = 8039139921608623115L;

    /**
     * 小区id
     */
    private Long communityId;
    /**
     * 楼栋id
     */
    private Long buildingId;
    /**
     * 单元id
     */
    private Long unitId;
    /**
     * 房间id
     */
    private Long roomId;
    /**
     * 业主id
     */
    private Long ownerId;
    /**
     * 租客id
     */
    private Long tenantId;
    /**
     * 账号id
     */
    private Long accountId;
    /**
     * 租客姓名
     */
    private String tenantName;
    /**
     * 租客手机号
     */
    private String tenantPhone;
    /**
     * 性别
     */
    private String gender;
    /**
     * 身份类型
     */
    private String cardType;
    /**
     * 证件号码
     */
    private String cardNo;
    /**
     * 身份证正面
     */
    private String cardFont;
    /**
     * 身份证反面
     */
    private String cardBack;
    /**
     * 人脸
     */
    private String faceUrl;
    /**
     * 户籍地址
     */
    private String domicileAddress;
    /**
     * 月租金
     */
    private BigDecimal monthRent;
    /**
     * 合同开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date contractStart;
    /**
     * 合同结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date contractEnd;
    /**
     * 合同编号
     */
    private String contractNo;
    /**
     * 合同文件路径
     */
    private String contractUrl;
    /**
     * 创建人
     */
    private String createBy;

}
