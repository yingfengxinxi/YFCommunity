package com.wr.remote.estate.customer.lost;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-27 10:56:12
 * @Desc: 寻人启事实体类
 */
@Data
public class PeopleLostVo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 924298501080040284L;

    /**
     * 主键
     */
    private Long lostId;

    /**
     * 小区id，表bus_community
     */
    private Long communityId;


    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 姓名
     */
    private String lostName;

    /**
     * 性别：0=男，1=女
     */
    private String gender;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 年龄
     */
    private Integer lostAge;

    /**
     * 身高
     */
    private BigDecimal lostHeight;

    /**
     * 籍贯
     */
    private String hometown;

    /**
     * 失踪日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lostDate;

    /**
     * 失踪地点
     */
    private String lostAddress;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactPhone;
    /**
     * 寻人类型
     */
    private String peopleType;

    /**
     * 详情
     */
    private String lostInfo;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    /**
     * 状态：0=寻找中，1=已找回，2=已撤销
     */
    private String lostStatus;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;


}
