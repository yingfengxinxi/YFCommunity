package com.wr.api.owner.entity.serve;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 自治规约实体类，表：bus_statute
 *
 * @author SJiang
 * @since 2022-11-15 13:08:05
 */
@Data
public class Statute extends BaseEntity {
    private static final long serialVersionUID = 4408150557790345597L;

    /**
     * 主键
     */
    private Long statuteId;
    /**
     * 物业id，表xccj_estate_sys.bus_estate
     */
    private Long estateId;
    /**
     * 标题
     */
    private String statuteTitle;
    /**
     * 封面
     */
    private String coverUrl;
    /**
     * 内容
     */
    private String content;

}

