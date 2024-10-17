package com.wr.api.owner.entity.serve;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 管家管理 实体，表：bus_majordomo
 *
 * @author Sjiang
 * @since 2022-11-11 16:28:35
 */
@Data
public class Majordomo extends BaseEntity {
    private static final long serialVersionUID = -7135337132476451932L;

    /**
     * 主键
     */
    private Long majordomoId;
    /**
     * 小区id，表bus_community
     */
    private Long communityId;
    /**
     * 楼栋id，表bus_building
     */
    private Long buildingId;
    /**
     * 员工id，表bus_staff
     */
    private Long staffId;

    @Override
    public String toString() {
        return "Majordomo{" +
                "majordomoId=" + majordomoId +
                ", communityId=" + communityId +
                ", buildingId=" + buildingId +
                ", staffId=" + staffId +
                '}';
    }
}

