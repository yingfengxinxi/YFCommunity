package com.wr.api.owner.entity.myinfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Wen Chou
 * @Date: 2022-11-11 09:49:11
 * @Desc:
 */
@Data
public class Myinfo {
    private static final long serialVersionUID = 521293669298749970L;

    /** 主键*/
    private Long ownerId;

    /** 小区id，表bus_community*/
    private Long communityId;
    /** 小区名称*/
    private String communityName;

    /** 楼栋id，表bus_building*/
    private Long buildingId;
    /** 楼栋名称*/
    private String buildingName;

    /** 单元id，表bus_unit*/
    private Long unitId;
    /** 单元名称*/
    private String unitName;

    /** 当前所住房间id，表bus_room*/
    private Long roomId;

    /** 房产id，表bus_house*/
    private Long houseId;

    /** 房间名称*/
    private String roomName;
    /** 业主默认房产 */
    private Long defaultRoom;

    /** 房间名称*/
    private String roomStatus;

    /** 户主id*/
    private Long parentId;

    /** 账号id，表bus_account*/
    private Long accountId;

    /** 产权证明书*/
    private String licenseUrl;

    /** 与户主关系：0=本人，1=配偶，2=父母，3=子女，4=其他,5=租客*/
    private String ownerRelation;

    /** 业主姓名*/
    private String ownerName;

    /** 手机号*/
    private String ownerPhone;

    /** 性别：0=男，1=女，2=未知*/
    private String gender;

    /** 证件类型：见数据字典 sys_certificate*/
    private String cardType;

    /** 证件号码*/
    private String cardNo;

    /** 证件照（正面）*/
    private String cardFont;

    /** 证件照（反面）*/
    private String cardBack;

    /** 人脸URL*/
    private String faceUrl;

    /** 户籍地址*/
    private String domicileAddress;

    /** 政治面貌：0=党员，1=团员，2=其他*/
    private String politicsStatus;

    /** 民族*/
    private String nation;

    /** 籍贯*/
    private String nativePlace;

    /** 宗教信仰*/
    private String religion;

    /** 文化程度*/
    private String eduLevel;

    /** 兵役状况*/
    private String military;

    /** 婚姻状况：0=未婚，1=已婚*/
    private String marriageStatus;

    /** 国籍*/
    private String nationality;

    /** 人口类型：0=户籍人口，1=常驻人口，2=暂住人口*/
    private String population;

    /** 职业*/
    private String career;

    /** QQ号*/
    private String qqNumber;

    /** 邮箱*/
    private String email;

    /** 数据来源：0=物业注册，1=手机注册*/
    private String dataSource;

    /** 个人介绍*/
    private String selfIntro;

    /** 审核状态：0=审核中，1=通过，2=驳回*/
    private String auditStatus;

    /** 审核时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 驳回理由*/
    private String rejectReason;

    /** 创建人*/
    private String createBy;

    /** 创建时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 修改人*/
    private String updateBy;

    /** 修改人*/
    private String volunteer;
    private String dictLabel;
    private String dictValue;
    /** 个人备注*/
    private String ownRemarks;
    /** 房屋坐落*/
    private String houseAddress;
    /** 房屋属性*/
    @Excel(name = "房屋属性", readConverterExp = "0=商品房,1=商铺,2=成套住宅,3=别墅", combo = "商品房,商铺,成套住宅,别墅", prompt = "必填")
    private String roomType;
    /**
     * 房屋属性：1=商品房，2=商铺，3=成套住宅，4=别墅
     */
    private String roomAttribute;
    /** 房屋面积*/
    private String totalArea;
    /** 交房时间*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliveryTime;

    private String estateId;
    private String floor;
    /** 房屋审核状态：0=审核中，1=通过，2=驳回*/
    private String houseAuditStatus;

    public String getDictLabel() {
        return dictLabel;
    }

    public void setDictLabel(String dictLabel) {
        this.dictLabel = dictLabel;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(String volunteer) {
        this.volunteer = volunteer;
    }

    /** 修改时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 删除标记：0=未删除，1=已删除*/
    private String delFlag;

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getOwnerRelation() {
        return ownerRelation;
    }

    public void setOwnerRelation(String ownerRelation) {
        this.ownerRelation = ownerRelation;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardFont() {
        return cardFont;
    }

    public void setCardFont(String cardFont) {
        this.cardFont = cardFont;
    }

    public String getCardBack() {
        return cardBack;
    }

    public void setCardBack(String cardBack) {
        this.cardBack = cardBack;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getDomicileAddress() {
        return domicileAddress;
    }

    public void setDomicileAddress(String domicileAddress) {
        this.domicileAddress = domicileAddress;
    }

    public String getPoliticsStatus() {
        return politicsStatus;
    }

    public void setPoliticsStatus(String politicsStatus) {
        this.politicsStatus = politicsStatus;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getMilitary() {
        return military;
    }

    public void setMilitary(String military) {
        this.military = military;
    }

    public String getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(String marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getSelfIntro() {
        return selfIntro;
    }

    public void setSelfIntro(String selfIntro) {
        this.selfIntro = selfIntro;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Long getDefaultRoom() {
        return defaultRoom;
    }

    public void setDefaultRoom(Long defaultRoom) {
        this.defaultRoom = defaultRoom;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }
}
