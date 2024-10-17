package com.wr.remote.estate.customer.secondhand;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-26 18:13:31
 * @Desc:
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("二手交易")
@TableName(value = "xccj_community.bus_second_hand")
public class SecondHand extends BaseEntity {

    private static final long serialVersionUID = 5380318867835855898L;

    /**
     * 主键
     */
    private String id;

    /**
     * 小区id
     */
    private Long communityId;


    /**
     * 缩略图
     */
    private String thumbnail;

    @TableLogic
    private String delFlag;
}
