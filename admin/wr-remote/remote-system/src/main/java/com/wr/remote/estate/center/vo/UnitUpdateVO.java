package com.wr.remote.estate.center.vo;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-12 11:21:50
 * @Desc:
 */
public class UnitUpdateVO {

    /**
     * 楼宇id
     */
    private Long unitId;

    /**
     * 楼宇名
     */
    private String unitName;

    /**
     * 修改人
     */
    private String updateBy;

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String toString() {
        return "UnitUpdateVO{" +
                "unitId=" + unitId +
                ", unitName='" + unitName + '\'' +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }
}
