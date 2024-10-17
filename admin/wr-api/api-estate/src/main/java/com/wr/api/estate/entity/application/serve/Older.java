package com.wr.api.estate.entity.application.serve;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-15 13:28:02
 * @Desc: 老人档案实体类
 */
@Data
public class Older implements Serializable {

    private static final long serialVersionUID = 1862765027158854704L;

    /**
     * 主键
     */
    private Long olderId;

    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    /**
     * 楼栋id，表bus_building
     */
    private Long buildingId;

    /**
     * 单元id，表bus_unit
     */
    private Long unitId;

    /**
     * 房间id，表bus_room
     */
    private Long roomId;

    /**
     * 业主id，表bus_owner
     */
    private Long ownerId;

    /**
     * 紧急联系人名称
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactPhone;

    /**
     * 联系人性别 新增
     */
    private String contactGender;

    /**
     * 与老人关系：0=父子，1=父女，2=母子，3=母女，4=兄弟，5=姐妹，6=其他关系
     */
    private String relation;

    /**
     * 审核状态：0=审核中，1=通过，2=驳回
     */
    private String auditStatus;

    /**
     * 驳回理由
     */
    private String rejectReason;

    /**
     * 创建人（申请人）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createBy;

    /**
     * 创建时间（申请时间）
     */
    private Date createTime;

    /**
     * 修改人（审批人）
     */
    private String updateBy;

    /**
     * 修改时间（审批时间）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    /**
     * 联系人身份证 新增
     */
    private byte[] contactId;

    /**
     * 联系人身份证
     */
    private String contactIds;

}
