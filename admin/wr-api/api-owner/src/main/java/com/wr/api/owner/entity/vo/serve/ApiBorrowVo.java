package com.wr.api.owner.entity.vo.serve;

import com.wr.api.owner.entity.serve.ApiBorrow;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 借用申请扩展类
 * @Author: lvzy
 * @Date: 2022-11-14 21:47:33
 * @Desc:借用申请
 */
@Data
public class ApiBorrowVo extends ApiBorrow implements Serializable {

    private static final long serialVersionUID = 414477600756484923L;

    /**
     * 图片路径
     */
    private String goodsUrl;

    /**
     * 物品编号
     */
    private String goodsNo;

    /**
     * 物品名称
     */
    private String goodsName;

    /**
     * 分类名称
     */
    private String classifyName;

    /**
     * 物品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 存放地址
     */
    private String address;

    /**
     * 用户名
     */
    private String accountName;

    /**
     * 手机号
     */
    private String loginName;

    /**
     * 楼栋名称
     */
    private String buildingName;

    /**
     * 单元名称
     */
    private String unitName;

    /**
     * 房号
     */
    private String roomName;

}
