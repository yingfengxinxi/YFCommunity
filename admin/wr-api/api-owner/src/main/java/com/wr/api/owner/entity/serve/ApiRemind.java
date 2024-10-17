package com.wr.api.owner.entity.serve;

import lombok.Data;

import java.io.Serializable;

/**
 * 借用提醒记录表
 * @Author: lvzy
 * @Date: 2022-11-14 20:12:55
 * @Desc: 借用提醒记录表
 */
@Data
public class ApiRemind implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long remindId;

    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    /**
     * 数据类型：0=物品借用提醒
     */
    private String remindType;

    /**
     * 借用人账号id，表bus_account
     */
    private Long accountId;

    /**
     * 物品id，表bus_goods
     */
    private Long goodsId;

    /**
     * 是否已读：0=未读，1=已读
     */
    private String readStatus;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
}