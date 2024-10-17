package com.wr.common.customize.model;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2021-07-22 15:52:42
 * @Desc: 区域
 */
public class RegionInfo implements Serializable {
    private static final long serialVersionUID = -1189036547368112231L;
    /**
     * egionIndexCode为空时，regionIndexCode会有平台生成，三方用户需要通过clientid和regionIndexCode进行关联。
     * 所以建议每次接口调用，clientid保持唯一。1~10个字符，只支持数字。
     */
    private Long clientId;
    /**
     * clientId
     * 区域唯一标志，不允许与其它区域唯一标志重复，包括已删除的区域，值为空或者不传此字段时系统自动生成唯一标志
     */
    private String regionIndexCode;
    /**
     *父区域唯一标识码
     */
    private String parentIndexCode;
    /**
     * 区域名称，同一级不可重复
     */
    private String regionName;

    /**
     * 区域类型
     * 10: 普通区域
     * 12: 楼栋单元
     */
    private Integer regionType;
    /**
     * 外码编码，regionType为12时有效，regionType为10时忽略该值
     */
    private String regionCode;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getRegionIndexCode() {
        return regionIndexCode;
    }

    public void setRegionIndexCode(String regionIndexCode) {
        this.regionIndexCode = regionIndexCode;
    }

    public String getParentIndexCode() {
        return parentIndexCode;
    }

    public void setParentIndexCode(String parentIndexCode) {
        this.parentIndexCode = parentIndexCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getRegionType() {
        return regionType;
    }

    public void setRegionType(Integer regionType) {
        this.regionType = regionType;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    @Override
    public String toString() {
        return "RegionInfo{" +
                "clientId=" + clientId +
                ", regionIndexCode='" + regionIndexCode + '\'' +
                ", parentIndexCode='" + parentIndexCode + '\'' +
                ", regionName='" + regionName + '\'' +
                ", regionType='" + regionType + '\'' +
                ", regionCode='" + regionCode + '\'' +
                '}';
    }
}
