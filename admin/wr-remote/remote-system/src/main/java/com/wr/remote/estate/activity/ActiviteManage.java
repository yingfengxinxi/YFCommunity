package com.wr.remote.estate.activity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: Wen Chou
 * @Date: 2022-10-11 13:42:50
 * @Desc:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("活动管理")
public class ActiviteManage extends BaseEntity {

    private static final long serialVersionUID = -114335928929756147L;
    /** '主键' */
    private Long activityId;
    /** '小区ID' */
    private Long communityId;
    /** '小区ID' */
    private Long buildingId;
    /** '小区名称' */
    private String communityName;
    /** '删除标记' */
    private Long delFlag;
    /** '类别ID' */
    private Long typeId;
    /** '活动状态' */
    private String actState;
    /** '负责人' */
    private String leader;
    /** '负责人电话' */
    private String leaderPhone;
    /** '活动说明' */
    private String actIntro;
    /** '物资数量' */
    private Long supplyNum;
    /** '物资名称' */
    private String supplyName;
    /** '参与人员' */
    private Long ownerId;
    /** '报名时间' */
    private String signInTime;
    /** '签到时间' */
    private String signUpTime;
    /** '类别名称' */
    private String typeName;
    /** '活动名称' */
    private String activityName;
    /** '人名' */
    private String ownerName;
    /** '手机号码' */
    private String ownerPhone;
    //备注
    private String remark;
    private String ownerRemark;
    /** '报名截至时间' */
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    private Date closeTime;
    /** '活动开始时间' */
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    private Date startTime;
    /** '活动结束时间' */
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    private Date endTime;
    /** '活动地址' */
    private String address;
    /** '活动范围' */
    private String actRang;
    /** '活动范围' */
    private String actRangs;
    /** '楼栋名' */
    private String buildingName;
    /** '活动费用' */
    private BigDecimal actAmount;
    /** '活动物资' */
    private String actSupply;
    /** '图片' */
    private String actUrl;
    /** '活动意义' */
    private String actMean;
    /** '签到标记' */
    private String signState;
    /** '纪律要求' */
    private String lawReq;
    /** '活动过程' */
    private String actProcess;
    /** '参与人数' */
    private Long joinNum;
    /** '签到人数' */
    private Long signNum;

    private Long interPeople;
    private Long signPeople;
    @JsonFormat(pattern ="yyyy-MM-dd" )
    private Date lastTime;

    private List<ActiviteManage> distributionLists;

    /** 物业id */
    private Long estateId;

    private List<Long> buildingIds;
}
