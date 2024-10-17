package com.wr.remote.govern.voluntary;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 志愿者活动表(BusVoluntary)实体类
 *
 * @author lvzy
 * @since 2022-10-24 17:10:28
 */
@Data
public class Voluntary  implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long voluntaryId;
    /**
     * 物业id，表xccj_estate_sys.bus_estate
     */
    private Long estateId;
    /**
     * 活动名称
     */
    private String actTitle;
    /**
     * 封面
     */
    private String coverUrl;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 需要人数
     */
    private Long needNum;
    /**
     * 内容
     */
    private String content;
    /**
     * 活动所在小区id，表xccj_community.bus_community
     */
    private Long communityId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
    /**
     * 发布小区
     */
    private Long[] communityIds;
}

