package com.wr.estate.entity.vo;

import com.wr.remote.work.inspect.OrderAudit;
import lombok.Data;

/**
 * @Author: lvzy
 * @Date: 2022-11-03 17:01:32
 * @Desc:
 */
@Data
public class OrderAuditSanitaTionVo extends OrderAudit {
    /**
     * 项目ID
     */
    private Long communityId;

    /**
     * 工单编号
     */
    private String cleanNo;

    /**
     * 工单名称
     */
    private String cleanName;

    /**
     * 申请人
     */
    private String applyName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 接收人姓名
     */
    private String executeName;

    /**
     * 接收人手机号
     */
    private String executePhone;

}
