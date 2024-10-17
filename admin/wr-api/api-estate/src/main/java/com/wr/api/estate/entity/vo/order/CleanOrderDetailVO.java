package com.wr.api.estate.entity.vo.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-14 20:53:57
 * @Desc: 报事报修工单详情
 */
@Data
public class CleanOrderDetailVO extends OrderDetailVO implements Serializable {
    private static final long serialVersionUID = -4327177548210424494L;

    /**
     * 地址
     */
    private String address;

}
