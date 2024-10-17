package com.wr.api.owner.entity.home.contact;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 常用电话对象 contact
 *
 * @author DIANWEI
 * @date 2022-11-17
 */
@Data
public class Contact extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long contactId;

    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    /**
     * 联系人名称
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactPhone;

    /**
     * 机构名称
     */
    private String companyName;

    /**
     * 机构地址
     */
    private String address;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
}
