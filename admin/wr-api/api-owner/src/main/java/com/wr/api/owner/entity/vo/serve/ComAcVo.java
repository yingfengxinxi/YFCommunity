package com.wr.api.owner.entity.vo.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: xuShu
 * @Date: 2022-11-16 09:52:45
 * @Desc: 社区活动表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComAcVo {
    /**
     * 主键
     */
    private Long activityId;

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 类型id
     */
    private Long typeId;

    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 活动类型
     */
    private String typeName;
    /**
     * 报名截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JSONField(format ="yyyy-MM-dd hh:mm:ss")
    private Date closeTime;
    /**
     * 活动开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format ="yyyy-MM-dd")
    private Date startTime;
    /**
     * 活动结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JSONField(format ="yyyy-MM-dd hh:mm:ss")
    private Date endTime;
    /**
     * 活动地点
     */
    private String address;

    /**
     * 活动范围：0=全体业主，1=选择业主
     */
    private String actRang;
    /**
     * 活动费用
     */
    private Double actAmount;
    /**
     * 图片
     */
    private String actUrl;
    /**
     * 活动说明
     */
    private String actIntro;
    /**
     * 报名人数
     */
    private Integer joinNum;
    /**
     * 负责人
     */
    private String leader;
    /**
     * 负责人联系方式
     */
    private String leaderPhone;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format ="yyyy-MM-dd")
    private Date createTime;
    /**
     * 状态
     */
    private String state;

    /**
     * 业主id
     */
    private Long ownerId;

    /**
     * 报名时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date signInTime;

    /**
     * 签到时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date signUpTime;

    /**
     * 0是已签到 1已报名
     */
    private String ApplySign;


    List<String> supplyNames;

    /** 物资 */
    List<Map<String,Object>> supplys;
}
