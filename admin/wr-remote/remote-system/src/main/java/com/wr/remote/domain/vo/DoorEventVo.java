package com.wr.remote.domain.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/3 16:06
 */
@Data
public class DoorEventVo {

    private String id;
    /**
     * 门禁点编号
     */
    private String doorNo;
    private String indexCode;
    private Long communityId;
    /**
     * 门序号
     */
    private Integer doorSerial;
    /**
     * 通道类型
     */
    private String channelType;
    //设备状态
    private Integer online;
    //所属小区
    private String communityName;
    //楼栋
    private String buildingName;
    //单元楼
    private String unitName;
    //门禁点地址
    private String address;
    //	事件时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "eventTime", format = "yyyy-MM-dd HH:mm:ss")
    private Date eventTime;

    //卡号
    private String cardNo;

    //人员名称
    private String personName;

    //人员所属组织名称
    private String orgName;

    //门禁点名称
    private String doorName;

    //	事件类型，参考附录D.2.1.1 门禁事件类型
    private String eventType;
    private List<String> eventTypes;

    //	进出门类型1=进0=出-1=未知要求：进门读卡器拨码设置为出门读卡器拨码设置为2
    private String inAndOutType;

    //	控制器名称
    private String devName;

    //证件号码
    private String certNo;
    private String startTime;
    private String endTime;
}
