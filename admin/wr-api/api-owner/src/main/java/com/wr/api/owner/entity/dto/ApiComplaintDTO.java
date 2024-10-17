package com.wr.api.owner.entity.dto;

import com.wr.api.owner.entity.home.location.ApiComplaint;
import lombok.Data;

import java.io.Serializable;

/**
 * 投诉表扬表接收类
 * @Author: lvzy
 * @Date: 2022-12-02 10:56:52
 * @Desc:投诉表扬表接收类
 */
@Data
public class ApiComplaintDTO extends ApiComplaint implements Serializable {

    private static final long serialVersionUID = -8129431076521369958L;

    /**
     * 居家维修工单ID
     */
    private Long orderId;

    /**
     * 评价星级：0~5
     */
    private Integer appraiseStar;

    /**
     * 工单名称
     */
    private String orderName;

    private String updateBy;

}
