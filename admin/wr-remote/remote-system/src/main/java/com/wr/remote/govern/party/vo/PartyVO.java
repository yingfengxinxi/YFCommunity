package com.wr.remote.govern.party.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: SJiang
 * @Date: 2022-10-25 10:36:24
 * @Desc: 党组织管理VO
 */
@Data
public class PartyVO implements Serializable {

    private static final long serialVersionUID = -904341378298424875L;

    /**
     * 主键id
     */
    private Long partyId;
    /**
     * 党组织名称
     */
    private String partyName;
    /**
     * 小区id,表xccj_community.bus_community
     */
    private Long communityId;
    /**
     * 小区,表xccj_community.bus_community
     */
    private String communityName;
    /**
     * 顶层党组织：0=是,1=不是
     */
    private String topParty;
    /**
     * 顶层党组织id
     */
    private Long topId;
    /**
     * 上级党组织id
     */
    private Long parentId;
    /**
     * 党组织logo路径
     */
    private String logoUrl;
    /**
     * 组织类型：1=行政村党组织,2=社区党组织,3=机关事业党组织,4=国有企业党组织,5=非公企业党组织,6=社会组织党组织,7=其他
     */
    private String partyType;
    /**
     * 单位名称
     */
    private String unitName;
    /**
     * 单位类型：1=城市街道,2=城市社区（居委会）,3=乡镇,4=农村社区（居委会）,5=建制村,6=国有经济控制,7=集团经济控制,8=非公有制经济控制,9=事业单位,10=机关,11=社会组织,12=其他
     */
    private String unitType;
    /**
     * 单位建党组织情况:1=建立党委,2=建立党总支,3=建立党支部,4=建立联合党支部
     */
    private String unitCase;
    /**
     * 党组织介绍
     */
    private String content;
    /**
     * 党组织状态：0=正常,1=关闭
     */
    private String partyStatus;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 党组织负责人id
     */
    private String adminId;
    /**
     * 党组织负责人
     */
    private String adminName;

    @Override
    public String toString() {
        return "PartyVO{" +
                "partyId=" + partyId +
                ", partyName='" + partyName + '\'' +
                ", communityId=" + communityId +
                ", communityName='" + communityName + '\'' +
                ", topParty='" + topParty + '\'' +
                ", topId=" + topId +
                ", parentId=" + parentId +
                ", logoUrl='" + logoUrl + '\'' +
                ", partyType='" + partyType + '\'' +
                ", unitName='" + unitName + '\'' +
                ", unitType='" + unitType + '\'' +
                ", unitCase='" + unitCase + '\'' +
                ", content='" + content + '\'' +
                ", partyStatus='" + partyStatus + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }
}
