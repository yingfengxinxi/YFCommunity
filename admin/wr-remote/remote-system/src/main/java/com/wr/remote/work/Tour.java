package com.wr.remote.work;

import com.wr.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 巡X类型对象 bus_tour
 *
 * @author 奔波儿灞
 * @date 2022-10-28
 */
public class Tour extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long tourId;

    /**
     * 物业id，表xccj_estate_sys.bus_estate
     */
    private Long estateId;

    /**
     * 类型：0=巡查类型，1=巡更类型，2=巡检类型
     */
    private String tourType;

    /**
     * 类型名称
     */
    private String tourName;

    /**
     * 状态：0=启用，1=停用
     */
    private String tourStatus;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setTourType(String tourType) {
        this.tourType = tourType;
    }

    public String getTourType() {
        return tourType;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourStatus(String tourStatus) {
        this.tourStatus = tourStatus;
    }

    public String getTourStatus() {
        return tourStatus;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("tourId", getTourId())
                .append("estateId", getEstateId())
                .append("tourType", getTourType())
                .append("tourName", getTourName())
                .append("tourStatus", getTourStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
