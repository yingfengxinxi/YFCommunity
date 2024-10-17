//package com.wr.remote.work.patrol;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.wr.common.core.web.domain.BaseEntity;
//import org.apache.commons.lang3.builder.ToStringBuilder;
//import org.apache.commons.lang3.builder.ToStringStyle;
//
///**
// * 巡查工单对象 bus_patrol
// *
// * @author 奔波儿灞
// * @date 2022-10-27
// */
//public class Patrol extends BaseEntity {
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 主键
//     */
//    private Long patrolId;
//
//    /**
//     * 小区id，表xccj_community.bus_community
//     */
//    private Long communityId;
//
//    /**
//     * 工单编号
//     */
//    private String patrolNo;
//
//    /**
//     * 工单名称
//     */
//    private String patrolName;
//
//    /**
//     * 巡查类型id，表xccj_work.bus_tour（tour_type=0）
//     */
//    private Long tourId;
//
//    /**
//     * 巡查点id，表xccj_work.bus_point（point_type!=2）
//     */
//    private Long pointId;
//
//    /**
//     * 工单来源：0=计划生成，1=后台添加，2=其他工单
//     */
//    private String dataSource;
//
//    /**
//     * 计划id，表xccj_work.bus_patrol_plan（data_source=0有值）
//     */
//    private Long planId;
//
//    /**
//     * 其他工单编号（data_source=2有值）
//     */
//    private String orderNo;
//
//    /**
//     * 工单分配方式：0=自动分配，1=管理员分配，2=抢单
//     */
//    private String allotWay;
//
//    /**
//     * 巡查次数/天次
//     */
//    private Long patrolNum;
//
//    /**
//     * 每次巡查间隔时间（一日多查时有值）
//     */
//    private BigDecimal gapTime;
//
//    /**
//     * 开始时间
//     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
//    private Date startTime;
//
//    /**
//     * 结束时间
//     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
//    private Date endTime;
//
//    /**
//     * 负责人id，表xccj_community.bus_staff
//     */
//    private Long staffId;
//
//    /**
//     * 协助人数
//     */
//    private Long assistNum;
//
//    /**
//     * 转交状态：0=无需转交，1=转交待确认
//     */
//    private String transferStatus;
//
//    /**
//     * 分配状态：0=待分配，1=已分配
//     */
//    private String allotStatus;
//
//    /**
//     * 接单状态：0=待接单，1=已接单
//     */
//    private String acceptStatus;
//
//    /**
//     * 执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成
//     */
//    private String executeStatus;
//
//    /**
//     * 执行人id，表xccj_community.bus_staff
//     */
//    private Long executeId;
//
//    /**
//     * 执行时间
//     */
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private Date executeTime;
//
//    /**
//     * 完成时间
//     */
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private Date finishTime;
//
//    /**
//     * 完成图片
//     */
//    private String imageUrl;
//
//    /**
//     * 删除标记：0=未删除，1=已删除
//     */
//    private String delFlag;
//
//    public void setPatrolId(Long patrolId) {
//        this.patrolId = patrolId;
//    }
//
//    public Long getPatrolId() {
//        return patrolId;
//    }
//
//    public void setCommunityId(Long communityId) {
//        this.communityId = communityId;
//    }
//
//    public Long getCommunityId() {
//        return communityId;
//    }
//
//    public void setPatrolNo(String patrolNo) {
//        this.patrolNo = patrolNo;
//    }
//
//    public String getPatrolNo() {
//        return patrolNo;
//    }
//
//    public void setPatrolName(String patrolName) {
//        this.patrolName = patrolName;
//    }
//
//    public String getPatrolName() {
//        return patrolName;
//    }
//
//    public void setTourId(Long tourId) {
//        this.tourId = tourId;
//    }
//
//    public Long getTourId() {
//        return tourId;
//    }
//
//    public void setPointId(Long pointId) {
//        this.pointId = pointId;
//    }
//
//    public Long getPointId() {
//        return pointId;
//    }
//
//    public void setDataSource(String dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    public String getDataSource() {
//        return dataSource;
//    }
//
//    public void setPlanId(Long planId) {
//        this.planId = planId;
//    }
//
//    public Long getPlanId() {
//        return planId;
//    }
//
//    public void setOrderNo(String orderNo) {
//        this.orderNo = orderNo;
//    }
//
//    public String getOrderNo() {
//        return orderNo;
//    }
//
//    public void setAllotWay(String allotWay) {
//        this.allotWay = allotWay;
//    }
//
//    public String getAllotWay() {
//        return allotWay;
//    }
//
//    public void setPatrolNum(Long patrolNum) {
//        this.patrolNum = patrolNum;
//    }
//
//    public Long getPatrolNum() {
//        return patrolNum;
//    }
//
//    public void setGapTime(BigDecimal gapTime) {
//        this.gapTime = gapTime;
//    }
//
//    public BigDecimal getGapTime() {
//        return gapTime;
//    }
//
//    public void setStartTime(Date startTime) {
//        this.startTime = startTime;
//    }
//
//    public Date getStartTime() {
//        return startTime;
//    }
//
//    public void setEndTime(Date endTime) {
//        this.endTime = endTime;
//    }
//
//    public Date getEndTime() {
//        return endTime;
//    }
//
//    public void setStaffId(Long staffId) {
//        this.staffId = staffId;
//    }
//
//    public Long getStaffId() {
//        return staffId;
//    }
//
//    public void setAssistNum(Long assistNum) {
//        this.assistNum = assistNum;
//    }
//
//    public Long getAssistNum() {
//        return assistNum;
//    }
//
//    public void setTransferStatus(String transferStatus) {
//        this.transferStatus = transferStatus;
//    }
//
//    public String getTransferStatus() {
//        return transferStatus;
//    }
//
//    public void setAllotStatus(String allotStatus) {
//        this.allotStatus = allotStatus;
//    }
//
//    public String getAllotStatus() {
//        return allotStatus;
//    }
//
//    public void setAcceptStatus(String acceptStatus) {
//        this.acceptStatus = acceptStatus;
//    }
//
//    public String getAcceptStatus() {
//        return acceptStatus;
//    }
//
//    public void setExecuteStatus(String executeStatus) {
//        this.executeStatus = executeStatus;
//    }
//
//    public String getExecuteStatus() {
//        return executeStatus;
//    }
//
//    public void setExecuteId(Long executeId) {
//        this.executeId = executeId;
//    }
//
//    public Long getExecuteId() {
//        return executeId;
//    }
//
//    public void setExecuteTime(Date executeTime) {
//        this.executeTime = executeTime;
//    }
//
//    public Date getExecuteTime() {
//        return executeTime;
//    }
//
//    public void setFinishTime(Date finishTime) {
//        this.finishTime = finishTime;
//    }
//
//    public Date getFinishTime() {
//        return finishTime;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setDelFlag(String delFlag) {
//        this.delFlag = delFlag;
//    }
//
//    public String getDelFlag() {
//        return delFlag;
//    }
//
//    @Override
//    public String toString() {
//        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
//                .append("patrolId", getPatrolId())
//                .append("communityId", getCommunityId())
//                .append("patrolNo", getPatrolNo())
//                .append("patrolName", getPatrolName())
//                .append("tourId", getTourId())
//                .append("pointId", getPointId())
//                .append("dataSource", getDataSource())
//                .append("planId", getPlanId())
//                .append("orderNo", getOrderNo())
//                .append("allotWay", getAllotWay())
//                .append("patrolNum", getPatrolNum())
//                .append("gapTime", getGapTime())
//                .append("startTime", getStartTime())
//                .append("endTime", getEndTime())
//                .append("staffId", getStaffId())
//                .append("assistNum", getAssistNum())
//                .append("transferStatus", getTransferStatus())
//                .append("allotStatus", getAllotStatus())
//                .append("acceptStatus", getAcceptStatus())
//                .append("executeStatus", getExecuteStatus())
//                .append("executeId", getExecuteId())
//                .append("executeTime", getExecuteTime())
//                .append("finishTime", getFinishTime())
//                .append("imageUrl", getImageUrl())
//                .append("remark", getRemark())
//                .append("createBy", getCreateBy())
//                .append("createTime", getCreateTime())
//                .append("updateBy", getUpdateBy())
//                .append("updateTime", getUpdateTime())
//                .append("delFlag", getDelFlag())
//                .toString();
//    }
//}
