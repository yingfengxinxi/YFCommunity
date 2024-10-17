package com.wr.api.estate.entity.vo.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-14 20:53:57
 * @Desc: 报事报修工单详情
 */
@Data
public class DecorateOrderDetailVO extends OrderDetailVO implements Serializable {
    private static final long serialVersionUID = -4327177548210424494L;

    /**
     * 户主姓名
     */
    private String ownerName;

    /**
     * 房号
     */
    private String roomName;

    /**
     * 地址
     */
    private String address;

    /**
     * 评定结果：0=待评定，1=合格，2=违规，3=整改完成
     */
    private String assessResult;
}
