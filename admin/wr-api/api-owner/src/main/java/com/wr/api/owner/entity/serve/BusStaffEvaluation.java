package com.wr.api.owner.entity.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 员工评价表
 *
 * @author SJiang
 * @since 2022-11-14 21:12:07
 */
@Data
@TableName(value = "xccj_community.bus_staff_evaluation")
public class BusStaffEvaluation extends BaseEntity {
    private static final long serialVersionUID = 387243816282535734L;

    /**
     * 主键
     */
    private String id;
    /**
     * 员工ID
     */
    private String staffId;
    /**
     * 员工账号id
     */
    private String userId;
    /**
     * 物业id
     */
    private String estateId;
    /**
     * 小区id
     */
    private String communityId;
    //评价人账号accound_id
    private String accoundId;
    //业主ID
    private String ownId;
    //评价级别1-5颗星
    private Integer satisfactionLevel;
    //评价备注
    private String comments;
    //评价日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date satisfactionTime;

}

