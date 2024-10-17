package com.wr.api.owner.entity.home.visitor;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Author: lusu
 * @Date: 2022-11-16 20:33:15
 * @Desc: 通行证表
 */


public class ApiLiaison implements Serializable {

    private static final long serialVersionUID = 6625086362674014792L;
    /**
     * 主键id
     */
    private Long liaisonId;

    /**
     * 账号id，表bus_account(account_type=2,3)
     */
    private Long accountId;

    /**
     * 拜访房间id,表bus_room
     */
    private Long roomId;

    /**
     * 被访人id,表bus_owner
     */
    private Long ownerId;

    /**
     * 拜访人姓名
     */
    private String userName;

    /**
     * 拜访人联系方式
     */
    private String userPhone;

    /**
     * 拜访人身份证号
     */
    private String cardNo;

    /**
     * 二维码路径
     */
    private String codeUrl;

    /**
     * 拜访人车牌号
     */
    private String plantNo;

    /**
     * 同行人数
     */
    private Integer withNum;

    /**
     * 二维码有效期开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date beginTime;

    /**
     * 二维码有效期过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date endTime;

    /**
     * 数据类型:0=用户为自己创建,1=业主为别人创建,2=装修人员
     */
    private String liaisonType;

    /**
     * 当前装修id，表bus_decorate(liaison_type=2时有值)
     */
    private Long decorateId;

    /**
     * 二维码申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /**
     * 二维码业主审核通过时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date passTime;

    /**
     * 状态 0 待审核 1 审核通过 2 未通过审核
     */
    private String liaisonStatus;

    /**
     * 删除状态 0 未删除 1 已删除
     */
    private String delFlag;

    public Long getLiaisonId() {
        return liaisonId;
    }

    public void setLiaisonId(Long liaisonId) {
        this.liaisonId = liaisonId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getPlantNo() {
        return plantNo;
    }

    public void setPlantNo(String plantNo) {
        this.plantNo = plantNo;
    }

    public Integer getWithNum() {
        return withNum;
    }

    public void setWithNum(Integer withNum) {
        this.withNum = withNum;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getLiaisonType() {
        return liaisonType;
    }

    public void setLiaisonType(String liaisonType) {
        this.liaisonType = liaisonType;
    }

    public Long getDecorateId() {
        return decorateId;
    }

    public void setDecorateId(Long decorateId) {
        this.decorateId = decorateId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    public String getLiaisonStatus() {
        return liaisonStatus;
    }

    public void setLiaisonStatus(String liaisonStatus) {
        this.liaisonStatus = liaisonStatus;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}