package com.wr.api.owner.entity.home;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
* @Author SUNk
* @Description 新访客档案表
* @Date 19:34 2024/3/13
* @Param
* @return
**/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("新访客档案表")
@TableName(value = "xccj_community.bus_interview")
public class BusInterview extends BaseEntity {
    private String id;
    private String cardType;
    private String cardNo;
    private Long communityId;
    private Long accountId;
    private String visitorName;
    private String visitorPhone;
    private String visitorNum;
    private String plantCar;
    private String plantNo;
    private String withNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date endTime;
    private Long roomId;
    private Long ownerId;
    private String ownPhone;
    private String ownName;
}
