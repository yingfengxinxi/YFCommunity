package com.wr.api.owner.entity.home.location;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: xuShu
 * @Date: 2022-12-02 13:50:49
 * @Desc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordOwnerVo {
    /**
     * 工单类型：见类 OrderConstants
     */
    private String orderType;

    /**
     * 工单id，order_type对应的表
     */
    private Long orderId;

    /**
     * 数据类型：0=其他，1=执行
     */
    private String dataType;

    /**
     * 结果
     */
    private String orderResult;

    /**
     * 提交人姓名
     */
    private String userName;

    /**
     * 相关人员姓名（多人）
     */
    private String relevant;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
