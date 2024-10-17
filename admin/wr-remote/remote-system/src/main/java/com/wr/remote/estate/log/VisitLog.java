package com.wr.remote.estate.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: RainCity
 * @Date: 2021-08-14 17:18:25
 * @Desc: 访客同行记录映射实体类
 */
@Data
public class VisitLog implements Serializable {

    private static final long serialVersionUID = 1469074621374766734L;
    /**主键id*/
    private Long logId;
    /**用户名*/
    private String userName;
    /**手机*/
    private String userPhone;
    /**身份证*/
    private String idCard;
    /**识别小区*/
    private Long communityId;
    private String communityName;
    /**拜访目标房间*/
    private String roomName;
    /**拜访目标人*/
    private String ownerName;
    /**拜访目标人手机号*/
    private String ownerPhone;
    /** 安康码颜色*/
    private String healthLevel;
    /**设备名称*/
    private String deviceName;
    /**设备编号*/
    private String indexCode;
    /**进门时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date intoTime;
    /**出门时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outTime;

    /** 删除状态 0 未删除 1 已删除 */
    private String delFlag;

    private String tableName;

}
