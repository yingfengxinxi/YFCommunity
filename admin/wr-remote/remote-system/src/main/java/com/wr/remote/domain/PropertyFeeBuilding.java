package com.wr.remote.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: bajie
 * @create: 2022/11/3
 * @Description:
 * @FileName: PropertyFeeEntity
 * @History:
 */
@Data
public class PropertyFeeBuilding extends BaseEntity {
    private static final long serialVersionUID = 1L;

    //主键
    private String propertyFeeBuildingId;
    //物业缴费设置id主键
    private String propertyFeeId;
    //楼栋id
    private Long buildingId;

}
