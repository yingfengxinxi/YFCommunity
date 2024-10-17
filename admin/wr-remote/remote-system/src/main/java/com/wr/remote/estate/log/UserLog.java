package com.wr.remote.estate.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-31 16:51:15
 * @Desc: 人员出入记录实体类
 */
@Data
public class UserLog implements Serializable {

    private static final long serialVersionUID = 3762709114153692152L;
    /**
     * 主键id
     */
    private Long logId;

    /**
     * 用户姓名
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
     * 用户类型 0=未知 1 业主 2 租客
     */
    private String userType;

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
     * 设备类型 0门禁设备 1=云+设备
     */
    private String deviceType;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备唯一标识
     */
    private String indexCode;

    /**
     * 识别方式 1  人脸识别 2 蓝牙开门 3 安康码 4  二维码 5 社保卡
     */
    private String ioType;

    /**
     * 安康码颜色 1绿色，2黄色，3红色
     */
    private String healthLevel;

    /**
     * 识别时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date discernTime;

    /**
     * 识别图片
     */
    private String picUrl;

    /**
     * 删除状态 0 未删除 1 已删除
     */
    private String delFlag;

    private String tableName;

}
