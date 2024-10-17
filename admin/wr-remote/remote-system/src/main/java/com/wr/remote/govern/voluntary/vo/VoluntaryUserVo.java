package com.wr.remote.govern.voluntary.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 志愿者表 excel
 *
 * @author lvzy
 * @since 2022-10-24 17:10:28
 */
@Data
public class VoluntaryUserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "姓名")
    private  String userName;

    @Excel(name = "所属小区")
    private  String communityName;

    @Excel(name = "参与活动")
    private  String actTitle;

    @Excel(name = "性别",readConverterExp = "0=男,1=女,2=未知")
    private String gender;

    @Excel(name = "年龄")
    private  Integer userAge;

    @Excel(name = "身份证号码")
    private String card_no;

    @Excel(name = "证件类型")
    private String dictLabel;

    @Excel(name = "联系电话")
    private String userPhone;

    @Excel(name = "申请时间")
    private String applyTime;
}

