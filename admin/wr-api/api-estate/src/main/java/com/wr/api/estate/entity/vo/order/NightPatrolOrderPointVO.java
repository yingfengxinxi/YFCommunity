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
public class NightPatrolOrderPointVO implements Serializable {
    private static final long serialVersionUID = -4327177548210424494L;

    /**
     * 巡查点id
     */
    private Long pointId;

    /**
     * 巡点名称
     */
    private String pointName;

    /**
     * 巡点地址
     */
    private String pointAddress;

    /**
     * 结果：0=待巡更，1=正常，2=异常
     */
    private String result;

    /**
     * 结果说明
     */
    private String resultInfo;
}
