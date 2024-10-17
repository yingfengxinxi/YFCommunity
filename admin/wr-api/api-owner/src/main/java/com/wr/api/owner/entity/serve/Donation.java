package com.wr.api.owner.entity.serve;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 业主捐赠 实体，表：bus_donation
 *
 * @author SJiang
 * @since 2022-11-14 21:12:07
 */
@Data
public class Donation extends BaseEntity {
    private static final long serialVersionUID = 387243816282535734L;

    /**
     * 主键
     */
    private Long donationId;
    /**
     * 物业id，表xccj_estate_sys.bus_estate
     */
    private Long estateId;
    /**
     * 标题
     */
    private String donationTitle;
    /**
     * 内容
     */
    private String content;

}

