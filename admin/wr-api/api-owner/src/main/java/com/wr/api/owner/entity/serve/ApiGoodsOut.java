package com.wr.api.owner.entity.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 物品出门表
 * @Author: lvzy
 * @Date: 2022-11-15 10:55:03
 * @Desc:物品出门表
 */
@Data
public class ApiGoodsOut extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -9034687045264462783L;

    /**
     * 主键
     */
    private Long outId;

    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    /**
     * 出门标题
     */
    private String outTitle;

    /**
     * 账号id，表bus_account(account_type=0,1)
     */
    private Long accountId;

    /**
     * 提交时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;

    /**
     * 出门时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date outTime;

    /**
     * 状态：0=未出门，1=已出门
     */
    private String outStatus;

    /**
     * 出入口id，表bus_passage
     */
    private Long passageId;

    /**
     * 操作人id，表bus_staff
     */
    private Long staffId;

    /**
     * 出门说明
     */
    private String remark;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
}