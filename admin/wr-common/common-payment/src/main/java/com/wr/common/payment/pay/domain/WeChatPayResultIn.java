package com.wr.common.payment.pay.domain;

import com.alibaba.fastjson2.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class WeChatPayResultIn {

    @JSONField(name = "id")
    @ApiModelProperty("通知ID")
    private String id;

    @JSONField(name = "create_time")
    @ApiModelProperty("通知创建时间")
    private String create_time;

    @JSONField(name = "resource_type")
    @ApiModelProperty("通知数据类型")
    private String resource_type;

    @JSONField(name = "event_type")
    @ApiModelProperty("通知类型")
    private String event_type;

    @JSONField(name = "summary")
    @ApiModelProperty("回调摘要")
    private String summary;

    @JSONField(name = "resource")
    @ApiModelProperty("回调摘要")
    private WechatPayResourceIn resource;
}