package com.wr.remote.work.Disaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: xuShu
 * @Date: 2022-11-02 09:17:28
 * @Desc: 灾害报事转交记录表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusOrderTransfer implements Serializable {


    /**
     * 主键
     */
    private Long transferId;

    /**
     * 工单类型
     */
    private String orderType;

    /**
     * 工单id
     */
    private Long orderId;

    /**
     * 转交人id
     */
    private Long sourceId;

    /**
     * 接收人id
     */
    private Long targetId;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 接受状态
     */
    private String acceptType;

    /**
     * 不同意理由
     */
    private String rejectReason;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除标记
     */
    private String delFlag;
}
