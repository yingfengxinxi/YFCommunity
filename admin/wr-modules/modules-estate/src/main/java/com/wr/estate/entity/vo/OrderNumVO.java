package com.wr.estate.entity.vo;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 *  index 查询工单数
 * @author DIANWEI
 */
@Data
public class OrderNumVO {

    /**
     * 工单名称
     */
    private String orderName;
    /**
     * 工单数
     */
    private Long orderNum;
}
