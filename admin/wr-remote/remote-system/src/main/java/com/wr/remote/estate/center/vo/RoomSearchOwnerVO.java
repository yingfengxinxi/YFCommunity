package com.wr.remote.estate.center.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-11 17:37:31
 * @Desc:
 */
@Data
@ToString
public class RoomSearchOwnerVO {

    /**
     * 业主Id
     */
    private Long ownerId;

    /**
     * 业主名称
     */
    private String ownerName;

    /**
     * 业主手机号
     */
    private String ownerPhone;

    /**
     * 业主身份证号
     */
    private String cardNo;

    /**
     * 所属小区
     */
    private String communityName;

    /**
     * 与户主关系
     */
    private Integer ownerRelation;
    //入住时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

}
