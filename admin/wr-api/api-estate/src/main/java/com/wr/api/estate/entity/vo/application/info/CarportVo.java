package com.wr.api.estate.entity.vo.application.info;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/23 17:21
 */
@Data
public class CarportVo {

    //车位编号
    private String carportNo;
    private String carportName;

    //车位属性：0=人防；1=非人防
    private String carportNature;

    //租售类型：0=售；1=年租；2=月租
    private String rentalType;

    //车位出租金额
    private BigDecimal carportFee;

    //出租开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String rentalStart;

    //出租结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String rentalEnd;
}
