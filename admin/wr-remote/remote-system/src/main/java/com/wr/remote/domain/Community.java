package com.wr.remote.domain;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  社区信息管理实体类
 * @author DIANWEI
 * @date 2022/9/21 14:46
 */
@Data
public class Community extends BaseEntity {
    /**
     * 主键
     */
    private Long communityId;
    /**
     * 社区名称
     */
    private String communityName;
    /**
     * 社区别名
     */
    private String communityAlias;
    /**
     * 所属物业id，表xccj_community.bus_estate
     */
    private Long estateId;
    /**
     * 区域id，表xccj_community.bus_area
     */
    private Long areaId;
    /**
     * 容积率
     */
    private BigDecimal plotRatio;
    /**
     * 开发商
     */
    private String developersName;
    /**
     * 总户数
     */
    private Integer residentNum;
    /**
     * 登记户数
     */
    private Integer registerNum;
    /**
     * 绿化率
     */
    private BigDecimal greenRatio;
    /**
     * 地理板块名称
     */
    private String plateName;
    /**
     * 户型面积范围
     */
    private String doorArea;
    /**
     * 占地面积
     */
    private BigDecimal landSpace;
    /**
     * 建筑面积
     */
    private BigDecimal coveredArea;
    /**
     * 合同开始日期
     */
    private Date contractBegin;
    /**
     * 合同结束日期
     */
    private Date contractEnd;
    /**
     * 楼盘地址
     */
    private String address;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 删除状态:0=未删除;1=已删除
     */
    private String delFlag;
}
