package com.wr.api.owner.entity.home.location;

import lombok.Data;

import java.io.Serializable;

/**
 * 工单协助人员表
 * @Author: lvzy
 * @Date: 2022-12-03 17:38:51
 * @Desc:工单协助人员表
 */
@Data
public class ApiOrderAssist implements Serializable {

    /**
     * 请求协助审核id，表xccj_work.bus_order_audit（audit_type=1）
     */
    private Long auditId;

    /**
     * 协助人员id，表xccj_community.bus_staff
     */
    private Long staffId;

    private static final long serialVersionUID = 1L;
}