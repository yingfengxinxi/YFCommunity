package com.wr.api.owner.entity.vo.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.remote.govern.party.Member;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 党员管理扩展
 * @Author: lvzy
 * @Date: 2022-11-17 13:43:31
 * @Desc:党员管理扩展
 */
@Data
public class ApiMemberVo extends Member implements Serializable {

    private static final long serialVersionUID = 9143291810465018739L;

    /**
     * 页数
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 个人党费
     */
    private BigDecimal partyFee;

    /**
     * 姓名
     */
    private String accountName;

    /**
     * 手机号
     */
    private String loginName;

    /**
     * 党组织姓名
     */
    private String partyName;

    /**
     * 缴费时间
     */
    @JsonFormat(pattern = "yyyy-MM")
    @JSONField(format = "yyyy-MM")
    private Date payTime;
}
