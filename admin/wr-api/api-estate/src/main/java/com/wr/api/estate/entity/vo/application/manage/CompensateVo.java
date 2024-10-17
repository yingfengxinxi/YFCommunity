package com.wr.api.estate.entity.vo.application.manage;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 19:49:53
 * @Desc: CompensateVo
 */
@Data
public class CompensateVo implements Serializable {

    private static final long serialVersionUID = -4453384816738907249L;
    /** 主键*/
    private Long compensateId;
    /** 小区id，表bus_community*/
    private Long communityId;
    /** 物品id*/
    private Long goodsId;
    /**
     * 借用申请ID
     */
    private Long borrowId;
    /** 赔偿人id*/
    private Long accountId;

    /** 赔偿方式：0=现金，1=物品*/
    private String compensateWay;

    /** 赔偿时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date compensateTime;

    /**删除标记：0=未删除，1=已删除*/
    private String delFlag;

}
