package com.wr.remote.govern.security;


import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * bus_bulletin
 * @author 
 */
@Data
public class Bulletin extends BaseEntity {
    private static final long serialVersionUID = -4269479080720627861L;
    /**
     * 主键
     */
    private Long bulletinId;

    /**
     * 物业id，表xccj_estate_sys.bus_estate
     */
    private Long estateId;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别：0=男，1=女，2=未知
     */
    private String gender;

    /**
     * 年龄
     */
    private Integer userAge;

    /**
     * 证件类型：见数据字典 sys_certificate
     */
    private String cardType;

    /**
     * 证件号码
     */
    private String cardNo;

    /**
     * 犯罪级别：A=A级，B=B级
     */
    private String crimeLevel;

    /**
     * 通报原因
     */
    @NotBlank(message = "通报原因不能为空")
    private String crimeReason;

    /**
     * 归档：0=未归档，1=已归档
     */
    private String archive;

    /**
     * 是否是管理员 -1是管理员
     */
    private String isManage;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    /** 线索数 */
    private Integer clueNum;

}