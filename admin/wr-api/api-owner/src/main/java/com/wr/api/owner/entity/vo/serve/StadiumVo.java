package com.wr.api.owner.entity.vo.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.remote.domain.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-15 11:21:42
 * @Desc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StadiumVo {

    /** 主键 */
    private Long appointId;

    /** 主键 */
    private Long venueId;

    /** 小区id */
    private Long communityId;

    /** 场馆名称 */
    @Excel(name = "场馆名称")
    private String venueName;

    /** 场馆地址 */
    @Excel(name = "场馆地址")
    private String address;

    /** 押金 */
    @Excel(name = "押金")
    private BigDecimal deposit;

    /** 每小时费用 */
    @Excel(name = "每小时费用")
    private BigDecimal hourFee;

    /** 管理员id*/
    @Excel(name = "管理员id")
    private Long staffId;

    /** 场馆图片 */
    @Excel(name = "场馆图片")
    private String venueImage;

    /** 状态：0=可用，1=关闭 */
    @Excel(name = "状态：0=可用，1=关闭")
    private String venueStatus;

    /** 删除标记：0=未删除，1=已删除 */
    private String delFlag;


    /**
     * 状态：0=未支付，1=已支付，2=已退款，3=无需退款
     */
    private String appointStatus;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 创建时间
     */
    private String createTime;

    private String createBy;
    /**
     * 场馆预约信息
     */
    List<StadiumPointVo> stadiumPointVos;

    private String userName;

    private String accountName;

    /**
     * 状态 0:进行中，1：未开始，2：已结束,3 已退款
     */
    private String timeStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planStart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planEnd;

    /**
     * 身份证
     */
    private String cardNo;
    private String userPhone;
    private String typeName;

    /**
     * 管理员联系方式
     */
    private String staffPhone;

    /**
     * 管理员姓名
     */
    private String staffName;
}
