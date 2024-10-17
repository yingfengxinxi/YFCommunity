package com.wr.api.estate.entity.vo.application.serve;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/23 16:02
 */
@Data
public class OwnerInfoVo {


    /**
     * 业主姓名
     */
    private String ownerName;


    /**
     * 手机号
     */
    private String ownerPhone;

    /**
     * 性别：0=男，1=女，2=未知
     */
    private String gender;
    /**
     * 证件类型：见数据字典 sys_certificate
     */
    private String cardType;
    /**
     * 证件号码
     */
    private String cardNo;

    //与户主关系：0=本人，1=配偶，2=父母，3=子女，4=其他,5=租客
    private String ownerRelation;
    //交房时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliveryTime;
    /**
     * 政治面貌：0=党员，1=团员，2=其他
     */
    private String politicsStatus;
    /**
     * 宗教信仰
     */
    private String religion;
    /**
     * 个人介绍
     */
    private String selfIntro;
    //身份证正面
    private String cardFont;
    //身份证反面
    private String cardBack;
    //账号id
    private Long accountId;
    //小区id
    private Long communityId;
}
