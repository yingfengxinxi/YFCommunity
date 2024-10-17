package com.wr.estate.entity.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-11-01 11:42:04
 * @Desc:
 */
@Data
@ToString
public class CleanExecuteNameVO {
    private Long cleanId;

    private Long communityId;

    private String cleanNo;

    private String cleanName;

    private String dataSource;

    private Long planId;

    private String orderNo;

    private Long venueId;

    private String address;

    private String allotWay;

    private Integer patrolNum;

    private BigDecimal gapTime;

    private Date startTime;

    private Date endTime;

    private String staffName;

    private Integer assistNum;

    private String transferStatus;

    private String allotStatus;

    private String acceptStatus;

    private String executeStatus;

    private Long executeName;

    private Date executeTime;

    private Date finishTime;

    private String imageUrl;

    private String remark;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String delFlag;

    public CleanExecuteNameVO(CleanVo cleanVo,String staffName) {
        this.cleanId = cleanVo.getCleanId();
        this.communityId =  cleanVo.getCommunityId();
        this.cleanNo = cleanVo.getCleanNo();
        this.cleanName =  cleanVo.getCleanName();
        this.dataSource =  cleanVo.getDataSource();
        this.planId =  cleanVo.getPlanId();
        this.orderNo = cleanVo.getOrderNo();
        this.venueId =  cleanVo.getVenueId();
        this.address = cleanVo.getAddress();
        this.allotWay = cleanVo.getAllotWay() ;
        this.patrolNum = cleanVo.getPatrolNum();
        this.gapTime = cleanVo.getGapTime();
        this.startTime = cleanVo.getStartTime();
        this.endTime = cleanVo.getEndTime();
        this.staffName =  staffName;
        this.assistNum = cleanVo.getAssistNum();
        this.transferStatus = cleanVo.getTransferStatus() ;
        this.allotStatus = cleanVo.getAllotStatus() ;
        this.acceptStatus = cleanVo.getAcceptStatus();
        this.executeStatus = cleanVo.getExecuteStatus();
        this.executeName = cleanVo.getExecuteId();
        this.executeTime = cleanVo.getExecuteTime() ;
        this.finishTime = cleanVo.getFinishTime();
        this.imageUrl = cleanVo.getImageUrl();
        this.remark = cleanVo.getRemark() ;
        this.createBy = cleanVo.getCreateBy() ;
        this.createTime = cleanVo.getCreateTime();
        this.updateBy =  cleanVo.getUpdateBy() ;
        this.updateTime = cleanVo.getUpdateTime() ;
        this.delFlag = cleanVo.getDelFlag() ;
    }
}
