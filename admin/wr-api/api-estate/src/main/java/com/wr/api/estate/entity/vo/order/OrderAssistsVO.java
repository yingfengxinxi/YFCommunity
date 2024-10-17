package com.wr.api.estate.entity.vo.order;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 工单协助人员对象
 *
 * @author 奔波儿灞
 * @date 2022-10-27
 */
@Data
public class OrderAssistsVO implements Serializable {
    private static final long serialVersionUID = 650284525847279067L;

    /**
     * 协助人id
     */
    private Long staffId;

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
     * 协助时间
     */
    private Date assistTime;

}
