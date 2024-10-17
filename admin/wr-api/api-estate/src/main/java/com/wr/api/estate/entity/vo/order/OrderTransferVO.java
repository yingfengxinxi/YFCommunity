package com.wr.api.estate.entity.vo.order;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 工单转交人员对象
 *
 * @author 奔波儿灞
 * @date 2022-10-27
 */
@Data
public class OrderTransferVO implements Serializable {
    private static final long serialVersionUID = 650284525847279067L;

    /**
     * 工单编号
     */
    private String staffName;

    /**
     * 协助人员手机号
     */
    private String staffPhone;

    /**
     * 协助人员岗位
     */
    private String staffPost;

    /**
     * 转交状态，0=待同意，1=同意，2=不同意
     */
    private Integer acceptStatus;

}
