package com.wr.api.estate.entity.vo.order;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-14 20:53:57
 * @Desc: 报事报修工单详情
 */
@Data
public class RepairOrderDetailVO extends OrderDetailVO implements Serializable {
    private static final long serialVersionUID = -4327177548210424494L;

    /**
     * 报事类型
     */
    private String repairName;

    /**
     * 地址
     */
    private String address;

    /**
     * 评价状态：0=未评价，1=已评价
     */
    private String appraiseStatus;

    /**
     * 评价星级：0~5
     */
    private Integer appraiseStar;

    /**
     * 评价备注
     */
    private String appraiseRemark;

    /**
     * 支付状态
     */
    private Integer appointStatus;

    /**
     * 支付金额
     */
    private Double payAmount;

    /**
     * 图片列表
     */
    private List<String> images;

    /**
     * 工单备注
     */
    private String orderRemark;

    /**
     * 评价备注
     */
    private String remark;

    /**
     * 评价图片
     */
    private String complaintUrl;


}
