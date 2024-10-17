package com.wr.api.owner.entity.vo.location;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.api.owner.entity.home.location.ApiHomeOrder;
import com.wr.api.owner.entity.home.location.ApiOrderImage;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 园区报事工单表扩展
 * @Author: lvzy
 * @Date: 2022-12-01 14:27:57
 * @Desc:园区报事工单表扩展
 */
@Data
public class ApiHomeOrderVo extends ApiHomeOrder implements Serializable {

    private static final long serialVersionUID = -1549959136627340463L;

    /**
     * 列表也页展示图片
     */
    private String imageUrlPage;

    /**
     * 居家状态: 1.待分配 2.转交中 3.待执行 4.执行中 5.待支付 6.待评价 7.已评价
     */
    private String homeStatus;

    /**
     * 业主姓名
     */
    private String ownerName;

    /**
     * 业主手机号
     */
    private String ownerPhone;

    /**
     * 居家维修工单名称
     */
    private String orderName;

    /**
     * 居家维修名称
     */
    private String repairName;

    /**
     * 负责人名称
     */
    private String staffNameOne;

    /**
     * 负责人手机号
     */
    private String staffPhoneOne;

    /**
     * 协助人名称
     */
    private String staffNameTwo;

    /**
     * 协助人手机号
     */
    private String staffPhoneTwo;

    /**
     * 类型：0=业主表扬，1=业主投诉
     */
    private String complaintType;

    /**
     * 评论内容
     */
    private String complaintRemark;

    /**
     * 评价图片
     */
    private String complaintUrl;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date createTimes;

    /**
     * 详情图片组
     */
    private List<ApiOrderImage> imageUrlList;

    /**
     * 协助人
     */
    private List<ApiOrderAssistVo> assistList;
}
