package com.wr.remote.govern.security;

import java.io.Serializable;
import java.util.Date;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-24 17:15:39
 * @Desc: 监控视频对象
 */
public class Camera extends BaseEntity {
    /**
     * 主键
     */
    private Long cameraId;

    /**
     * 唯一编码
     */
    private String indexCode;

    /**
     * 资源名称
     */
    private String cameraName;

    /**
     * 监控点类型 枪机0 半球1 快球2 带云台枪机3
     */
    private Integer cameraType;

    /**
     * 通道号
     */
    private Integer chanNum;

    /**
     * 设备能力集
     */
    private String capability;

    /**
     * 通道子类型 模拟通道analog 数字通道digital 镜像通道mirror 录播通道record 零通道zero
     */
    private String channelType;

    /**
     * 解码模式
     */
    private String decodeTag;

    /**
     * 监控点关联对讲唯一标志
     */
    private String cameraRelateTalk;

    /**
     * 传输协议
     */
    private Integer transType;

    /**
     * 接入协议
     */
    private String treatyType;

    /**
     * 录像存储位置
     */
    private String recordLocation;

    /**
     * 所属DAC编号
     */
    private String dacIndexCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 在线状态：-1=未检测，0=离线，1=在线
     */
    private Integer online;

    /**
     * 厂商
     */
    private String manufacturer;

    /**
     * 数据库同步人
     */
    private String renewalBy;

    /**
     * 同步时间
     */
    private Date renewalTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private Date operateTime;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    /**
     * 楼栋id，表xccj_community.bus_building
     */
    private Long buildingId;

    /**
     * 单元id，表xccj_community.bus_unit
     */
    private Long unitId;

    /**
     * 设备所在地址
     */
    private String address;

    /**
     * 设备二维码
     */
    private String deviceQr;

    /**
     * 设备类型：见字典 sys_camera_type
     */
    private String deviceType;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;


    public Long getCameraId() {
        return cameraId;
    }

    public void setCameraId(Long cameraId) {
        this.cameraId = cameraId;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public Integer getCameraType() {
        return cameraType;
    }

    public void setCameraType(Integer cameraType) {
        this.cameraType = cameraType;
    }

    public Integer getChanNum() {
        return chanNum;
    }

    public void setChanNum(Integer chanNum) {
        this.chanNum = chanNum;
    }

    public String getCapability() {
        return capability;
    }

    public void setCapability(String capability) {
        this.capability = capability;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getDecodeTag() {
        return decodeTag;
    }

    public void setDecodeTag(String decodeTag) {
        this.decodeTag = decodeTag;
    }

    public String getCameraRelateTalk() {
        return cameraRelateTalk;
    }

    public void setCameraRelateTalk(String cameraRelateTalk) {
        this.cameraRelateTalk = cameraRelateTalk;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public String getTreatyType() {
        return treatyType;
    }

    public void setTreatyType(String treatyType) {
        this.treatyType = treatyType;
    }

    public String getRecordLocation() {
        return recordLocation;
    }

    public void setRecordLocation(String recordLocation) {
        this.recordLocation = recordLocation;
    }

    public String getDacIndexCode() {
        return dacIndexCode;
    }

    public void setDacIndexCode(String dacIndexCode) {
        this.dacIndexCode = dacIndexCode;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getRenewalBy() {
        return renewalBy;
    }

    public void setRenewalBy(String renewalBy) {
        this.renewalBy = renewalBy;
    }

    public Date getRenewalTime() {
        return renewalTime;
    }

    public void setRenewalTime(Date renewalTime) {
        this.renewalTime = renewalTime;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeviceQr() {
        return deviceQr;
    }

    public void setDeviceQr(String deviceQr) {
        this.deviceQr = deviceQr;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}