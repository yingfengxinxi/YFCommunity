package com.wr.api.owner.entity.serve;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 防控信息实体,表：bus_prevent
 *
 * @author SJiangg
 * @since 2022-11-15 10:16:29
 */
@Data
public class Prevent extends BaseEntity {
    private static final long serialVersionUID = -1625734590371314103L;

    /**
     * 主键
     */
    private Long preventId;
    /**
     * 物业id，表xccj_estate_sys.bus_estate
     */
    private Long estateId;
    /**
     * 标题
     */
    private String preventTitle;
    /**
     * 图片
     */
    private String imageUrl;
    /**
     * 内容
     */
    private String content;

}

