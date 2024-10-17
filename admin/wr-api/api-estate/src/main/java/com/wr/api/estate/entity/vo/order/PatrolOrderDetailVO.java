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
public class PatrolOrderDetailVO extends OrderDetailVO implements Serializable {
    private static final long serialVersionUID = -4327177548210424494L;

    /**
     * 巡查类型
     */
    private String patrolType;

    /**
     * 工单来源：0=计划生成，1=后台添加，2=其他工单
     */
    private String orderSource;

    /**
     * 巡查点
     */
    private String pointName;

    /**
     * 巡查点位置
     */
    private String pointAddress;

    /**
     * 其他工单编号
     */
    private String otherOrderNo;

    /**
     * 是否已签到，1已签到，0未签到
     */
    private Integer hasSigIn;

    /**
     * 异常图片
     */
    private List<String> abnormalImages;
}
