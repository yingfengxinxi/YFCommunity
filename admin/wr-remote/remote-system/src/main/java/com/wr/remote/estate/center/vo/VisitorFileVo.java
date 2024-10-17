package com.wr.remote.estate.center.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-10-14 10:06:19
 * @Desc: 访客 excel
 */
@Data
public class VisitorFileVo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long visitorId;

    @Excel(name = "访客姓名")
    private String visitorName;

    @Excel(name = "访客手机号码")
    private String visitorPhone;

    @Excel(name = "拜访地址")
    private String address;

    @Excel(name = "访客人数")
    private Integer withNum;

    @Excel(name = "拜访时间", dateFormat = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    @Excel(name = "有效时间", dateFormat = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;


    @Excel(name = "拜访业主姓名")
    private String ownerName;

    @Excel(name = "拜访业主手机号")
    private String ownerPhone;


    private String cardNo;

    private String phone;
    private Long communityId;
    private String name;
    private Long[] ids;
}
