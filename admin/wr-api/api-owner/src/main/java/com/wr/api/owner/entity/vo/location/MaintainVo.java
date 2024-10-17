package com.wr.api.owner.entity.vo.location;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-16 20:07:10
 * @Desc:   报事报修
 */
@Data
public class MaintainVo implements Serializable {

    private static final long serialVersionUID = 1L;

    //工单id
    private Long workId;
    private Long orderId;
    //执行状态
    private Long status;
    //报事名称
    private String auditName;
    //报事类型名称
    private String repairName;
    //报事类型   3中类型
    private String workType;
    //发布人
    private String ownerName;
    //电话号码
    private String ownerIphone;
    //地址
    private String address;
    //计划开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planStart;
    //计划结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planEnd;
    //备注
    private String remark;
    //星级评价
    private Integer appraiseStar;
    //图片路径
    private List<String> images;
    //工单编号
    private String orderNo;
    //负责人
    private String staffName;
    //单个图片对象
    private String imageUrlOne;
    //负责人电话
    private String staffIphone;
    private String complaintType;
    //协助人
    private List<OrderAssistVo> orderAssistList;
    //评价类型
    private String evaluationType;
    //评价备注
    private String evaluationremark;
    //评价图片
    private String evaluationimage;

    /**
     * 驳回理由
     */
    private String rejectReason;
}
