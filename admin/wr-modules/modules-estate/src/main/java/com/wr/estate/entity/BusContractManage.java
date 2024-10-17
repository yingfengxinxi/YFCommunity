package com.wr.estate.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/2/22 16:51
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("合同管理")
@TableName(value = "xccj_community.bus_contract_manage")
public class BusContractManage extends BaseEntity {

    //	主键
    private String id;
    //物业id
    private Long estateId;
    //小区id，表xccj_community.bus_community
    private Long communityId;
    //合同名称
    private String contractName;
    //	合同编号
    private String contractNo;
    //甲方负责人
    private String personChargeParty1Name;
    //甲方负责人联系方式
    private String personChargeParty1Phone;
    //甲方负责人身份证正面
    private String personChargeParty1CardFont;
    //甲方负责人身份证反面
    private String personChargeParty1CardBack;
    //乙方负责人
    private String personChargeParty2Name;
    //乙方负责人联系方式
    private String personChargeParty2Phone;
    //乙方负责人身份证正面
    private String personChargeParty2CardFont;
    //乙方负责人身份证反面
    private String personChargeParty2CardBack;
    //签约人姓名
    private String contractorName;
    //签约金额
    private BigDecimal contractorMoney;
    //服务开始时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date serviceStartTime;
    //服务结束时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date serviceEndTime;
    //合同附件
    private String signContract;
    //其他附件
    private String other;
    //	删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;

}
