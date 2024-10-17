package com.wr.remote.estate.center;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @Author: lvzy
 * @Date: 2022-10-12 09:13:59
 * @Desc:
 */
public class PersonLabel extends BaseEntity {

    private static final long serialVersionUID = -7018129787103770705L;

    /** 主键 */
    private Long labelId;

    /** 小区id */
    private Long communityId;

    /** 人员类型：0=业主，1=租客 */
    private String personType;

    /** 人员id，表bus_owner/bus_tenant */
    private String  personId;

    /** 年龄标签: 1=婴幼儿、2=青少年、3=青年、4=中年、5=中老年、6=老年 */
    private String ageLabel;

    /** 身份标签∶ 1=群众、2=团员、3=党员、4=犯青少年、5=刑教释满人、6=社区服刑人、7=吸毒重点人、8=精神病人、9=犯青少年、10=重点人员 */
    private String identityLabel;

    /** 身体健康标签︰1=健全、2=轻度残疾、3=重度残疾 */
    private String healthyLabel;

    /** 新冠健康状态标签:1=健康、2=密接者、3=次密接、4=确诊、5=治愈、6=复发 */
    private String coronavirus;

    /** 安康码:1=绿码、2=黄码、3=红码、4灰码 */
    private String healthyColor;

    /** 行动标签:1=正常、2=隔离 */
    private String behaviorLabel;

    /** 删除标记0：未删除1已删除 */
    private String delFlag;

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getAgeLabel() {
        return ageLabel;
    }

    public void setAgeLabel(String ageLabel) {
        this.ageLabel = ageLabel;
    }

    public String getIdentityLabel() {
        return identityLabel;
    }

    public void setIdentityLabel(String identityLabel) {
        this.identityLabel = identityLabel;
    }

    public String getHealthyLabel() {
        return healthyLabel;
    }

    public void setHealthyLabel(String healthyLabel) {
        this.healthyLabel = healthyLabel;
    }

    public String getCoronavirus() {
        return coronavirus;
    }

    public void setCoronavirus(String coronavirus) {
        this.coronavirus = coronavirus;
    }

    public String getHealthyColor() {
        return healthyColor;
    }

    public void setHealthyColor(String healthyColor) {
        this.healthyColor = healthyColor;
    }

    public String getBehaviorLabel() {
        return behaviorLabel;
    }

    public void setBehaviorLabel(String behaviorLabel) {
        this.behaviorLabel = behaviorLabel;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "PersonLabel{" +
                "labelId=" + labelId +
                ", communityId=" + communityId +
                ", personType='" + personType + '\'' +
                ", personId=" + personId +
                ", ageLabel='" + ageLabel + '\'' +
                ", identityLabel='" + identityLabel + '\'' +
                ", healthyLabel='" + healthyLabel + '\'' +
                ", coronavirus='" + coronavirus + '\'' +
                ", healthyColor='" + healthyColor + '\'' +
                ", behaviorLabel='" + behaviorLabel + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
