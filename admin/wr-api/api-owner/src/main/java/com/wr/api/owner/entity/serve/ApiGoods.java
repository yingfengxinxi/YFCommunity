package com.wr.api.owner.entity.serve;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 物品管理表
 * @Author: lvzy
 * @Date: 2022-11-14 13:44:10
 * @Desc: 物品管理表
 */
@Data
public class ApiGoods extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long goodsId;

    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    /**
     * 物品编号
     */
    private String goodsNo;

    /**
     * 物品名称
     */
    private String goodsName;

    /**
     * 分类id，表bus_classify
     */
    private Long classifyId;

    /**
     * 物品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 图片路径
     */
    private String goodsUrl;

    /**
     * 借用状态：0=空闲中，1=借用中
     */
    private String borrowStatus;

    /**
     * 存放地址
     */
    private String address;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
}