package com.wr.api.owner.entity.vo.serve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: xuShu
 * @Date: 2022-12-06 13:25:15
 * @Desc: 老人基本信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OldBasicInfoVo {


    private Long ownerId;

    private String accountId;

    private Long parentId;

    /**
     * 老人姓名
     */
    private String ownerName;

    /**
     * 性别：0=男，1=女，2=未知
     */
    private String gender;


    /**
     * 联系方式
     */
    private String ownerPhone;

    /**
     * 证件类型
     */
    private String cardType;

    /**
     * 证件名称
     */
    private String dictLabel;

    /**
     * 政治面貌：0=党员，1=团员，2=其他
     */
    private String politicsStatus;

    /**
     * 与户主关系：0=本人，1=配偶，2=父母，3=子女，4=其他
     */
    private  String ownerRelation;

    /**
     * 证件照（正面）
     */
    private String cardFont;

    /**
     * 证件照（反面）
     */
    private String cardBack;

    /**
     * 人脸照片
     */
    private String faceUrl;

    private String cardNo;

}
