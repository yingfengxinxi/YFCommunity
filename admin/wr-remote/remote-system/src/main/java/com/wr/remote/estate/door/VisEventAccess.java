package com.wr.remote.estate.door;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/13 19:47
 * @Version 1.5
 */
@Data
public class VisEventAccess extends BaseEntity {

    /**事件唯一编号*/
    private String eventId;
    /**事件类型*/
    private Integer eventType;
    /**事件名称*/
    private String eventName;
    /**事件产生事件*/
    private String eventTime;
    /**人员唯一编号*/
    private String personId;
    /**卡号*/
    private String cardNo;
    /**人员名称*/
    private String personName;
    /**组织编码*/
    private String orgIndexCode;
    /**组织名称*/
    private String orgName;
    /**设备名称*/
    private String deviceName;
    /**设备编码*/
    private String deviceIndexCode;
    /**图片存储服务的唯一标识*/
    private String svrIndexCode;
    /**抓怕图片地址*/
    private String picUrl;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private List<Date> dateRange;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    private Long communityId;

    private String beginDate;

    private String endDate;

}
