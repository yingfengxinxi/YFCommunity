package com.wr.estate.entity.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: lvzy
 * @Date: 2022-11-03 11:58:12
 * @Desc: 工单执行人信息
 */
@Data
@ToString
public class Executor {
    /**
     * 执行人ID
     */
    private Long staffId;


    /**
     * 执行人姓名
     */
    private String staffName;

    /**
     * 执行人手机号
     */
    private String staffPhone;

    /**
     * 执行人身份证号
     */
    private String cardNo;


    /**
     * 主要负责人
     */
    private String mainStaffName;

    /**
     * 人员类别
     */
    private String personnelType;

}
