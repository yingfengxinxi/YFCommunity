package com.wr.remote.work.vo;

import com.wr.remote.work.inspect.OrderAudit;

/**
 * 工单协助审核扩展类
 * @Author: lvzy
 * @Date: 2022-10-31 20:01:57
 * @Desc: 工单协助审核扩展类
 */
public class OrderAuditVo extends OrderAudit {

    private static final long serialVersionUID = 2845479315371533335L;

    /**
     * 项目ID
     */
    private Long communityId;

    /**
     * 工单编号
     */
    private String inspectNo;

    /**
     * 工单名称
     */
    private String inspectName;

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

    public String getExecuteName() {
        return executeName;
    }

    public void setExecuteName(String executeName) {
        this.executeName = executeName;
    }

    public String getExecutePhone() {
        return executePhone;
    }

    public void setExecutePhone(String executePhone) {
        this.executePhone = executePhone;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getInspectNo() {
        return inspectNo;
    }

    public void setInspectNo(String inspectNo) {
        this.inspectNo = inspectNo;
    }

    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
