package com.wr.remote.estate.manage.contract;

import com.wr.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * @Author: lusu
 * @Date: 2022-10-17 13:01:14
 * @Desc: 采购材料表
 */
public class Material extends BaseEntity {
    private static final long serialVersionUID = -3268973855593050599L;
    /**
     *主键
     */
    private Long materialId;
    /**
     * 采购合同id，表bus_procurement'
     */
    private Long procurementId;

    /**
     * 物品名称
     */
    private String materialName;
    /**
     * 物品规格
     */
    private String specification;
    /**
     * 数量
     */
    private Long materialNum;

    /**
     *单价
     */
    private BigDecimal unitPrice;

    /**
     * 总价
     */
    private BigDecimal totalAmount;

    /**
     * 实际金额
     */
    private BigDecimal actuallyAmount;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;




    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getProcurementId() {
        return procurementId;
    }

    public void setProcurementId(Long procurementId) {
        this.procurementId = procurementId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Long getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(Long materialNum) {
        this.materialNum = materialNum;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getActuallyAmount() {
        return actuallyAmount;
    }

    public void setActuallyAmount(BigDecimal actuallyAmount) {
        this.actuallyAmount = actuallyAmount;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Material{" +
                "materialId=" + materialId +
                ", procurementId=" + procurementId +
                ", materialName='" + materialName + '\'' +
                ", specification='" + specification + '\'' +
                ", materialNum=" + materialNum +
                ", unitPrice=" + unitPrice +
                ", totalAmount=" + totalAmount +
                ", actuallyAmount=" + actuallyAmount +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
