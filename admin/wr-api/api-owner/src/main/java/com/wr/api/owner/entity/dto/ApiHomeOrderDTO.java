package com.wr.api.owner.entity.dto;

import com.wr.api.owner.entity.home.location.ApiHomeOrder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 园区报事工单接收
 * @Author: lvzy
 * @Date: 2022-12-02 09:35:21
 * @Desc:园区报事工单接收
 */
@Data
public class ApiHomeOrderDTO extends ApiHomeOrder implements Serializable {

    private static final long serialVersionUID = -8635027106424348518L;

    /**
     * 图片类型：0=居家维修工单,1=园区报事申请,2灾害报事申请
     */
    private String imageType;

    /**
     * 图片组
     */
    private List<String> imageList;
}
