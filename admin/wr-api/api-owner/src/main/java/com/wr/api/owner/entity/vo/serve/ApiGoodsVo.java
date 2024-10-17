package com.wr.api.owner.entity.vo.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.api.owner.entity.serve.ApiGoods;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 物品管理扩展类
 * @Author: lvzy
 * @Date: 2022-11-14 13:25:24
 * @Desc: 物品管理扩展类
 */
@Data
public class ApiGoodsVo extends ApiGoods implements Serializable {

    private static final long serialVersionUID = -5772953775809608219L;

    /**
     * 页数
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 借用人账号
     */
    private Long accountId;

    /**
     * 借用申请ID
     */
    private Long borrowId;
    /**
     * 物品id
     */
    private Long goodsId;

    /**
     * 物品名称
     */
    private String goodsName;

    /**
     * 物品编号
     */
    private String goodsNo;

    /**
     * 借用状态：0=空闲中，1=借用中
     */
    private String borrowStatus;

    /**
     * 物品类型
     */
    private String classifyName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 审核状态: 0=审核中，1=驳回，2=通过
     */
    private String auditStatus;

    /**
     * 预计归还时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date returnTime;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /**
     * 列表展示审核时间
     */
    @JsonFormat(pattern = "yyyy.MM.dd")
    @JSONField(format = "yyyy.MM.dd")
    private Date auditTimeList;

    /**
     * 我的借用状态 0=审核待，1=已驳回，2=借用中, 3=已归还
     */
    private String auditStatusByMe;

    /**
     * 间隔时间
     */
    private String hourTimeInterval;

}
