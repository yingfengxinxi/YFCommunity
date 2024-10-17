package com.wr.api.estate.entity.application.manage;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 13:28:23
 * @Desc: GoodsOut
 */
@Data
public class GoodsOut implements Serializable {

    private static final long serialVersionUID = 7908816953206831093L;
    /*主键*/
    private Long outId;
    /*小区id，表bus_community*/
    private Long communityId;
    /*出门标题*/
    private String outTitle;
    /*账号id，表bus_account(account_type=0,1)*/
    private Long accountId;
    /**提交时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;
    /**出门时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date outTime;
    /*状态：0=未出门，1=已出门*/
    private String outStatus;
    /*出入口id，表bus_passage*/
    private Long passageId;
    /*操作人id，表bus_staff*/
    private Long staffId;
    /*出门说明*/
    private String remark;
    /*删除标记：0=未删除，1=已删除*/
    private String delFlag;

    /*提交人姓名*/
    private String accountName;
    /*出入口名称*/
    private String passageName;
    /*出入口名称*/
    private String loginName;
    /*出门图片*/
    private String imageUrl;

}
