package com.wr.api.estate.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 借用归还
 *
 * @Author RainCity
 * @Date 2023-06-06 17:26:29
 */
@Data
public class BorrowDTO implements Serializable {
    private static final long serialVersionUID = -6936206184869274124L;
    /**
     * 借用人账号
     */
    private Long accountId;

    /**
     * 借用申请ID
     */
    private Long borrowId;
    /**
     * 物品id
     */
    private Long goodsId;
}
