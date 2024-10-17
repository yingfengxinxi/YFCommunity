package com.wr.remote.govern.home;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * @Author: lusu
 * @Date: 2022-12-01 10:44:01
 * @Desc: 首页统计： 入党审核  社区救助  警民互动
 */
@Data
public class Census implements Serializable {

    private static final long serialVersionUID = 7218108548752300526L;

    /**
     * 物业id
     */
    private Long estateId;


    /**
     * 入党审核：待审核
     */
    private BigDecimal memberAudit;
    /**
     * 入党审核：已驳回
     */
    private BigDecimal memberRebut;
    /**
     * 社区救助：未开始
     */
    private BigDecimal rescueNotStart;
    /**
     * 社区救助：进行中
     */
    private BigDecimal rescueStart;
    /**
     * 警民互动：通报人数
     */
    private BigDecimal bulletinNum;
    /**
     * 警民互动：归档人数
     */
    private BigDecimal fileNum;


    /**
     * 党费统计一对多， 需要集合接收
     */
    private ArrayList<MemberCensus> memberList;

    /**
     * 线索反馈次数
     */
    private ArrayList<Clue> clueList;

    /**
     * 业主委员会
     */
    private ArrayList<Committee> committeeList;


    /**
     * 党费缴费记录
     */
    private ArrayList<HomePartyPay> partyFeeList;

    /**
     * 党费缴费日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date payMonth;


}
