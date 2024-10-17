package com.wr.remote.aiot;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 14:35:34
 * @Desc: 停车库
 */
@Data
public class Park extends BaseDevice{
    private static final long serialVersionUID = 2022985771797485544L;

    /** 主键 */
    private Long parkId;

    /** 父停车库唯一标识 */
    private String parentIndexCode;

    /** 停车库名称 */
    private String parkName;

    /** 车库类型：0=车库，1=大门 */
    private String parkType;

    /** 支付二维码 */
    private String payCode;

    /** 出入口数据 */
    List<Entrance> entranceList;
}
