package com.wr.api.estate.entity.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 工单详情父对象
 *
 * @author 奔波儿灞
 * @date 2022-10-27
 */
@Data
public class OrderDetailVO implements Serializable {
    private static final long serialVersionUID = 650284525847279067L;

    /**
     * 负责人id
     */
    private Long staffId;

    /**
     * 工单编号
     */
    private String orderNo;

    /**
     * 工单名称
     */
    private String orderName;

    /**
     * 工单类型名称，详见OrderConstants
     */
    private String orderTypeName;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 完成备注
     */
    private String finishRemark;

    /**
     * 完成图片
     */
    private String finishImageUrl;

    /**
     * 执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date executeTime;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /**
     * 订单状态，1待分配，2待接单，3待执行，4转交中，5执行中，6已完成，7超时未完成，8超时完成
     */
    private Integer orderStatus;

    /**
     * 协助人员信息
     */
    private List<OrderAssistsVO> orderAssists;

    /**
     * 转交人信息
     */
    private OrderTransferVO orderTransfer;

    /**
     * 是否是负责人，1是，0否
     */
    private Integer hasCharge;

    /**
     * 是否有协助正在审核，1是，0否
     */
    private Integer hasAssistAudit;
}
