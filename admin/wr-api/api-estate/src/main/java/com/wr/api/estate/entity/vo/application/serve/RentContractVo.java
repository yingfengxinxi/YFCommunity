package com.wr.api.estate.entity.vo.application.serve;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/23 15:58
 * 租房合同
 */
@Data
public class RentContractVo {

    //租房开始时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date contractStart;

    //租房结束时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date contractEnd;

    //押金付款时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date depositPayTime;

    //租赁总金额
    private BigDecimal totalMoney;
    //押金金额
    private BigDecimal deposit;
}
