package com.wr.estate.entity;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 医疗检测通告对象 bus_medical
 *
 * @author bajie
 * @date 2022-10-13
 */
public class MedicalEntity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long medicalId;

    /** 发布范围 小区id，表bus_community */
    @Excel(name = "发布范围 小区id，表bus_community")
    private Long communityId;

    /** 年龄限制 */
    private Long ageLimit;

    /** 活动名称 */
    @Excel(name = "活动名称")
    private String activityName;

    /** 活动类型：0=公益活动，1=其他 */
    @Excel(name = "活动类型：0=公益活动，1=其他",readConverterExp ="0=公益活动,1=其他")
    private String medicalType;

    /** 检测地点 */
    @Excel(name = "检测地点")
    private String address;

    /** 检测机构 */
    @Excel(name = "检测机构")
    private String medicalOrg;

    /** 负责人姓名 */
    @Excel(name = "负责人姓名")
    private String headName;

    /** 负责人手机号 */
    @Excel(name = "负责人手机号")
    private String headPhone;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 总人数 */
    private Long totalNum;

    /** 参与人数 */
    private Long joinNum;

    /** 删除标记：0=未删除，1=已删除 */
    private String delFlag;

    /**
     * 档案完成度
     */
    private float fileCompletion;

    private Map<String,Object> params;

    private Long examinerMedical;

    public Long getExaminerMedical() {
        return examinerMedical;
    }

    public void setExaminerMedical(Long examinerMedical) {
        this.examinerMedical = examinerMedical;
    }

    @Override
    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public float getFileCompletion() {
        return fileCompletion;
    }

    public void setFileCompletion(float fileCompletion) {
        this.fileCompletion = fileCompletion;
    }

    public void setMedicalId(Long medicalId)
    {
        this.medicalId = medicalId;
    }

    public Long getMedicalId()
    {
        return medicalId;
    }
    public void setCommunityId(Long communityId)
    {
        this.communityId = communityId;
    }

    public Long getCommunityId()
    {
        return communityId;
    }
    public void setAgeLimit(Long ageLimit)
    {
        this.ageLimit = ageLimit;
    }

    public Long getAgeLimit()
    {
        return ageLimit;
    }
    public void setActivityName(String activityName)
    {
        this.activityName = activityName;
    }

    public String getActivityName()
    {
        return activityName;
    }
    public void setMedicalType(String medicalType)
    {
        this.medicalType = medicalType;
    }

    public String getMedicalType()
    {
        return medicalType;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setMedicalOrg(String medicalOrg)
    {
        this.medicalOrg = medicalOrg;
    }

    public String getMedicalOrg()
    {
        return medicalOrg;
    }
    public void setHeadName(String headName)
    {
        this.headName = headName;
    }

    public String getHeadName()
    {
        return headName;
    }
    public void setHeadPhone(String headPhone)
    {
        this.headPhone = headPhone;
    }

    public String getHeadPhone()
    {
        return headPhone;
    }
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getStartTime()
    {
        return startTime;
    }
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }
    public void setTotalNum(Long totalNum)
    {
        this.totalNum = totalNum;
    }

    public Long getTotalNum()
    {
        return totalNum;
    }
    public void setJoinNum(Long joinNum)
    {
        this.joinNum = joinNum;
    }

    public Long getJoinNum()
    {
        return joinNum;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("medicalId", getMedicalId())
            .append("communityId", getCommunityId())
            .append("ageLimit", getAgeLimit())
            .append("activityName", getActivityName())
            .append("medicalType", getMedicalType())
            .append("address", getAddress())
            .append("medicalOrg", getMedicalOrg())
            .append("headName", getHeadName())
            .append("headPhone", getHeadPhone())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("remark", getRemark())
            .append("totalNum", getTotalNum())
            .append("joinNum", getJoinNum())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .append("fileCompletion",getFileCompletion())
            .toString();
    }
}
