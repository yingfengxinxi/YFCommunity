package com.wr.api.owner.entity.vo.serve;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: xuShu
 * @Date: 2022-11-15 16:35:08
 * @Desc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OlderFilesVo {
    /**
     * 主键
     */
    private Long olderId;

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 楼栋id
     */
    private Long buildingId;

    /**
     * 单元id
     */
    private Long unitId;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 业主id
     */
    private Long ownerId;

    /**
     * 紧急联系人
     */
    private String contactName;

    /**
     * 紧急联系人电话
     */
    private String contactPhone;

    /**
     * 联系人性别
     */
    private String contactGender;

    /**
     * 联系人身份证
     */
    private String contactId;

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
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 修改时间
     */
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 逻辑删除
     */
    private String delFlag;

    private Long accountId;

    /**
     * 与户主关系：0=本人，1=配偶，2=父母，3=子女，4=其他,5=租客
     */
    private String ownerRelation;

    /**
     * 业主姓名
     */
    private String  ownerName;
    /**
     * 手机号
     */
    private String  ownerPhone;

    /**
     * 性别：0=男，1=女，2=未知
     */
    private String  gender;
    /**
     * 证件类型
     */
    private String  cardType;
    /**
     * 证件号码
     */
    private String  cardNo;
    /**
     * 证件照（正面）
     */
    private String  cardFont;
    /**
     * 证件照（反面）
     */
    private String  cardBack;
    /**
     * 人脸URL
     */
    private String  faceUrl;
    /**
     * 政治面貌：0=党员，1=团员，2=其他
     */
    private String  politicsStatus;

    private String communityName;

    private String unitName;

    private String roomName;

    private String buildingName;

    private String roomStatus;

    private String dictLabel;
}
