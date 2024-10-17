package com.wr.api.estate.entity.application.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2021-11-14 09:42:25
 * @Desc: 访客同行记录映射实体类
 */
@Data
public class VisitLog implements Serializable {

    private static final long serialVersionUID = -326404585553978419L;

    /**
     * 主键id
     */
    private Long logId;

    /**
     * 来访姓名
     */
    private String userName;

    /**
     * 手机号
     */
    private String userPhone;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 车牌号
     */
    private String plantNo;

    /**
     * 同行人数
     */
    private String withNum;

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 识别小区
     */
    private String communityName;

    /**
     * 楼栋、单元、房间
     */
    private String roomName;

    /**
     * 拜访目标人
     */
    private String ownerName;

    /**
     * 拜访目标人手机号
     */
    private String ownerPhone;

    /**
     * 安康码颜色 1绿色，2黄色，3红色
     */
    private String healthLevel;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备唯一标识
     */
    private String indexCode;

    /**
     * 进门时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date intoTime;

    /**
     * 出门时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date outTime;

    /**
     * 删除状态 0 未删除 1 已删除
     */
    private String delFlag;

}
