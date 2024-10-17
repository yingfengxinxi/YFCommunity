package com.wr.remote.work.patrol.vo;

import com.wr.common.core.annotation.Excel;

/**
 * 协助人员
 *
 * @Author: 奔波儿灞
 * @Date: 2022-11-03 16:31:15
 * @Desc:
 */
public class AssistStaffListVO {

    /**
     * 员工姓名
     */
    private String staffName;

    /**
     * 账号/手机号
     */
    private String staffPhone;

    /**
     * 身份证号码
     */
    private String cardNo;

    /**
     * 岗位
     */
    private String workPost;

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getWorkPost() {
        return workPost;
    }

    public void setWorkPost(String workPost) {
        this.workPost = workPost;
    }

    @Override
    public String toString() {
        return "AssistStaffListVO{" +
                "staffName='" + staffName + '\'' +
                ", staffPhone='" + staffPhone + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", workPost='" + workPost + '\'' +
                '}';
    }
}
