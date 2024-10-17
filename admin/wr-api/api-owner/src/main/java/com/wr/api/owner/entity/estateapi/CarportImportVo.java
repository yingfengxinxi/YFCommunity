package com.wr.api.owner.entity.estateapi;

import com.wr.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author lvzy
 * @Date 2024/2/7 11:19
 */
@Data
public class CarportImportVo {

    @Excel(name = "车位编号", prompt = "必填")
    private String carportNo;

    @Excel(name = "车位属性", prompt = "必填",readConverterExp = "0=人防,1=非人防", combo = "人防,非人防")
    private String carportNature;


    @Excel(name = "租售类型", prompt = "必填",readConverterExp="0=售,1=年租,2=月租",combo = "售,年租,月租")
    private String rentalType;

    @Excel(name = "金额", prompt = "必填")
    private BigDecimal carportFee;

}
