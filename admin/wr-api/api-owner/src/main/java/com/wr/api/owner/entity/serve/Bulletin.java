package com.wr.api.owner.entity.serve;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * 人员通报实体，表：bus_bulletin
 *
 * @author SJiang
 * @since 2022-11-15 14:18:36
 */
public class Bulletin extends BaseEntity {
    private static final long serialVersionUID = -595208698359282694L;

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
    private String crimeReason;
    /**
     * 归档：0=未归档，1=已归档
     */
    private String archive;

}

