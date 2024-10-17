package com.wr.api.estate.entity.vo.order;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-14 20:53:57
 * @Desc: 报事报修工单详情
 */
@Data
public class NightPatrolOrderDetailVO extends OrderDetailVO implements Serializable {
    private static final long serialVersionUID = -4327177548210424494L;

    /**
     * 巡更类型
     */
    private String nightPatrolType;

    /**
     * 地址
     */
    private String orderSource;

    /**
     * 来源工单
     */
    private String otherOrderNo;

    /**
     * 巡更顺序：0=顺序，1=乱序
     */
    private Integer watchmanType;

    /**
     * 巡查点
     */
    private List<NightPatrolOrderPointVO> points;

    /**
     * 异常图片
     */
    private List<String> abnormalImages;
}
